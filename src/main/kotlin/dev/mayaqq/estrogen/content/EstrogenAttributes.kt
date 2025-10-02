@file:EventSubscriber
package dev.mayaqq.estrogen.content

import dev.mayaqq.cynosure.entities.EntityAttributes
import dev.mayaqq.cynosure.events.PostInitEvent
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.events.entity.EntityDamageEvent
import dev.mayaqq.cynosure.utils.contains
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.content.EstrogenAttributes.BoobGrowingStartTime
import dev.mayaqq.estrogen.content.EstrogenAttributes.BoobInitialSize
import dev.mayaqq.estrogen.content.EstrogenAttributes.DashLevel
import dev.mayaqq.estrogen.content.EstrogenAttributes.FallDamageResistance
import dev.mayaqq.estrogen.content.EstrogenAttributes.ShowBoobs
import net.minecraft.core.registries.Registries
import net.minecraft.tags.DamageTypeTags
import net.minecraft.world.damagesource.DamageSources
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.RangedAttribute
import net.minecraft.world.entity.player.Player
import uwu.serenity.kritter.api.Registrar
import uwu.serenity.kritter.api.entry
import kotlin.math.pow

object EstrogenAttributes : Registrar<Attribute> by Estrogen..Registries.ATTRIBUTE {
    // Dash Level
    val DashLevel by entry("dash_level", {RangedAttribute("attribute.name.estrogen.dash_level", 0.0, 0.0, 10.0).setSyncable(true)})
    // Boob growing Sync
    val ShowBoobs by entry("show_boobs", {RangedAttribute("attribute.name.estrogen.show_boobs", 0.0, 0.0, 1.0).setSyncable(true)})
    val BoobGrowingStartTime by entry("boob_growing_start_time", {RangedAttribute("attribute.name.estrogen.boob_growing_start_time", -1.0, -1.0, 2.0.pow(53)).setSyncable(true)})
    val BoobInitialSize by entry("boob_initial_size", {RangedAttribute("attribute.name.estrogen.boob_initial_size", 0.0, 0.0, 1.0).setSyncable(true)})
    // Fall Damage Resistance
    val FallDamageResistance by entry("fall_damage_resistance", {RangedAttribute("attribute.name.estrogen.fall_damage_resistance", 1.0, 1.0, 1000.0).setSyncable(true)})
}

@Subscription
fun postInit(event: PostInitEvent) {
    EntityAttributes.modify(EntityType.PLAYER) {
        add(DashLevel)
        add(FallDamageResistance)
        add(ShowBoobs)
        add(BoobInitialSize)
        add(BoobGrowingStartTime)
    }
}

@Subscription
fun onDamage(event: EntityDamageEvent) {
    if (event.entity is Player && event.source in DamageTypeTags.IS_FALL) {
        val amount = (event.entity as Player).getAttributeValue(FallDamageResistance).toFloat()
        if (amount > event.amount) {
            event.result = 0.0F
        } else {
            event.result = event.amount / amount
        }
    }
}