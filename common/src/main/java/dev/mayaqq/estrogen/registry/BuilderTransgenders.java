package dev.mayaqq.estrogen.registry;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import uwu.serenity.critter.stdlib.blocks.BlockBuilder;
import uwu.serenity.critter.stdlib.items.ItemBuilder;

import java.util.function.Function;
import java.util.function.UnaryOperator;

// :333333
public class BuilderTransgenders {
    static <B extends Block, P> UnaryOperator<BlockBuilder<B, P>> stressImpact(double impact) {
        return b -> {
            BlockStressDefaults.setDefaultImpact(b.getId(), impact);
            return b;
        };
    }

    static <I extends Item, P> UnaryOperator<ItemBuilder<I, P>> tooltip(Function<Item, TooltipModifier> tooltipFactory) {
        return b -> {
            TooltipModifier.REGISTRY.registerDeferred(b.getId(), tooltipFactory);
            return b;
        };
    }

    static <I extends Item, P> UnaryOperator<ItemBuilder<I, P>> standardTooltip() {
        return tooltip(item -> new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE));
    }
}
