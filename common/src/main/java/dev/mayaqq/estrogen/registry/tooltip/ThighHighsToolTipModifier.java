package dev.mayaqq.estrogen.registry.tooltip;

import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import dev.mayaqq.estrogen.platform.CommonPlatform;
import dev.mayaqq.estrogen.registry.items.ThighHighsItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public abstract class ThighHighsToolTipModifier implements TooltipModifier {

    private static final Component DYED = Component.translatable("item.dyed")
        .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC);

    private final ThighHighsItem item;
    protected final ItemDescription.Modifier wrappedDescription;

    public static ThighHighsToolTipModifier create(Item item) {
        return CommonPlatform.createThighHighTooltip(item);
    }

    public ThighHighsToolTipModifier(ThighHighsItem item) {
        this.item = item;
        this.wrappedDescription = new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE);
    }


    public void modify(ItemStack stack, Player player, TooltipFlag flags, List<Component> tooltip) {
        item.getStyle(stack).ifPresentOrElse(style -> {
            String translationKey = style.toLanguageKey("tooltip.thigh_highs");
            tooltip.add(1, Component.translatable(translationKey));
        }, () -> {
            if(item.hasCustomColor(stack)) tooltip.add(1, DYED);
        });
    }
}
