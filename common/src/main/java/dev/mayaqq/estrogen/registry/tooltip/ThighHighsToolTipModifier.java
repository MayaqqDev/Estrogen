package dev.mayaqq.estrogen.registry.tooltip;

import com.simibubi.create.foundation.item.TooltipModifier;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import dev.mayaqq.estrogen.registry.items.ThighHighsItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ThighHighsToolTipModifier implements TooltipModifier {
    @Override
    public void modify(ItemStack stack, Player player, TooltipFlag flags, List<Component> tooltip) {
        ThighHighsItem thighHighs = EstrogenItems.THIGH_HIGHS.get();
        if(!stack.is(thighHighs)) return;

        thighHighs.getStyle(stack).ifPresent(style -> {
            String translationKey = style.toLanguageKey("tooltip.thigh_highs");
            tooltip.add(1, Component.translatable(translationKey));
        });
    }
}
