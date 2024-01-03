package dev.mayaqq.estrogen.registry.common;

import dev.architectury.core.block.ArchitecturyLiquidBlock;
import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;
import dev.architectury.core.item.ArchitecturyBucketItem;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;

public class EstrogenFluidAttributes {

    public static Registrar<Item> FLUID_ITEMS = Estrogen.MANAGER.get().get(Registries.ITEM);
    public static Registrar<Block> FLUID_BLOCKS = Estrogen.MANAGER.get().get(Registries.BLOCK);

    public static final SimpleArchitecturyFluidAttributes MOLTEN_SLIME = lavaLike("molten_slime", EstrogenFluids.MOLTEN_SLIME.still(), EstrogenFluids.MOLTEN_SLIME.flowing(), 144238144);
    public static final SimpleArchitecturyFluidAttributes TESTOSTERONE_MIXTURE = waterLike("testosterone_mixture", EstrogenFluids.TESTOSTERONE_MIXTURE.still(), EstrogenFluids.TESTOSTERONE_MIXTURE.flowing(), 154148010);
    public static final SimpleArchitecturyFluidAttributes LIQUID_ESTROGEN = waterLike("liquid_estrogen", EstrogenFluids.LIQUID_ESTROGEN.still(), EstrogenFluids.LIQUID_ESTROGEN.flowing(), 104164161);
    public static final SimpleArchitecturyFluidAttributes FILTRATED_HORSE_URINE = waterLike("filtrated_horse_urine", EstrogenFluids.FILTRATED_HORSE_URINE.still(), EstrogenFluids.FILTRATED_HORSE_URINE.flowing(), 0xE1E114);
    public static final SimpleArchitecturyFluidAttributes HORSE_URINE = waterLike("horse_urine", EstrogenFluids.HORSE_URINE.still(), EstrogenFluids.HORSE_URINE.flowing(), 0x8C8B05);
    public static final SimpleArchitecturyFluidAttributes MOLTEN_AMETHYST = lavaLike("molten_amethyst", EstrogenFluids.MOLTEN_AMETHYST.still(), EstrogenFluids.MOLTEN_AMETHYST.flowing(), 0xAE7AFD);

    public static void register() {}

    public static SimpleArchitecturyFluidAttributes waterLike(String id, RegistrySupplier<? extends FlowingFluid> still, RegistrySupplier<? extends FlowingFluid> flowing, int color) {
        return SimpleArchitecturyFluidAttributes.of(still, flowing)
                .blockSupplier(() -> FLUID_BLOCKS.register(Estrogen.id(id + "_block"), () -> new ArchitecturyLiquidBlock(still, BlockBehaviour.Properties.copy(Blocks.WATER))))
                .bucketItemSupplier(() -> FLUID_ITEMS.register(Estrogen.id(id + "_bucket"), () -> new ArchitecturyBucketItem(still, new Item.Properties().arch$tab(Estrogen.ESTROGEN_GROUP.get()))))
                .flowingTexture(new ResourceLocation("minecraft", "block/water_flow"))
                .sourceTexture(new ResourceLocation("minecraft", "block/water_still"))
                .color(color)
                ;
    }

    public static SimpleArchitecturyFluidAttributes lavaLike(String id, RegistrySupplier<? extends FlowingFluid> still, RegistrySupplier<? extends FlowingFluid> flowing, int color) {
        return SimpleArchitecturyFluidAttributes.of(still, flowing)
                .blockSupplier(() -> FLUID_BLOCKS.register(Estrogen.id(id + "_block"), () -> new ArchitecturyLiquidBlock(still, BlockBehaviour.Properties.copy(Blocks.LAVA))))
                .bucketItemSupplier(() -> FLUID_ITEMS.register(Estrogen.id(id + "_bucket"), () -> new ArchitecturyBucketItem(still, new Item.Properties().arch$tab(Estrogen.ESTROGEN_GROUP.get()))))
                .flowingTexture(Estrogen.id("block/blank_lava/blank_lava_flow"))
                .sourceTexture(Estrogen.id("block/blank_lava/blank_lava_still"))
                .color(color)
                ;
    }
}
