package dev.mayaqq.estrogen.forge.registry.item;

import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class EstrogenPatchesItem extends Item implements ICurioItem {
    public EstrogenPatchesItem(Properties arg) {
        super(arg);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            player.addEffect(new MobEffectInstance(EstrogenEffects.ESTROGEN_EFFECT, -1, stack.getCount() - 1, false, false, false));
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        slotContext.entity().removeEffect(EstrogenEffects.ESTROGEN_EFFECT);
    }

    @Override
    public @NotNull String getDescriptionId(ItemStack stack) {
        int count = stack.getCount();
        if (count == 1) {
            return super.getDescriptionId(stack);
        } else {
            return "item.estrogen.estrogen_patches_plural";
        }
    }
}
