package dev.mayaqq.estrogen.content

import dev.mayaqq.cynosure.entities.EntityAttributes
import dev.mayaqq.cynosure.events.PostInitEvent
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.estrogen.Estrogen
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.RangedAttribute
import uwu.serenity.kritter.api.Registrar
import uwu.serenity.kritter.api.entry
import kotlin.math.pow

@EventSubscriber
object EstrogenAttributes : Registrar<Attribute> by Estrogen..Registries.ATTRIBUTE {
    // Dash Level
    val DashLevel by entry("dash_level", {RangedAttribute("attribute.name.estrogen.dash_level", 0.0, 0.0, 10.0).setSyncable(true)})
    // Boob growing Sync
    val ShowBoobs by entry("show_boobs", {RangedAttribute("attribute.name.estrogen.show_boobs", 0.0, 0.0, 1.0).setSyncable(true)})
    val BoobGrowingStartTime by entry("boob_growing_start_time", {RangedAttribute("attribute.name.estrogen.boob_growing_start_time", -1.0, -1.0, 2.0.pow(53)).setSyncable(true)})
    val BoobInitialSize by entry("boob_initial_size", {RangedAttribute("attribute.name.estrogen.boob_initial_size", 0.0, 0.0, 1.0).setSyncable(true)})
    // Fall Damage Resistance
    val FallDamageResistance by entry("fall_damage_resistance", {RangedAttribute("attribute.name.estrogen.fall_damage_resistance", 1.0, 1.0, 1000.0).setSyncable(true)})

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
}