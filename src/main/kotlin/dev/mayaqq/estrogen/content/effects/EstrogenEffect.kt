package dev.mayaqq.estrogen.content.effects

import dev.mayaqq.cynosure.utils.isModLoaded
import dev.mayaqq.estrogen.compat.cobblemon.CobblemonCompat
import net.minecraft.client.player.LocalPlayer
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import java.util.*

class EstrogenEffect(category: MobEffectCategory, color: Int) : MobEffect(category, color) {

    private val DASH_MODIFIER_UUID: UUID = UUID.fromString("2a2591c5-009f-4b24-97f2-b15f43415e4c")
    private val FALL_DAMAGE_RESISTANCE_UUID: UUID = UUID.fromString("2a2591c5-009f-4b24-97f2-b15f43415e4d")
    private val BOOBS_MODIFIER_UUID: UUID = UUID.fromString("2a2591c5-009f-4b24-97f2-b15f43415e4e")

    init {
        addAttributeModifier(
            FALL_DAMAGE_RESISTANCE.get(),
            FALL_DAMAGE_RESISTANCE_UUID.toString(),
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

        // Check if Dash is enabled on the server
        //TODO: CONFIG CHECK if (!EstrogenConfig.server().dashEnabled.get()) return

        // Only tick on the client and if the entity is a player
        if (!(entity is LocalPlayer && entity.level().isClientSide)) return

        //TODO: Dash Tick
    }

    //TODO: Rest of the fucking owl
}