package dev.mayaqq.estrogen.registry.tooltip;

import com.simibubi.create.foundation.item.TooltipModifier;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import dev.mayaqq.estrogen.registry.items.ThighHighsItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ThighHighsToolTipModifier implements TooltipModifier {

    private final ThighHighsItem item;

    public ThighHighsToolTipModifier(Item item) {
        this.item = (ThighHighsItem) item;
    }

    @Override
    public void modify(ItemStack stack, Player player, TooltipFlag flags, List<Component> tooltip) {
        item.getStyle(stack).ifPresentOrElse(style -> {
            String translationKey = style.toLanguageKey("tooltip.thigh_highs");
            tooltip.add(1, Component.translatable(translationKey));
        }, () -> {
            if(item.hasCustomColor(stack)) {
                tooltip.add(1, Component.translatable("item.dyed")
                    .withStyle(ChatFormatting.GRAY)
                    .withStyle(ChatFormatting.ITALIC)
                );
            }
        });
    }
}
