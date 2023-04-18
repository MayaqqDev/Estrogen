package dev.mayaqq.estrogen.registry;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.entities.DashParticleEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class EntityRegistry {
    public static final EntityType<DashParticleEntity> DASH_PARTICLE_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            Estrogen.id("dash_particle_entity"), FabricEntityTypeBuilder.create(SpawnGroup.MISC, DashParticleEntity::new).dimensions(EntityDimensions.fixed(1, 2)).build()
    );
    public static void register() {
    }
}
