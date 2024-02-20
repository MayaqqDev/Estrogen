package dev.mayaqq.estrogen.registry;

import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.utils.Time;
import net.minecraft.world.effect.MobEffectInstance;

import static dev.mayaqq.estrogen.registry.EstrogenAttributes.BOOB_GROWING_START_TIME;
import static dev.mayaqq.estrogen.registry.EstrogenAttributes.BOOB_INITIAL_SIZE;
import static dev.mayaqq.estrogen.registry.EstrogenEffects.ESTROGEN_EFFECT;
import static dev.mayaqq.estrogen.utils.Boob.boobSize;

public class EstrogenEvents {

    public static void register() {
        PlayerEvent.PLAYER_QUIT.register(player -> {
            if (player.hasEffect(ESTROGEN_EFFECT.get())) {
                double startTime = player.getAttributeValue(BOOB_GROWING_START_TIME.get());
                double currentTime = Time.currentTime(player.level());
                float initialSize = (float) player.getAttributeValue(BOOB_INITIAL_SIZE.get());
                float size = boobSize(startTime, currentTime, initialSize, 0.0F);
                player.getAttribute(BOOB_INITIAL_SIZE.get()).setBaseValue(size);
            }
        });
        PlayerEvent.PLAYER_JOIN.register(player -> {
            if (player.hasEffect(ESTROGEN_EFFECT.get())) {
                double currentTime = Time.currentTime(player.level());
                player.getAttribute(BOOB_GROWING_START_TIME.get()).setBaseValue(currentTime);
            }
        });
        TickEvent.PLAYER_POST.register(player -> {
            if (EstrogenConfig.common().minigameEnabled.get()) {
                if (EstrogenConfig.common().permaDash.get()) {
                    player.addEffect(new MobEffectInstance(ESTROGEN_EFFECT.get(), 20, EstrogenConfig.common().girlPowerLevel.get(), false, false, false));
                }
            }
        });
    }
}
