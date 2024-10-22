package dev.mayaqq.estrogen.registry;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.blocks.fluids.EstrogenLiquidBlock;
import dev.mayaqq.estrogen.utils.EstrogenColors;
import dev.mayaqq.estrogen.utils.registry.EstrogenFluidEntry;
import dev.mayaqq.estrogen.utils.registry.EstrogenFluidRegistrar;
import earth.terrarium.botarium.common.registry.fluid.BotariumFlowingFluid;
import earth.terrarium.botarium.common.registry.fluid.BotariumSourceFluid;
import earth.terrarium.botarium.common.registry.fluid.FluidBucketItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;

public class EstrogenFluids {

    public static final EstrogenFluidRegistrar FLUIDS = EstrogenFluidRegistrar.create(Estrogen.REGISTRIES);


    // Setting all properties manually here
    public static final EstrogenFluidEntry<BotariumSourceFluid, BotariumFlowingFluid> LIQUID_ESTROGEN =
        FLUIDS.entry("liquid_estrogen", BotariumSourceFluid::new, BotariumFlowingFluid::new)
            .properties(p -> p.still(Estrogen.id("block/liquid_estrogen/liquid_estrogen_still"))
                .flowing(Estrogen.id("block/liquid_estrogen/liquid_estrogen_flow"))
                .overlay(Estrogen.id("block/liquid_estrogen/liquid_estrogen_flow"))
                .screenOverlay(new ResourceLocation("textures/misc/underwater.png"))
                .canConvertToSource(false)
                .canDrown(true)
                .canExtinguish(true)
                .canHydrate(true)
                .canPushEntity(true)
                .canSwim(true)
                .viscosity(1500)
                .density(1500))
            .renderType(() -> RenderType::translucent)
            .block(EstrogenLiquidBlock::new)
            .copyProperties(() -> Blocks.WATER)
            .properties(p -> p.mapColor(MapColor.COLOR_CYAN))
            .build()
            .bucket(FluidBucketItem::new)
            .properties(p -> p.craftRemainder(Items.BUCKET)
                .stacksTo(1)
                .rarity(Rarity.RARE))
            .build()
            .register();

    // Simple fluids done with transformers
    // Lava like
    public static final EstrogenFluidEntry<BotariumSourceFluid, BotariumFlowingFluid> MOLTEN_SLIME =
        FLUIDS.entry("molten_slime", BotariumSourceFluid::new, BotariumFlowingFluid::new)
            .transform(Transgenders.lavaLikeFluid(MapColor.COLOR_GREEN, EstrogenColors.MOLTEN_SLIME.value))
            .transform(Transgenders.simpleBucket())
            .register();

    public static final EstrogenFluidEntry<BotariumSourceFluid, BotariumFlowingFluid> MOLTEN_AMETHYST =
        FLUIDS.entry("molten_amethyst", BotariumSourceFluid::new, BotariumFlowingFluid::new)
            .transform(Transgenders.lavaLikeFluid(MapColor.COLOR_PURPLE, EstrogenColors.MOLTEN_AMETHYST.value))
            .transform(Transgenders.simpleBucket())
            .register();

    // Wate like
    public static final EstrogenFluidEntry<BotariumSourceFluid, BotariumFlowingFluid> TESTOSTERONE_MIXTURE =
        FLUIDS.entry("testosterone_mixture", BotariumSourceFluid::new, BotariumFlowingFluid::new)
            .transform(Transgenders.waterLikeFluid(MapColor.TERRACOTTA_YELLOW, EstrogenColors.TESTOSTERONE_MIXTURE.value))
            .transform(Transgenders.simpleBucket())
            .register();

    public static final EstrogenFluidEntry<BotariumSourceFluid, BotariumFlowingFluid> FILTRATED_HORSE_URINE =
        FLUIDS.entry("filtrated_horse_urine", BotariumSourceFluid::new, BotariumFlowingFluid::new)
            .transform(Transgenders.waterLikeFluid(MapColor.TERRACOTTA_YELLOW, EstrogenColors.FILTRATED_HORSE_URINE.value))
            .transform(Transgenders.simpleBucket())
            .register();

    public static final EstrogenFluidEntry<BotariumSourceFluid, BotariumFlowingFluid> HORSE_URINE =
        FLUIDS.entry("horse_urine", BotariumSourceFluid::new, BotariumFlowingFluid::new)
            .transform(Transgenders.waterLikeFluid(MapColor.COLOR_YELLOW, EstrogenColors.HORSE_URINE.value))
            .transform(Transgenders.simpleBucket())
            .register();

    public static final EstrogenFluidEntry<BotariumSourceFluid, BotariumFlowingFluid> GENDER_FLUID =
        FLUIDS.entry("gender_fluid", BotariumSourceFluid::new, BotariumFlowingFluid::new)
            .transform(Transgenders.waterLikeFluid(MapColor.COLOR_CYAN, EstrogenColors.FILTRATED_HORSE_URINE.value))
            .transform(Transgenders.simpleBucket())
            .register();


}