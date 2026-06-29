package dev.mayaqq.estrogen.content

import dev.mayaqq.cynosure.utils.addSpawn
import dev.mayaqq.cynosure.utils.contains
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.client.content.entityRenderers.moth.MothRenderer
import dev.mayaqq.estrogen.content.entities.MothEntity
import dev.mayaqq.estrogen.utils.entity.getMothMobCategory
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.entity.*
import invoke.kitty.kritter.utils.color.White
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.SpawnPlacementTypes
import net.minecraft.world.level.levelgen.Heightmap

object EstrogenEntities : Registrar<EntityType<*>> by Registrar(MOD_ID, Registries.ENTITY_TYPE) {
    val Moth = entity("moth", getMothMobCategory(), ::MothEntity) {
        settings {
            sized(0.6f, 0.6f)
            clientTrackingRange(80)
            fireImmune()
            canSpawnFarFromPlayer()
            eyeHeight(0.5F)
        }
        renderer(::MothRenderer)
        attributes(MothEntity::createAttributes)
        spawnPlacement(SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MothEntity::checkMobSpawnRules)
        spawnEgg(White, White)
        addSpawn(
            { it in EstrogenTags.Biomes.SPAWNS_MOTH },
            getMothMobCategory(),
            30,
            1 to 3,
        )
    }
}