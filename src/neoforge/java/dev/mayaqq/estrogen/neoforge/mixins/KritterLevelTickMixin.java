package dev.mayaqq.estrogen.neoforge.mixins;

import invoke.kitty.kritter.events.ServerTickEvents;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Kritter's shared NeoForge tick bridge uses a JVM type switch containing
 * ClientLevel. NeoForge resolves that client class even when the actual value
 * is a ServerLevel, which crashes a dedicated server on its first tick.
 */
@Mixin(targets = "invoke.kitty.kritter.platform.forge.ForgeEventsKt", remap = false)
public abstract class KritterLevelTickMixin {
    @Inject(method = "onLevelTickPre", at = @At("HEAD"), cancellable = true)
    private static void estrogen$dispatchServerLevelTickPre(
            LevelTickEvent.Pre event,
            CallbackInfo callback
    ) {
        if (event.getLevel() instanceof ServerLevel level) {
            ServerTickEvents.StartLevelTick.getDispatcher().invoke(level);
            callback.cancel();
        }
    }

    @Inject(method = "onLevelTickPost", at = @At("HEAD"), cancellable = true)
    private static void estrogen$dispatchServerLevelTickPost(
            LevelTickEvent.Post event,
            CallbackInfo callback
    ) {
        if (event.getLevel() instanceof ServerLevel level) {
            ServerTickEvents.EndLevelTick.getDispatcher().invoke(level);
            callback.cancel();
        }
    }
}
