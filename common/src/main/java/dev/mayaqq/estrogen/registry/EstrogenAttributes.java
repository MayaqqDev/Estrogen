package dev.mayaqq.estrogen.registry;

import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import uwu.serenity.critter.api.entry.RegistryEntry;
import uwu.serenity.critter.api.generic.Registrar;

public class EstrogenAttributes {
    public static final Registrar<Attribute> ATTRIBUTES = Estrogen.REGISTRIES.getRegistrar(Registries.ATTRIBUTE);
    public static final RegistryEntry<Attribute> DASH_LEVEL = ATTRIBUTES.entry("dash_level", () -> (new RangedAttribute("attribute.name.estrogen.dash_level", 0.0, 0.0, 10.0)).setSyncable(true)).register();
    // Boob growing client sided sync
    public static final RegistryEntry<Attribute> SHOW_BOOBS = ATTRIBUTES.entry("show_boobs", () -> (new RangedAttribute("attribute.name.estrogen.show_boobs", 0.0, 0.0, 1.0)).setSyncable(true)).register();
    public static final RegistryEntry<Attribute> BOOB_GROWING_START_TIME = ATTRIBUTES.entry("boob_growing_start_time", () -> (new RangedAttribute("attribute.name.estrogen.boob_growing_start_time", -1.0, -1.0, Math.pow(2, 53))).setSyncable(true)).register();
    public static final RegistryEntry<Attribute> BOOB_INITIAL_SIZE = ATTRIBUTES.entry("boob_initial_size", () -> (new RangedAttribute("attribute.name.estrogen.boob_initial_size", 0.0, 0.0, 1.0)).setSyncable(true)).register();
    public static final RegistryEntry<Attribute> FALL_DAMAGE_RESISTANCE = ATTRIBUTES.entry("fall_damage_resistance", () -> (new RangedAttribute("attribute.name.estrogen.fall_damage_resistance", 1.0, 1.0, 100.0)).setSyncable(true)).register();

    // This is needed for the mixin to work, as it runs before mod init
    static {
        ATTRIBUTES.register();
    }

    public static void init() {}
}
