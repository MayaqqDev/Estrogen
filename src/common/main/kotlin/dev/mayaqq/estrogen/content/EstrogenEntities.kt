package dev.mayaqq.estrogen.content

import dev.mayaqq.cynosure.utils.addSpawn
import dev.mayaqq.cynosure.utils.colors.White
import dev.mayaqq.cynosure.utils.colors.toUInt
import dev.mayaqq.cynosure.utils.contains
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.client.content.entityRenderers.moth.MothRenderer
import dev.mayaqq.estrogen.content.entities.MothEntity
import dev.mayaqq.estrogen.utils.entity.getMothMobCategory
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.SpawnPlacements
import net.minecraft.world.level.levelgen.Heightmap
import uwu.serenity.kritter.api.Registrar
import uwu.serenity.kritter.client.stdlib.renderer
import uwu.serenity.kritter.stdlib.attributes
import uwu.serenity.kritter.stdlib.entity
import uwu.serenity.kritter.stdlib.spawnEgg
import uwu.serenity.kritter.stdlib.spawnPlacement

object EstrogenEntities : Registrar<EntityType<*>> by Estrogen..Registries.ENTITY_TYPE {
    val Moth by entity("moth", getMothMobCategory(), ::MothEntity) {
        settings {
            sized(0.6f, 0.6f)
            clientTrackingRange(80)
            fireImmune()
            canSpawnFarFromPlayer()
        }
        renderer(::MothRenderer)
        attributes(MothEntity::createAttributes)
        spawnPlacement(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MothEntity::checkMobSpawnRules)
        spawnEgg(White.toUInt(), White.toUInt())
        addSpawn(
            { it in EstrogenTags.Biomes.SPAWNS_MOTH },
            getMothMobCategory(),
            30,
            1 to 3,
        )
    }
}