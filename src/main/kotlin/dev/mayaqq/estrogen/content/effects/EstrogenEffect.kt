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
import dev.mayaqq.estrogen.id
import dev.mayaqq.estrogen.utils.holder
import net.minecraft.core.Holder
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket
import net.minecraft.server.level.ServerPlayer
import net.minecraft.tags.DamageTypeTags
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.AttributeInstance
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.player.Player


class EstrogenEffect(category: MobEffectCategory, color: Int) : MobEffect(category, color) {
    init {
        addAttributeModifier(
            FallDamageResistance.holder(),
            fallDamageResistanceID,
            2.0,
            AttributeModifier.Operation.ADD_VALUE
        )
    }

    override fun applyEffectTick(entity: LivingEntity, amplifier: Int): Boolean {
        if (isModLoaded("cobblemon")) {
            if (entity.javaClass.getPackageName().contains("cobblemon")) {
                CobblemonCompat.toFemale(entity)
            }
        }

        if (!EstrogenCommonConfig.Dash.enabled) return false

        // Only tick on the client and if the entity is a player
        if (entity is Player && entity.level().isClientSide) {
            ClientDash.tick()
            if (entity.getEffect(EstrogenEffects.Estrogen.holder())?.duration == 1) ClientDash.reset()
        }
        return true
    }

    override fun isInstantenous(): Boolean = false

    companion object {
        private val dashModifierID = id("dash_modifier")
        private val fallDamageResistanceID = id("fall_damage_resistance")
        private val boobModifierID = id("boob_modifier")

        fun sendPlayerStatusEffect(player: ServerPlayer, effect: Holder<MobEffect>, vararg targetPlayers: ServerPlayer) {
            val effectInstance = player.getEffect(effect) ?: return
            //TODO: WHAT THE FUCK IS A BLEND
            sendPacket(ClientboundUpdateMobEffectPacket(player.id, effectInstance, false), *targetPlayers)
        }

        fun sendRemovePlayerStatusEffect(player: ServerPlayer, effect: Holder<MobEffect>, vararg targetPlayers: ServerPlayer) {
            sendPacket(ClientboundRemoveMobEffectPacket(player.id, effect), *targetPlayers)
        }

        private fun sendPacket(packet: Packet<*>, vararg players: ServerPlayer) {
            for (player in players) {
                player.connection.send(packet)
            }
        }

        fun handleEffectRemoval(entity: LivingEntity) {
            removeDashing(entity.uuid)

            if (entity is Player) {
                if (entity is ServerPlayer) {
                    sendRemovePlayerStatusEffect(
                        entity,
                        EstrogenEffects.Estrogen.holder(),
                        *tracking(entity).toTypedArray()
                    )
                }

                entity.getAttribute(EstrogenAttributes.DashLevel.holder())?.removeModifier(dashModifierID)
                entity.getAttribute(EstrogenAttributes.ShowBoobs.holder())?.removeModifier(boobModifierID)
            }
        }

        fun handleEffectAddition(entity: LivingEntity, amplifier: Int) {
            if (entity !is Player) return

            if (entity is ServerPlayer) {
                sendPlayerStatusEffect(
                    entity,
                    EstrogenEffects.Estrogen.holder(),
                    *tracking(entity).toTypedArray()
                )
            }

            val dashModifier = AttributeModifier(
                dashModifierID,
                (amplifier + 1).toDouble(),
                AttributeModifier.Operation.ADD_VALUE
            )
            entity.getAttribute(EstrogenAttributes.DashLevel.holder())?.replaceModifier(dashModifier)

            val boobModifier = AttributeModifier(
                boobModifierID,
                1.0,
                AttributeModifier.Operation.ADD_VALUE
            )
            entity.getAttribute(EstrogenAttributes.ShowBoobs.holder())?.replaceModifier(boobModifier)

            entity.getAttribute(EstrogenAttributes.BoobGrowingStartTime.holder())?.let {
                if (it.baseValue < 0.0) {
                    val currentTime = currentTime(entity.level())
                    it.baseValue = currentTime
                }
            }
        }
    }
}

@Subscription
internal fun EntityTrackingEvent.Start.onPlayerTracking() {
    if (entity is ServerPlayer) EstrogenEffect.sendPlayerStatusEffect(
        entity as ServerPlayer,
        EstrogenEffects.Estrogen.holder(),
        player,
    )
}

@Subscription
internal fun EntityDamageSourceEvent.onDamageSource() {
    if (source in DamageTypeTags.IS_FALL && (entity as? Player)?.hasEffect(EstrogenEffects.Estrogen.holder()) == true) {
        result = EstrogenDamageSources.of(entity.level(), EstrogenDamageSources.GIRLPOWER)
    }
}

@Subscription
internal fun LivingEntityEvent.EffectApply.onApplyEffect() {
    if (this.oldInstance == null && this.effect == EstrogenEffects.Estrogen && entity is Player) {
        if (!Boob.shouldShow(entity as Player)) {
            entity.getAttribute(EstrogenAttributes.BoobInitialSize.holder())?.baseValue = 0.0
            entity.getAttribute(EstrogenAttributes.BoobGrowingStartTime.holder())?.baseValue = -1.0
        }
    }

    if (effect == EstrogenEffects.Estrogen) {
        EstrogenEffect.handleEffectAddition(entity, newInstance.amplifier)
    }
}

@Subscription
internal fun LivingEntityEvent.EffectRemove.onEffectRemoved() {
    if (effect == EstrogenEffects.Estrogen)
    EstrogenEffect.handleEffectRemoval(this.entity)
}

@Subscription
internal fun LivingEntityEvent.EffectExpire.onEffectExpired() {
    if (effect == EstrogenEffects.Estrogen)
    EstrogenEffect.handleEffectRemoval(this.entity)
}

fun AttributeInstance.replaceModifier(modifier: AttributeModifier) {
    this.getModifier(modifier.id())?.let { this.removeModifier(modifier.id()) }
    this.addPermanentModifier(modifier)

}