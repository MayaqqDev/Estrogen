package dev.mayaqq.estrogen.registry;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.backend.instancing.InstancedRenderRegistry;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstancingController;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import uwu.serenity.critter.stdlib.blockEntities.BlockEntityBuilder;
import uwu.serenity.critter.stdlib.blocks.BlockBuilder;
import uwu.serenity.critter.stdlib.items.ItemBuilder;
import uwu.serenity.critter.utils.environment.EnvExecutor;
import uwu.serenity.critter.utils.environment.Environment;

import java.util.function.*;

// :333333
public class Transgenders {
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

    // Supplier wrapping is necessary to avoid loading client-only classes on the server
    static <BE extends BlockEntity, P> UnaryOperator<BlockEntityBuilder<BE, P>> instance(Supplier<BiFunction<MaterialManager, BE, BlockEntityInstance<? super BE>>> instanceFactory, Predicate<BE> shouldRender) {
        return b -> {
            EnvExecutor.runWhenOn(Environment.CLIENT, () -> () -> {
                b.onRegister(betype -> InstancedRenderRegistry.configure(betype)
                        .factory(instanceFactory.get())
                        .skipRender(be -> !shouldRender.test(be))
                        .apply());
            });
            return b;
        };
    }

    static <BE extends BlockEntity, P> UnaryOperator<BlockEntityBuilder<BE, P>> instanceController(Supplier<Supplier<BlockEntityInstancingController<? super BE>>> instanceController) {
        return b -> {
            EnvExecutor.runWhenOn(Environment.CLIENT, () -> () -> {
                b.onRegister(beType -> InstancedRenderRegistry.setController(beType, instanceController.get().get()));
            });
            return b;
        };
    }
}
