package dev.mayaqq.estrogen.registry.common;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.Registry;

public class EstrogenAttributes {
    public static void register() {
        DefaultAttributeContainer.Builder builder = PlayerEntity.createPlayerAttributes();
        builder.add(BOOB_GROWING_START_TIME);
        builder.add(BOOB_INITIAL_SIZE);
        FabricDefaultAttributeRegistry.register(EntityType.PLAYER, builder);
    }
    public static final EntityAttribute BOOB_GROWING_START_TIME = Registry.register(Registry.ATTRIBUTE, "estrogen.boob_growing_start_time", (new ClampedEntityAttribute("attribute.name.estrogen.boob_growing_start_time", -1.0, -1.0, Math.pow(2, 53))).setTracked(true));
    public static final EntityAttribute BOOB_INITIAL_SIZE = Registry.register(Registry.ATTRIBUTE, "estrogen.boob_initial_size", (new ClampedEntityAttribute("attribute.name.estrogen.boob_initial_size", 0.0, 0.0, 1.0)).setTracked(true));

}
