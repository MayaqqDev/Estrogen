package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.Estrogen
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.RangedAttribute
import uwu.serenity.kritter.api.Registrar
import uwu.serenity.kritter.api.entry
import uwu.serenity.kritter.api.entry.RegistryEntry
import kotlin.math.pow

object EstrogenAttributes : Registrar<Attribute> by Estrogen..Registries.ATTRIBUTE {
    // Dash Level
    val DASH_LEVEL = entry("dash_level", {RangedAttribute("attribute.name.estrogen.dash_level", 0.0, 0.0, 10.0).setSyncable(true)}).register()
    // Boob growing Sync
    val SHOW_BOOBS = entry("show_boobs", {RangedAttribute("attribute.name.estrogen.show_boobs", 0.0, 0.0, 1.0).setSyncable(true)}).register()
    val BOOB_GROWING_START_TIME = entry("boob_growing_start_time", {RangedAttribute("attribute.name.estrogen.boob_growing_start_time", -1.0, -1.0, 2.0.pow(53)).setSyncable(true)}).register()
    val BOOB_INITIAL_SIZE = entry("boob_initial_size", {RangedAttribute("attribute.name.estrogen.boob_initial_size", 0.0, 0.0, 1.0).setSyncable(true)}).register()
    // Fall Damage Resistance
    val FALL_DAMAGE_RESISTANCE = entry("fall_damage_resistance", {RangedAttribute("attribute.name.estrogen.fall_damage_resistance", 1.0, 1.0, 100.0).setSyncable(true)}).register()

    //TODO: In old estrogen, we register attributes sooner so we can use them in the mixin, not sure if that will break for us.
}