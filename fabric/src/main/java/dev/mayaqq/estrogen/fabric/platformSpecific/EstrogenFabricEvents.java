package dev.mayaqq.estrogen.fabric.platformSpecific;

import dev.mayaqq.estrogen.networking.EstrogenStatusEffectSenderFabric;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.minecraft.server.level.ServerPlayer;

import static dev.mayaqq.estrogen.registry.common.EstrogenEffects.ESTROGEN_EFFECT;

public class EstrogenFabricEvents {
    // Could replace with mixin in common? Event is called at head and tail, pretty easy impl
    public static void register() {
        EntityTrackingEvents.START_TRACKING.register(((trackedEntity, player) -> {
            if (trackedEntity instanceof ServerPlayer trackedPlayer) {
                new EstrogenStatusEffectSenderFabric().sendPlayerStatusEffect(trackedPlayer, ESTROGEN_EFFECT, player);
            }
        }));

        EntityTrackingEvents.STOP_TRACKING.register(((trackedEntity, player) -> {
            if (trackedEntity instanceof ServerPlayer trackedPlayer) {
                new EstrogenStatusEffectSenderFabric().sendRemovePlayerStatusEffect(trackedPlayer, ESTROGEN_EFFECT, player);
            }
        }));
    }
}
