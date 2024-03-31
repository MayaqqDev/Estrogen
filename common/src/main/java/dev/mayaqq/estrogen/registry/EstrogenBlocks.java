package dev.mayaqq.estrogen.registry;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.mayaqq.estrogen.registry.blocks.CentrifugeBlock;
import dev.mayaqq.estrogen.registry.blocks.CookieJarBlock;
import dev.mayaqq.estrogen.registry.blocks.DreamBlock;
import dev.mayaqq.estrogen.registry.blocks.fluids.EstrogenLiquidBlock;
import dev.mayaqq.estrogen.registry.blocks.fluids.LavaLikeLiquidBlock;
import earth.terrarium.botarium.common.registry.fluid.BotariumLiquidBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class EstrogenBlocks {
    public static final ResourcefulRegistry<Block> BLOCKS = ResourcefulRegistries.create(BuiltInRegistries.BLOCK, "estrogen");
    public static final ResourcefulRegistry<Block> TRANSPARENT_BLOCKS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> CREATE_LIKE_BLOCKS = ResourcefulRegistries.create(BLOCKS);

    public static final RegistryEntry<CentrifugeBlock> CENTRIFUGE = CREATE_LIKE_BLOCKS.register("centrifuge", () -> new CentrifugeBlock(BlockBehaviour.Properties.copy(SharedProperties.copperMetal()).mapColor(MapColor.COLOR_ORANGE).noOcclusion()));

    public static final RegistryEntry<CookieJarBlock> COOKIE_JAR = CREATE_LIKE_BLOCKS.register("cookie_jar", () -> new CookieJarBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)));

    public static final RegistryEntry<Block> DREAM_BLOCK = BLOCKS.register("dream_block", () -> new DreamBlock(BlockBehaviour.Properties.copy(Blocks.END_GATEWAY).sound(EstrogenSoundTypes.DREAM_BLOCK)));
    public static final RegistryEntry<Block> DORMANT_DREAM_BLOCK = BLOCKS.register("dormant_dream_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BEACON)));

    public static final RegistryEntry<Block> MOLTEN_SLIME_BLOCK = BLOCKS.register("molten_slime", () -> new LavaLikeLiquidBlock(EstrogenFluidProperties.MOLTEN_SLIME, BlockBehaviour.Properties.copy(Blocks.LAVA).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryEntry<Block> TESTOSTERONE_MIXTURE_BLOCK = TRANSPARENT_BLOCKS.register("testosterone_mixture", () -> new BotariumLiquidBlock(EstrogenFluidProperties.TESTOSTERONE_MIXTURE, BlockBehaviour.Properties.copy(Blocks.WATER).mapColor(MapColor.TERRACOTTA_YELLOW)));
    public static final RegistryEntry<Block> LIQUID_ESTROGEN_BLOCK = TRANSPARENT_BLOCKS.register("liquid_estrogen", () -> new EstrogenLiquidBlock(EstrogenFluidProperties.LIQUID_ESTROGEN, BlockBehaviour.Properties.copy(Blocks.WATER).mapColor(MapColor.COLOR_CYAN)));
    public static final RegistryEntry<Block> FILTRATED_HORSE_URINE_BLOCK = TRANSPARENT_BLOCKS.register("filtrated_horse_urine", () -> new BotariumLiquidBlock(EstrogenFluidProperties.FILTRATED_HORSE_URINE, BlockBehaviour.Properties.copy(Blocks.WATER).mapColor(MapColor.TERRACOTTA_YELLOW)));
    public static final RegistryEntry<Block> HORSE_URINE_BLOCK = TRANSPARENT_BLOCKS.register("horse_urine", () -> new BotariumLiquidBlock(EstrogenFluidProperties.HORSE_URINE, BlockBehaviour.Properties.copy(Blocks.WATER).mapColor(MapColor.COLOR_YELLOW)));
    public static final RegistryEntry<Block> MOLTEN_AMETHYST_BLOCK = BLOCKS.register("molten_amethyst", () -> new LavaLikeLiquidBlock(EstrogenFluidProperties.MOLTEN_AMETHYST, BlockBehaviour.Properties.copy(Blocks.LAVA).mapColor(MapColor.COLOR_PURPLE)));

    public static void registerExtraProperties() {
        BlockStressDefaults.setDefaultImpact(CENTRIFUGE.getId(), 8.0);
    }
}