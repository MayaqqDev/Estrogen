package dev.mayaqq.estrogen.registry.common.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

import java.util.List;

public class UwUItem extends Item {
    public UwUItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.estrogen.uwu.tooltip"));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
