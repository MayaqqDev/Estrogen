package dev.mayaqq.estrogen.fabric;

import dev.mayaqq.estrogen.networking.EstrogenStatusEffectSender;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.minecraft.server.level.ServerPlayer;

public class EstrogenFabricEvents {
    // Could replace with mixin in common? Event is called at head and tail, pretty easy impl
    public static void register() {
        EntityTrackingEvents.START_TRACKING.register(((trackedEntity, player) -> {
            if (trackedEntity instanceof ServerPlayer trackedPlayer) {
                new EstrogenStatusEffectSender().sendPlayerStatusEffect(trackedPlayer, EstrogenEffects.ESTROGEN_EFFECT, player);
            }
        }));

        EntityTrackingEvents.STOP_TRACKING.register(((trackedEntity, player) -> {
            if (trackedEntity instanceof ServerPlayer trackedPlayer) {
                new EstrogenStatusEffectSender().sendRemovePlayerStatusEffect(trackedPlayer, EstrogenEffects.ESTROGEN_EFFECT, player);
            }
        }));
    }
}
