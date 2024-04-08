package dev.mayaqq.estrogen.registry;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.mayaqq.estrogen.registry.blocks.*;
import dev.mayaqq.estrogen.registry.blocks.fluids.EstrogenLiquidBlock;
import dev.mayaqq.estrogen.registry.blocks.fluids.LavaLikeLiquidBlock;
import dev.mayaqq.estrogen.utils.StatePredicates;
import earth.terrarium.botarium.common.registry.fluid.BotariumLiquidBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class EstrogenBlocks {
    public static final ResourcefulRegistry<Block> BLOCKS = ResourcefulRegistries.create(BuiltInRegistries.BLOCK, "estrogen");
    public static final ResourcefulRegistry<Block> TRANSPARENT_BLOCKS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> CREATE_LIKE_BLOCKS = ResourcefulRegistries.create(BLOCKS);

    public static final RegistryEntry<CentrifugeBlock> CENTRIFUGE = CREATE_LIKE_BLOCKS.register("centrifuge", () -> new CentrifugeBlock(BlockBehaviour.Properties.copy(SharedProperties.copperMetal()).requiresCorrectToolForDrops().mapColor(MapColor.COLOR_ORANGE).noOcclusion()));

    public static final RegistryEntry<CookieJarBlock> COOKIE_JAR = CREATE_LIKE_BLOCKS.register("cookie_jar", () -> new CookieJarBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)));

    public static final RegistryEntry<Block> DREAM_BLOCK = BLOCKS.register("dream_block", () -> new DreamBlock(BlockBehaviour.Properties.copy(Blocks.END_GATEWAY).isSuffocating(StatePredicates::never).sound(EstrogenSoundTypes.DREAM_BLOCK)));
    public static final RegistryEntry<Block> DORMANT_DREAM_BLOCK = BLOCKS.register("dormant_dream_block", () -> new DormantDreamBlock(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DIAMOND)
                    .instrument(NoteBlockInstrument.HAT)
                    .strength(3.0F)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isRedstoneConductor(StatePredicates::always)
                    .sound(EstrogenSoundTypes.DORMANT_DREAM_BLOCK)
                    .isValidSpawn(StatePredicates::never)
                    .isSuffocating(StatePredicates::never)
                    .isViewBlocking(StatePredicates::never)
    ));

    public static final RegistryEntry<Block> ESTROGEN_PILL_BLOCK = BLOCKS.register("estrogen_pill_block", () -> new EstrogenPillBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1.0F, 1.0F)));

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