package dev.mayaqq.estrogen.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class EstrogenAttributes {
    public static final ResourcefulRegistry<Attribute> ATTRIBUTES = ResourcefulRegistries.create(BuiltInRegistries.ATTRIBUTE, "estrogen");
    public static final RegistryEntry<Attribute> DASH_LEVEL = ATTRIBUTES.register("dash_level", () -> (new RangedAttribute("attribute.name.estrogen.dash_level", 0.0, 0.0, 10.0)).setSyncable(true));
    // Boob growing client sided sync
    public static final RegistryEntry<Attribute> BOOB_GROWING_START_TIME = ATTRIBUTES.register("boob_growing_start_time", () -> (new RangedAttribute("attribute.name.estrogen.boob_growing_start_time", -1.0, -1.0, Math.pow(2, 53))).setSyncable(true));
    public static final RegistryEntry<Attribute> BOOB_INITIAL_SIZE = ATTRIBUTES.register("boob_initial_size", () -> (new RangedAttribute("attribute.name.estrogen.boob_initial_size", 0.0, 0.0, 1.0)).setSyncable(true));
    public static final RegistryEntry<Attribute> FALL_DAMAGE_RESISTANCE = ATTRIBUTES.register("fall_damage_resistance", () -> (new RangedAttribute("attribute.name.estrogen.fall_damage_resistance", 1.0, 1.0, 100.0)).setSyncable(true));
}
