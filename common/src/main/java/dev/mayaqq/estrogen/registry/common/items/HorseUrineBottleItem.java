package dev.mayaqq.estrogen.registry.common.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class HorseUrineBottleItem extends Item {

    public HorseUrineBottleItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, net.minecraft.world.level.Level level, LivingEntity user) {
        super.finishUsingItem(stack, level, user);
        if (user instanceof ServerPlayer serverPlayerEntity) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.awardStat(Stats.ITEM_USED.get(this));
        }

        if (stack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        } else {
            if (user instanceof Player player && !player.isCreative()) {
                ItemStack itemStack = new ItemStack(Items.GLASS_BOTTLE);
                if (!player.getInventory().add(itemStack)) {
                    player.drop(itemStack, false);
                }
            }

            return stack;
        }
    }

    public int getUseDuration(ItemStack itemStack) {
        return 40;
    }

    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.DRINK;
    }

    public SoundEvent getDrinkingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    public SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        return ItemUtils.startUsingInstantly(level, player, interactionHand);
    }
}
