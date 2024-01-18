package dev.mayaqq.estrogen.utils;

import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.chunk.ChunkSource;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

public class PlayerLookup {
    public static Collection<ServerPlayer> tracking(Entity entity) {
        Objects.requireNonNull(entity, "Entity cannot be null");
        ChunkSource manager = entity.level().getChunkSource();

        if (manager instanceof ServerChunkCache) {
            ChunkMap storage = ((ServerChunkCache) manager).chunkMap;
            ChunkMap.TrackedEntity tracker = storage.entityMap.get(entity.getId());

            // return an immutable collection to guard against accidental removals.
            if (tracker != null) {
                return tracker.seenBy.stream().map(ServerPlayerConnection::getPlayer).collect(Collectors.toUnmodifiableSet());
            }

            return Collections.emptySet();
        }

        throw new IllegalArgumentException("Only supported on server worlds!");
    }
}
