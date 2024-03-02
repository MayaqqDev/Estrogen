package dev.mayaqq.estrogen.registry;

import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.utils.Time;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import static dev.mayaqq.estrogen.registry.EstrogenAttributes.BOOB_GROWING_START_TIME;
import static dev.mayaqq.estrogen.registry.EstrogenAttributes.BOOB_INITIAL_SIZE;
import static dev.mayaqq.estrogen.registry.EstrogenEffects.ESTROGEN_EFFECT;
import static dev.mayaqq.estrogen.utils.Boob.boobSize;

public class EstrogenEvents {
    public static InteractionResult entityInteract(Player player, Entity entity, ItemStack stack, Level level) {
        if (entity.getType().is(EstrogenTags.Entities.URINE_GIVING) && !player.isSpectator()) {
            if (stack.is(Items.GLASS_BOTTLE)) {
                if ((entity instanceof Animal animal && !animal.isBaby()) || !(entity instanceof Animal)) {
                    level.playSound(null, player.blockPosition(), SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS);
                    if (!player.isCreative()) stack.shrink(1);
                    player.getInventory().placeItemBackInInventory(new ItemStack(EstrogenItems.HORSE_URINE_BOTTLE.get()));
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return null;
    }

    public static void onPlayerJoin(Entity entity) {
        if (entity instanceof Player player) {
            if (player.hasEffect(ESTROGEN_EFFECT.get())) {
                double currentTime = Time.currentTime(player.level());
                player.getAttribute(BOOB_GROWING_START_TIME.get()).setBaseValue(currentTime);
            }
        }
    }

    public static void onPlayerQuit(Entity entity) {
        if (entity instanceof Player player) {
            if (player.hasEffect(ESTROGEN_EFFECT.get())) {
                double startTime = player.getAttributeValue(BOOB_GROWING_START_TIME.get());
                double currentTime = Time.currentTime(player.level());
                float initialSize = (float) player.getAttributeValue(BOOB_INITIAL_SIZE.get());
                float size = boobSize(startTime, currentTime, initialSize, 0.0F);
                player.getAttribute(BOOB_INITIAL_SIZE.get()).setBaseValue(size);

            }
        }
    }

    public static void playerTickEnd(Player player) {
        if (EstrogenConfig.common().minigameEnabled.get()) {
            if (EstrogenConfig.common().permaDash.get()) {
                player.addEffect(new MobEffectInstance(ESTROGEN_EFFECT.get(), 20, EstrogenConfig.common().girlPowerLevel.get(), false, false, false));
            }
        }
    }
}
