package dev.mayaqq.estrogen.forge.tooltip;

import dev.mayaqq.estrogen.registry.items.ThighHighsItem;
import dev.mayaqq.estrogen.registry.tooltip.ThighHighsToolTipModifier;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ForgeThighHighsTooltip extends ThighHighsToolTipModifier {
    public ForgeThighHighsTooltip(ThighHighsItem item) {
        super(item);
    }

    @Override
    public void modify(ItemTooltipEvent context) {
        super.modify(context.getItemStack(), context.getEntity(), context.getFlags(), context.getToolTip());
    }
}
