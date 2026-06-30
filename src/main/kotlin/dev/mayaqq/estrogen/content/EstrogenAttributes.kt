@file:EventSubscriber
package dev.mayaqq.estrogen.content

import dev.mayaqq.cynosure.entities.EntityAttributes
import dev.mayaqq.cynosure.events.PostInitEvent
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.events.entity.EntityDamageEvent
import dev.mayaqq.cynosure.events.entity.player.PlayerRespawnEvent
import dev.mayaqq.cynosure.utils.add
import dev.mayaqq.cynosure.utils.contains
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.content.EstrogenAttributes.BoobGrowingStartTime
import dev.mayaqq.estrogen.content.EstrogenAttributes.BoobInitialSize
import dev.mayaqq.estrogen.content.EstrogenAttributes.DashLevel
import dev.mayaqq.estrogen.content.EstrogenAttributes.FallDamageResistance
import dev.mayaqq.estrogen.content.EstrogenAttributes.ShowBoobs
import dev.mayaqq.estrogen.utils.holder
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.api.builder.entry
import net.minecraft.core.registries.Registries
import net.minecraft.tags.DamageTypeTags
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.RangedAttribute
import net.minecraft.world.entity.player.Player
import kotlin.math.pow

object EstrogenAttributes : Registrar<Attribute> by Registrar(MOD_ID, Registries.ATTRIBUTE) {
    // Dash Level
    val DashLevel = entry("dash_level", {
        RangedAttribute("attribute.name.estrogen.dash_level", 0.0, 0.0, 10.0).setSyncable(true)
    }) {}
    // Boob growing Sync
    val ShowBoobs = entry("show_boobs", {
        RangedAttribute("attribute.name.estrogen.show_boobs", 0.0, 0.0, 1.0).setSyncable(true)
    }) {}
    val BoobGrowingStartTime = entry("boob_growing_start_time", {
        RangedAttribute("attribute.name.estrogen.boob_growing_start_time", -1.0, -1.0, 2.0.pow(53)).setSyncable(true)
    }) {}
    val BoobInitialSize = entry("boob_initial_size", {
        RangedAttribute("attribute.name.estrogen.boob_initial_size", 0.0, 0.0, 1.0).setSyncable(true)
    }) {}
    // Fall Damage Resistance
    val FallDamageResistance = entry("fall_damage_resistance", {
        RangedAttribute("attribute.name.estrogen.fall_damage_resistance", 1.0, 1.0, 1000.0).setSyncable(true)
    }) {}
}

object EstrogenAttributeEvents {
    @Subscription
    fun PostInitEvent.postInit() {
        EntityAttributes.modify(EntityType.PLAYER) {
            add(DashLevel.value!!)
            add(FallDamageResistance.value!!)
            add(ShowBoobs.value!!)
            add(BoobInitialSize.value!!)
            add(BoobGrowingStartTime.value!!)
        }
    }

    @Subscription
    internal fun EntityDamageEvent.onDamage() {
        if (entity is Player && source in DamageTypeTags.IS_FALL) {
            val resistance = (entity as Player).getAttributeValue(FallDamageResistance.holder()).toFloat()
            result = if (resistance > amount) {
                0.0F
            } else {
                amount / resistance
            }
        }
    }

    @Subscription
    internal fun PlayerRespawnEvent.onPlayerRespawn() {
        if (newPlayer.level().isClientSide) return
        val attributes = arrayOf(ShowBoobs, BoobGrowingStartTime, BoobInitialSize)
        attributes.forEach { attribute ->
            oldPlayer.getAttribute(attribute.holder())?.let { instance ->
                newPlayer.getAttribute(attribute.holder())?.replaceFrom(instance)
            }
        }
    }
}