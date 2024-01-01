package dev.mayaqq.estrogen.mixin.accessor;

import net.minecraft.server.network.ServerPlayerConnection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(targets = "net/minecraft/server/world/ThreadedAnvilChunkStorage$EntityTracker")
public interface EntityTrackerAccessor {
	@Accessor("listeners")
	Set<ServerPlayerConnection> getPlayersTracking();
}
