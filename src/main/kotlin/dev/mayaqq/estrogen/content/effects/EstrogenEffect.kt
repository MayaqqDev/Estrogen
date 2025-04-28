package dev.mayaqq.estrogen.content.effects

import dev.mayaqq.cynosure.entities.PlayerLookup.tracking
import dev.mayaqq.cynosure.utils.currentTime
import dev.mayaqq.cynosure.utils.isModLoaded
import dev.mayaqq.estrogen.client.features.boobs.Boob
import dev.mayaqq.estrogen.client.features.dash.ClientDash
import dev.mayaqq.estrogen.compat.cobblemon.CobblemonCompat
import dev.mayaqq.estrogen.content.EstrogenAttributes
import dev.mayaqq.estrogen.content.EstrogenAttributes.FALL_DAMAGE_RESISTANCE
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.features.dash.CommonDash.removeDashing
import net.minecraft.client.player.LocalPlayer
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.AttributeMap
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.player.Player
import java.util.*

class EstrogenEffect(category: MobEffectCategory, color: Int) : MobEffect(category, color) {

    private val DASH_MODIFIER_UUID: UUID = UUID.fromString("2a2591c5-009f-4b24-97f2-b15f43415e4c")
    private val FALL_DAMAGE_RESISTANCE_UUID: UUID = UUID.fromString("2a2591c5-009f-4b24-97f2-b15f43415e4d")
    private val BOOBS_MODIFIER_UUID: UUID = UUID.fromString("2a2591c5-009f-4b24-97f2-b15f43415e4e")

    init {
        addAttributeModifier(
            FALL_DAMAGE_RESISTANCE,
            FALL_DAMAGE_RESISTANCE_UUID.toString(),
            2.0,
            AttributeModifier.Operation.ADDITION
        )
    }

    override fun applyEffectTick(entity: LivingEntity, amplifier: Int) {
//        if (isModLoaded("cobblemon")) {
//            if (entity.javaClass.getPackageName().contains("cobblemon")) {
//                CobblemonCompat.toFemale(entity)
//            }
//        }

        // Check if Dash is enabled on the server
        //TODO: CONFIG CHECK if (!EstrogenConfig.server().dashEnabled.get()) return

        // Only tick on the client and if the entity is a player
        if (!(entity is LocalPlayer && entity.level().isClientSide)) return

        ClientDash.tick()
    }

    override fun removeAttributeModifiers(entity: LivingEntity, attributes: AttributeMap, amplifier: Int) {
        super.removeAttributeModifiers(entity, attributes, amplifier)
        removeDashing(entity.getUUID())

        if (entity is ServerPlayer) {
            sendRemovePlayerStatusEffect(
                entity,
                EstrogenEffects.ESTROGEN,
                *tracking(entity).filterIsInstance<ServerPlayer>().toTypedArray()
            )
        }

        if (entity is Player) {
            entity.getAttribute(EstrogenAttributes.DASH_LEVEL)?.removeModifier(DASH_MODIFIER_UUID)
            entity.getAttribute(EstrogenAttributes.SHOW_BOOBS)?.removeModifier(BOOBS_MODIFIER_UUID)
        }

        if (entity is Player && !Boob.shouldShow(entity)) {
            entity.getAttribute(EstrogenAttributes.BOOB_INITIAL_SIZE)?.baseValue = 0.0
            entity.getAttribute(EstrogenAttributes.BOOB_GROWING_START_TIME)?.baseValue = -1.0
        }
    }

    override fun addAttributeModifiers(entity: LivingEntity, attributes: AttributeMap, amplifier: Int) {
        super.addAttributeModifiers(entity, attributes, amplifier)
        if (entity !is Player) return

        if (entity is ServerPlayer) {
            sendPlayerStatusEffect(
                entity,
                EstrogenEffects.ESTROGEN,
                *tracking(entity).filterIsInstance<ServerPlayer>().toTypedArray()
            )
        }

        super.addAttributeModifiers(entity, attributes, amplifier)

        val dashModifier = AttributeModifier(
            DASH_MODIFIER_UUID,
            "Dash Level",
            (amplifier + 1).toDouble(),
            AttributeModifier.Operation.ADDITION
        )
        entity.getAttribute(EstrogenAttributes.DASH_LEVEL)?.removeModifier(DASH_MODIFIER_UUID)
        entity.getAttribute(EstrogenAttributes.DASH_LEVEL)?.addPermanentModifier(dashModifier)

        entity.getAttribute(EstrogenAttributes.SHOW_BOOBS)?.removeModifier(BOOBS_MODIFIER_UUID)
        entity.getAttribute(EstrogenAttributes.SHOW_BOOBS)?.addPermanentModifier(
            AttributeModifier(
                BOOBS_MODIFIER_UUID,
                "Show Boobs",
                1.0,
                AttributeModifier.Operation.ADDITION
            )
        )

        val startTime = entity.getAttribute(EstrogenAttributes.BOOB_GROWING_START_TIME)
        // should fix crash related to applying effect to entity without given attribute
        if (startTime != null && startTime.baseValue < 0.0) {
            val currentTime = currentTime(entity.level())
            entity.getAttribute(EstrogenAttributes.BOOB_GROWING_START_TIME)!!.baseValue = currentTime
        }
    }

    override fun isDurationEffectTick(duration: Int, amplifier: Int): Boolean {
        return true
    }

    companion object {
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