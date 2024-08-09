package dev.mayaqq.estrogen.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.entities.MothEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class EstrogenEntities {
    public static final ResourcefulRegistry<EntityType<?>> ENTITIES = ResourcefulRegistries.create(BuiltInRegistries.ENTITY_TYPE, Estrogen.MOD_ID);

    public static final RegistryEntry<EntityType<MothEntity>> MOTH = ENTITIES.register("moth", () ->
            EntityType.Builder.<MothEntity>of(MothEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 0.6F)
                    .clientTrackingRange(80)
                    .build("moth"));
}
