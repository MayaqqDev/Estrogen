package dev.mayaqq.estrogen.registry;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.backend.instancing.InstancedRenderRegistry;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstancingController;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.blocks.fluids.LavaLikeLiquidBlock;
import dev.mayaqq.estrogen.utils.registry.EstrogenFluidBuilder;
import earth.terrarium.baubly.Baubly;
import earth.terrarium.baubly.client.BaubleRenderer;
import earth.terrarium.baubly.client.BaublyClient;
import earth.terrarium.baubly.common.Bauble;
import earth.terrarium.botarium.common.registry.fluid.BotariumFlowingFluid;
import earth.terrarium.botarium.common.registry.fluid.BotariumLiquidBlock;
import earth.terrarium.botarium.common.registry.fluid.BotariumSourceFluid;
import earth.terrarium.botarium.common.registry.fluid.FluidBucketItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.Nullable;
import uwu.serenity.critter.platform.PlatformUtils;
import uwu.serenity.critter.stdlib.blockEntities.BlockEntityBuilder;
import uwu.serenity.critter.stdlib.blocks.BlockBuilder;
import uwu.serenity.critter.stdlib.items.ItemBuilder;
import uwu.serenity.critter.utils.SafeSupplier;
import uwu.serenity.critter.utils.environment.EnvExecutor;
import uwu.serenity.critter.utils.environment.Environment;

import java.util.function.*;

// :333333
public class Transgenders {

    // Blocks
    static <B extends Block, P> UnaryOperator<BlockBuilder<B, P>> stressImpact(double impact) {
        return b -> {
            BlockStressDefaults.setDefaultImpact(b.getId(), impact);
            return b;
        };
    }

    // Items
    static <I extends Item, P> UnaryOperator<ItemBuilder<I, P>> tooltip(Function<Item, TooltipModifier> tooltipFactory) {
        return b -> {
            TooltipModifier.REGISTRY.registerDeferred(b.getId(), tooltipFactory);
            return b;
        };
    }

    static <I extends Item, P> UnaryOperator<ItemBuilder<I, P>> standardTooltip() {
        return tooltip(item -> new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE));
    }

    static <I extends Item & Bauble, P> UnaryOperator<ItemBuilder<I, P>> bauble(@Nullable SafeSupplier<BaubleRenderer> rendererFactory) {
        return b -> {
            if(rendererFactory != null && PlatformUtils.getEnvironment() == Environment.CLIENT) {
                b.onRegister(item -> BaublyClient.registerBaubleRenderer(item, rendererFactory.getSafe()));
            }
            b.onRegister(Baubly::registerBauble);
            return b;
        };
    }

    // Block Entities
    // Supplier wrapping is necessary to avoid loading client-only classes on the server
    static <BE extends BlockEntity, P> UnaryOperator<BlockEntityBuilder<BE, P>> instance(Supplier<BiFunction<MaterialManager, BE, BlockEntityInstance<? super BE>>> instanceFactory, boolean alwaysRender) {
        return b -> {
            EnvExecutor.runWhenOn(Environment.CLIENT, () -> () -> {
                b.onRegister(betype -> InstancedRenderRegistry.configure(betype)
                        .factory(instanceFactory.get())
                        .skipRender(be -> !alwaysRender)
                        .apply());
            });
            return b;
        };
    }

    static <BE extends BlockEntity, P> UnaryOperator<BlockEntityBuilder<BE, P>> instanceController(SafeSupplier<BlockEntityInstancingController<? super BE>> instanceController) {
        return b -> {
            EnvExecutor.runWhenOn(Environment.CLIENT, () -> () -> {
                b.onRegister(beType -> InstancedRenderRegistry.setController(beType, instanceController.getSafe()));
            });
            return b;
        };
    }

    // Fluids
    static <F extends BotariumSourceFluid, F2 extends BotariumFlowingFluid, P> UnaryOperator<EstrogenFluidBuilder<F, F2, P>> lavaLikeFluid(MapColor mapColor, int tint) {
        return b -> b.properties(p -> p.still(Estrogen.id("block/blank_lava/blank_lava_still"))
                .flowing(Estrogen.id("block/blank_lava/blank_lava_flow"))
                .overlay(Estrogen.id("block/blank_lava/blank_lava_flow"))
                .screenOverlay(new ResourceLocation("textures/misc/underwater.png"))
                .tintColor(tint)
                .temperature(10000)
                .canConvertToSource(false)
                .canDrown(false)
                .canExtinguish(false)
                .canHydrate(false)
                .canPushEntity(true)
                .canSwim(false)
                .lightLevel(15)
                .motionScale(0.004)
                .pathType(BlockPathTypes.LAVA)
                .tickRate(10)
                .viscosity(1500)
                .density(1500))
            .block(LavaLikeLiquidBlock::new)
            .copyProperties(() -> Blocks.LAVA)
            .properties(p -> p.mapColor(mapColor))
            .build();
    }

    static <F extends BotariumSourceFluid, F2 extends BotariumFlowingFluid, P> UnaryOperator<EstrogenFluidBuilder<F, F2, P>> waterLikeFluid(MapColor mapColor, int tintColor) {
        return b -> b.properties(p -> p.still(new ResourceLocation("minecraft", "block/water_still"))
                .flowing(new ResourceLocation("minecraft", "block/water_flow"))
                .overlay(new ResourceLocation("minecraft", "block/water_flow"))
                .screenOverlay(new ResourceLocation("textures/misc/underwater.png"))
                .tintColor(tintColor)
                .canConvertToSource(false)
                .canDrown(true)
                .canExtinguish(true)
                .canHydrate(true)
                .canPushEntity(true)
                .canSwim(true)
                .viscosity(1500)
                .density(1500))
            .renderType(() -> RenderType::translucent)
            .block(BotariumLiquidBlock::new)
            .copyProperties(() -> Blocks.WATER)
            .properties(p -> p.mapColor(mapColor))
            .build();
    }

    static <F extends BotariumSourceFluid, F2 extends BotariumFlowingFluid, P> UnaryOperator<EstrogenFluidBuilder<F, F2, P>> simpleBucket() {
        return b -> b.bucket(FluidBucketItem::new)
            .properties(EstrogenItems::bucketProperties)
            .build();
    }




}
