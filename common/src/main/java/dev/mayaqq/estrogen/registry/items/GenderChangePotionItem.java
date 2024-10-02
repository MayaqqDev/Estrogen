package dev.mayaqq.estrogen.registry.items;

import dev.mayaqq.estrogen.utils.Time;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import static dev.mayaqq.estrogen.registry.EstrogenAttributes.*;

public class GenderChangePotionItem extends Item {

    public GenderChangePotionItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
        super.finishUsingItem(stack, level, livingEntity);
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (!level.isClientSide && livingEntity instanceof Player player) {
            AttributeInstance showBoobs = player.getAttribute(SHOW_BOOBS.get());
            AttributeInstance startTime = player.getAttribute(BOOB_GROWING_START_TIME.get());
            AttributeInstance initialSize = player.getAttribute(BOOB_INITIAL_SIZE.get());
            if (showBoobs != null && startTime != null && initialSize != null) {
                if (showBoobs.getBaseValue() > 0.0) {
                    showBoobs.setBaseValue(0.0);
                    startTime.setBaseValue(-1.0);
                    initialSize.setBaseValue(0.0);
                } else {
                    showBoobs.setBaseValue(1.0);
                    if (startTime.getBaseValue() < 0.0) {
                        double currentTime = Time.currentTime(player.level());
                        startTime.setBaseValue(currentTime);
                    }
                }
            }
        }

        if (stack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        } else if (livingEntity instanceof Player player && !player.getAbilities().instabuild) {
            ItemStack itemStack = new ItemStack(Items.GLASS_BOTTLE);
            if (!player.getInventory().add(itemStack)) {
                player.drop(itemStack, false);
            }
        }

        if (level.isClientSide()) {
            Vec3 playerPos = livingEntity.position();
            double heightIncrement = livingEntity.getBoundingBox().getYsize() / 50;

            for (int i = 0; i < 50; i++) {
                double angle = i * Math.PI / 16;
                double x = playerPos.x + 0.5 * Math.cos(angle);
                double y = playerPos.y + i * heightIncrement;
                double z = playerPos.z + 0.5 * Math.sin(angle);

                y += level.random.nextFloat() * 0.1 * (level.random.nextBoolean() ? 1 : -1);

                level.addParticle(
                        new DustParticleOptions(new Vector3f(1f, 0.30f, 0.70f), 1f),
                        x, y, z,
                        0, 0, 0
                );

                x = playerPos.x + 0.5 * Math.cos(angle + Math.PI);
                z = playerPos.z + 0.5 * Math.sin(angle + Math.PI);
                y += level.random.nextFloat() * 0.1 * (level.random.nextBoolean() ? 1 : -1);

                level.addParticle(
                        new DustParticleOptions(new Vector3f(0.15f, 0.20f, 0.81f), 1f),
                        x, y, z,
                        0, 0, 0
                );
            }
        }

        return stack;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 40;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public @NotNull SoundEvent getDrinkingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    @Override
    public @NotNull SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }
}
