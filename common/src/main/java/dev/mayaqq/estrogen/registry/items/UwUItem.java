package dev.mayaqq.estrogen.registry.items;


import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UwUItem extends Item {
    public UwUItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable net.minecraft.world.level.Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        tooltip.add(Component.translatable("item.estrogen.uwu.tooltip"));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
