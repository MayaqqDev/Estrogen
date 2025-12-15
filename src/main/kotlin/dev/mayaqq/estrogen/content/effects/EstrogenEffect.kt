@file:EventSubscriber
package dev.mayaqq.estrogen.content.effects

import dev.mayaqq.cynosure.core.isModLoaded
import dev.mayaqq.cynosure.entities.PlayerLookup.tracking
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.events.entity.EntityDamageSourceEvent
import dev.mayaqq.cynosure.events.entity.EntityTrackingEvent
import dev.mayaqq.cynosure.events.entity.LivingEntityEvent
import dev.mayaqq.cynosure.utils.contains
import dev.mayaqq.cynosure.utils.currentTime
import dev.mayaqq.estrogen.client.features.boobs.Boob
import dev.mayaqq.estrogen.client.features.dash.ClientDash
import dev.mayaqq.estrogen.compat.cobblemon.CobblemonCompat
import dev.mayaqq.estrogen.config.EstrogenCommonConfig
import dev.mayaqq.estrogen.content.EstrogenAttributes
import dev.mayaqq.estrogen.content.EstrogenAttributes.FallDamageResistance
import dev.mayaqq.estrogen.content.EstrogenDamageSources
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.features.dash.CommonDash.removeDashing
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket
import net.minecraft.server.level.ServerPlayer
import net.minecraft.tags.DamageTypeTags
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.AttributeMap
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.player.Player
import java.util.*


class EstrogenEffect(category: MobEffectCategory, color: Int) : MobEffect(category, color) {
    init {
        addAttributeModifier(
            FallDamageResistance,
            fallDamageResistanceUUID.toString(),
            2.0,
            AttributeModifier.Operation.ADDITION
        )
    }

    override fun applyEffectTick(entity: LivingEntity, amplifier: Int) {
        if (isModLoaded("cobblemon")) {
            if (entity.javaClass.getPackageName().contains("cobblemon")) {
                CobblemonCompat.toFemale(entity)
            }
        }

        if (!EstrogenCommonConfig.Dash.enabled) return

        // Only tick on the client and if the entity is a player
        if (entity is Player && entity.level().isClientSide) ClientDash.tick()
    }

    override fun removeAttributeModifiers(entity: LivingEntity, attributes: AttributeMap, amplifier: Int) {
        super.removeAttributeModifiers(entity, attributes, amplifier)
        removeDashing(entity.uuid)

        if (entity is Player) {
            if (entity is ServerPlayer) {
                sendRemovePlayerStatusEffect(
                    entity,
                    EstrogenEffects.Estrogen,
                    *tracking(entity).toTypedArray()
                )
            }

            entity.getAttribute(EstrogenAttributes.DashLevel)?.removeModifier(dashModifierUUID)
            entity.getAttribute(EstrogenAttributes.ShowBoobs)?.removeModifier(boobModifierUUID)
        }
    }

    override fun addAttributeModifiers(entity: LivingEntity, attributes: AttributeMap, amplifier: Int) {
        super.addAttributeModifiers(entity, attributes, amplifier)
        if (entity !is Player) return

        if (entity is ServerPlayer) {
            sendPlayerStatusEffect(
                entity,
                EstrogenEffects.Estrogen,
                *tracking(entity).toTypedArray()
            )
        }

        val dashModifier = AttributeModifier(
            dashModifierUUID,
            "Dash Level",
            (amplifier + 1).toDouble(),
            AttributeModifier.Operation.ADDITION
        )
        entity.getAttribute(EstrogenAttributes.DashLevel)?.addPermanentModifier(dashModifier)

        val boobModifier = AttributeModifier(
            boobModifierUUID,
            "Show Boobs",
            1.0,
            AttributeModifier.Operation.ADDITION
        )
        entity.getAttribute(EstrogenAttributes.ShowBoobs)?.addPermanentModifier(boobModifier)

        entity.getAttribute(EstrogenAttributes.BoobGrowingStartTime)?.let {
            if (it.baseValue < 0.0) {
                val currentTime = currentTime(entity.level())
                it.baseValue = currentTime
            }
        }
    }

    override fun isDurationEffectTick(duration: Int, amplifier: Int): Boolean = true

    companion object {
        private val dashModifierUUID: UUID = UUID.fromString("2a2591c5-009f-4b24-97f2-b15f43415e4c")
        private val fallDamageResistanceUUID: UUID = UUID.fromString("2a2591c5-009f-4b24-97f2-b15f43415e4d")
        private val boobModifierUUID: UUID = UUID.fromString("2a2591c5-009f-4b24-97f2-b15f43415e4e")

        fun sendPlayerStatusEffect(player: ServerPlayer, effect: MobEffect, vararg targetPlayers: ServerPlayer) {
            val effectInstance = player.getEffect(effect) ?: return
            sendPacket(ClientboundUpdateMobEffectPacket(player.id, effectInstance), *targetPlayers)
        }

        fun sendRemovePlayerStatusEffect(player: ServerPlayer, effect: MobEffect, vararg targetPlayers: ServerPlayer) {
            sendPacket(ClientboundRemoveMobEffectPacket(player.id, effect), *targetPlayers)
        }

        private fun sendPacket(packet: Packet<*>, vararg players: ServerPlayer) {
            for (player in players) {
                player.connection.send(packet)
            }
        }
    }
}

@Subscription
internal fun EntityTrackingEvent.Start.onPlayerTracking() {
    if (entity is ServerPlayer) EstrogenEffect.sendPlayerStatusEffect(
        entity as ServerPlayer,
        EstrogenEffects.Estrogen,
        player,
    )
}

@Subscription
internal fun EntityDamageSourceEvent.onDamageSource() {
    if (source in DamageTypeTags.IS_FALL && (entity as? Player)?.hasEffect(EstrogenEffects.Estrogen) == true) {
        result = EstrogenDamageSources.of(entity.level(), EstrogenDamageSources.GIRLPOWER)
    }
}

@Subscription
internal fun LivingEntityEvent.EffectApply.onApplyEffect() {
    if (this.oldInstance == null && this.effect == EstrogenEffects.Estrogen && entity is Player) {
        if (!Boob.shouldShow(entity as Player)) {
            entity.getAttribute(EstrogenAttributes.BoobInitialSize)?.baseValue = 0.0
            entity.getAttribute(EstrogenAttributes.BoobGrowingStartTime)?.baseValue = -1.0
        }
    }
}