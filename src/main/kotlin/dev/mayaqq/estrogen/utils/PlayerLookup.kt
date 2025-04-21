package dev.mayaqq.estrogen.utils

import net.minecraft.server.level.ServerChunkCache
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.ServerPlayerConnection
import net.minecraft.world.entity.Entity
import java.util.*
import java.util.stream.Collectors
/*
 * This code includes modifications based on or derived from code provided by fabric-api.
 * The original code can be found at: https://github.com/FabricMC/fabric
 * fabric-api is licensed under Apache License 2.0.
 */
//TODO: Cynosure me this batman
object PlayerLookup {
    fun tracking(entity: Entity?): MutableCollection<ServerPlayer?> {
        Objects.requireNonNull<Entity?>(entity, "Entity cannot be null")
        val manager = entity!!.level().getChunkSource()

        if (manager is ServerChunkCache) {
            val storage = manager.chunkMap
            val tracker = storage.entityMap.get(entity.getId())

            // return an immutable collection to guard against accidental removals.
            if (tracker != null) {
                return tracker.seenBy.stream().map<ServerPlayer?> { obj: ServerPlayerConnection? -> obj!!.player }
                    .collect(Collectors.toUnmodifiableSet())
            }

            return mutableSetOf<ServerPlayer?>()
        }

        throw IllegalArgumentException("Only supported on server worlds!")
    }
}