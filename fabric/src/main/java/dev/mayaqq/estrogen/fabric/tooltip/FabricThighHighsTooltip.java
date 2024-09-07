package dev.mayaqq.estrogen.fabric.tooltip;

import dev.mayaqq.estrogen.registry.items.ThighHighsItem;
import dev.mayaqq.estrogen.registry.tooltip.ThighHighsToolTipModifier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class FabricThighHighsTooltip extends ThighHighsToolTipModifier {
    public FabricThighHighsTooltip(ThighHighsItem item) {
        super(item);
    }

    @Override
    public void modify(ItemStack stack, Player player, TooltipFlag flags, List<Component> tooltip) {
        super.modify(stack, player, flags, tooltip);
        wrappedDescription.modify(stack, player, flags, tooltip);
    }
}
