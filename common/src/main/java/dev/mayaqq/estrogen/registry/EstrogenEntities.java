package dev.mayaqq.estrogen.registry;

import com.teamresourceful.resourcefullib.common.color.Color;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.registry.entityRenderers.moth.MothRenderer;
import dev.mayaqq.estrogen.registry.entities.MothEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import uwu.serenity.critter.stdlib.entities.EntityEntry;
import uwu.serenity.critter.stdlib.entities.EntityRegistrar;

public class EstrogenEntities {

    public static final EntityRegistrar ENTITIES = EntityRegistrar.create(Estrogen.MOD_ID);

    public static final EntityEntry<MothEntity> MOTH = ENTITIES.entry("moth", MobCategory.CREATURE, MothEntity::new)
        .settings(s -> s.sized(0.6f, 0.6f)
            .clientTrackingRange(80)
            .fireImmune())
        .renderer(() -> MothRenderer::new)
        .attributes(MothEntity::createAttributes)
        .spawnPlacement(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MothEntity::checkMobSpawnRules)
        .defaultSpawnEgg(Color.parseColor("#ffc514"), Color.parseColor("#ff83c0"))
        .register();

}
