package dev.mayaqq.estrogen.registry.common;

import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.utils.Time;
import net.minecraft.world.effect.MobEffectInstance;

import static dev.mayaqq.estrogen.registry.common.EstrogenAttributes.BOOB_GROWING_START_TIME;
import static dev.mayaqq.estrogen.registry.common.EstrogenAttributes.BOOB_INITIAL_SIZE;
import static dev.mayaqq.estrogen.registry.common.EstrogenEffects.ESTROGEN_EFFECT;
import static dev.mayaqq.estrogen.utils.Boob.boobSize;

public class EstrogenEvents {

    public static void register() {
        PlayerEvent.PLAYER_QUIT.register(player -> {
            if (player.hasEffect(ESTROGEN_EFFECT)) {
                double startTime = player.getAttributeValue(BOOB_GROWING_START_TIME.get());
                double currentTime = Time.currentTime(player.getLevel());
                float initialSize = (float) player.getAttributeValue(BOOB_INITIAL_SIZE.get());
                float size = boobSize(startTime, currentTime, initialSize, 0.0F);
                player.getAttribute(BOOB_INITIAL_SIZE.get()).setBaseValue(size);
            }
        });
        PlayerEvent.PLAYER_JOIN.register(player -> {
            if (player.hasEffect(ESTROGEN_EFFECT)) {
                double currentTime = Time.currentTime(player.getLevel());
                player.getAttribute(BOOB_GROWING_START_TIME.get()).setBaseValue(currentTime);
            }
        });
        TickEvent.PLAYER_POST.register(player -> {
            if (EstrogenConfig.common().minigameEnabled.get()) {
                if (EstrogenConfig.common().permaDash.get()) {
                    player.addEffect(new MobEffectInstance(ESTROGEN_EFFECT, 20, EstrogenConfig.common().girlPowerLevel.get(), false, false, false));
                }
            }
        });
    }
}
