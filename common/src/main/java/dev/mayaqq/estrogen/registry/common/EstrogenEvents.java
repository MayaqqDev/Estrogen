package dev.mayaqq.estrogen.registry.common;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.mayaqq.estrogen.utils.Time;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;

import static dev.mayaqq.estrogen.registry.common.EstrogenAttributes.BOOB_GROWING_START_TIME;
import static dev.mayaqq.estrogen.registry.common.EstrogenAttributes.BOOB_INITIAL_SIZE;
import static dev.mayaqq.estrogen.registry.common.EstrogenEffects.ESTROGEN_EFFECT;
import static dev.mayaqq.estrogen.utils.Boob.boobSize;

public class EstrogenEvents {

    public static void register() {
        PlayerEvent.PLAYER_QUIT.register(player -> {
            if (player.hasEffect(ESTROGEN_EFFECT)) {
                double startTime = player.getAttributeValue(BOOB_GROWING_START_TIME.get());
                double currentTime = Time.currentTime(player.level());
                float initialSize = (float) player.getAttributeValue(BOOB_INITIAL_SIZE.get());
                float size = boobSize(startTime, currentTime, initialSize, 0.0F);
                player.getAttribute(BOOB_INITIAL_SIZE.get()).setBaseValue(size);
            }
        });
        PlayerEvent.PLAYER_JOIN.register(player -> {
            if (player.hasEffect(ESTROGEN_EFFECT)) {
                double currentTime = Time.currentTime(player.level());
                player.getAttribute(BOOB_GROWING_START_TIME.get()).setBaseValue(currentTime);
            }
        });
    }
}
