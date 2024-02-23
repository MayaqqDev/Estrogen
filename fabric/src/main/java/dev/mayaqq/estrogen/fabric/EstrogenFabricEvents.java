package dev.mayaqq.estrogen.fabric;

import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
import dev.mayaqq.estrogen.registry.effects.EstrogenEffect;
import dev.mayaqq.estrogen.utils.Time;
import io.github.fabricators_of_create.porting_lib.entity.events.player.PlayerTickEvents;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

import static dev.mayaqq.estrogen.registry.EstrogenAttributes.BOOB_GROWING_START_TIME;
import static dev.mayaqq.estrogen.registry.EstrogenAttributes.BOOB_INITIAL_SIZE;
import static dev.mayaqq.estrogen.registry.EstrogenEffects.ESTROGEN_EFFECT;
import static dev.mayaqq.estrogen.utils.Boob.boobSize;

public class EstrogenFabricEvents {
    public static void register() {
        EntityTrackingEvents.START_TRACKING.register(((trackedEntity, player) -> {
            if (trackedEntity instanceof ServerPlayer trackedPlayer) {
                EstrogenEffect.sendPlayerStatusEffect(trackedPlayer, EstrogenEffects.ESTROGEN_EFFECT.get(), player);
            }
        }));

        EntityTrackingEvents.STOP_TRACKING.register(((trackedEntity, player) -> {
            if (trackedEntity instanceof ServerPlayer trackedPlayer) {
                EstrogenEffect.sendPlayerStatusEffect(trackedPlayer, EstrogenEffects.ESTROGEN_EFFECT.get(), player);
            }
        }));

        // Minigame ticking
        PlayerTickEvents.END.register((player) -> {
            if (EstrogenConfig.common().minigameEnabled.get()) {
                if (EstrogenConfig.common().permaDash.get()) {
                    player.addEffect(new MobEffectInstance(ESTROGEN_EFFECT.get(), 20, EstrogenConfig.common().girlPowerLevel.get(), false, false, false));
                }
            }
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            if (handler.player.hasEffect(ESTROGEN_EFFECT.get())) {
                double currentTime = Time.currentTime(handler.player.level());
                handler.player.getAttribute(BOOB_GROWING_START_TIME.get()).setBaseValue(currentTime);
            }
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            if (handler.player.hasEffect(ESTROGEN_EFFECT.get())) {
                double startTime = handler.player.getAttributeValue(BOOB_GROWING_START_TIME.get());
                double currentTime = Time.currentTime(handler.player.level());
                float initialSize = (float) handler.player.getAttributeValue(BOOB_INITIAL_SIZE.get());
                float size = boobSize(startTime, currentTime, initialSize, 0.0F);
                handler.player.getAttribute(BOOB_INITIAL_SIZE.get()).setBaseValue(size);
            }
        });
    }
}
