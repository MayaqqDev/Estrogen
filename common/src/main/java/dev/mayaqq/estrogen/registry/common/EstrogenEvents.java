package dev.mayaqq.estrogen.registry.common;

import dev.mayaqq.estrogen.networking.EstrogenStatusEffectSender;
import dev.mayaqq.estrogen.utils.Time;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

import static dev.mayaqq.estrogen.registry.common.EstrogenAttributes.BOOB_GROWING_START_TIME;
import static dev.mayaqq.estrogen.registry.common.EstrogenAttributes.BOOB_INITIAL_SIZE;
import static dev.mayaqq.estrogen.registry.common.EstrogenEffects.ESTROGEN_EFFECT;
import static dev.mayaqq.estrogen.utils.Boob.boobSize;

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

        ServerPlayConnectionEvents.DISCONNECT.register(((handler, server) -> {
            if (handler.player.hasStatusEffect(ESTROGEN_EFFECT)) {
                double startTime = handler.player.getAttributeValue(BOOB_GROWING_START_TIME);
                double currentTime = Time.currentTime(handler.player.getWorld());
                float initialSize = (float) handler.player.getAttributeValue(BOOB_INITIAL_SIZE);
                float size = boobSize(startTime, currentTime, initialSize, 0.0F);
                handler.player.getAttributeInstance(BOOB_INITIAL_SIZE).setBaseValue(size);
            }
        }));

        ServerPlayConnectionEvents.JOIN.register(((handler, sender, server) -> {
            if (handler.player.hasStatusEffect(ESTROGEN_EFFECT)) {
                double currentTime = Time.currentTime(handler.player.getWorld());
                handler.player.getAttributeInstance(BOOB_GROWING_START_TIME).setBaseValue(currentTime);
            }
        }));
    }
}
