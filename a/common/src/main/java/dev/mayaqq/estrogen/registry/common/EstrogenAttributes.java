package dev.mayaqq.estrogen.registry.common;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.mayaqq.estrogen.Estrogen;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Player;

public class EstrogenAttributes {
    public static void register() {
        AttributeSupplier.Builder builder = Player.createAttributes();
        builder.add(DASH_LEVEL.get());
        builder.add(BOOB_GROWING_START_TIME.get());
        builder.add(BOOB_INITIAL_SIZE.get());
        FabricDefaultAttributeRegistry.register(EntityType.PLAYER, builder);
    }

    public static final Registrar<Attribute> attributes = Estrogen.MANAGER.get().get(Registries.ATTRIBUTE);
    public static final RegistrySupplier<Attribute> DASH_LEVEL = attributes.register(Estrogen.id("dash_level"), () -> (new RangedAttribute("attribute.name.estrogen.dash_level", 0.0, 0.0, 10.0)).setSyncable(true));

    // Boob growing client sided sync
    public static final RegistrySupplier<Attribute> BOOB_GROWING_START_TIME = attributes.register(Estrogen.id("boob_growing_start_time"), () -> (new RangedAttribute("attribute.name.estrogen.boob_growing_start_time", -1.0, -1.0, Math.pow(2, 53))).setSyncable(true));
    public static final RegistrySupplier<Attribute> BOOB_INITIAL_SIZE = attributes.register(Estrogen.id("boob_initial_size"), () -> (new RangedAttribute("attribute.name.estrogen.boob_initial_size", 0.0, 0.0, 1.0)).setSyncable(true));

}
