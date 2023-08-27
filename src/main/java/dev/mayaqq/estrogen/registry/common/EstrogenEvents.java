package dev.mayaqq.estrogen.registry.common;

import dev.mayaqq.estrogen.networking.EstrogenStatusEffectSender;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.minecraft.server.network.ServerPlayerEntity;

import static dev.mayaqq.estrogen.registry.common.EstrogenEffects.ESTROGEN_EFFECT;

public class EstrogenEvents {
    public static void register() {
        EntityTrackingEvents.START_TRACKING.register(((trackedEntity, player) -> {
            if (trackedEntity instanceof ServerPlayerEntity trackedPlayer) {
                EstrogenStatusEffectSender.sendPlayerStatusEffect(trackedPlayer, ESTROGEN_EFFECT, player);
            }
        }));

        EntityTrackingEvents.STOP_TRACKING.register(((trackedEntity, player) -> {
            if (trackedEntity instanceof ServerPlayerEntity trackedPlayer) {
                EstrogenStatusEffectSender.sendRemovePlayerStatusEffect(trackedPlayer, ESTROGEN_EFFECT, player);
            }
        }));

    }
}
