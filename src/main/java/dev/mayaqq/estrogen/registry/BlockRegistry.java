package dev.mayaqq.estrogen.registry;

import dev.mayaqq.estrogen.registry.blockEntities.CentrifugeBlockEntity;
import dev.mayaqq.estrogen.registry.blocks.CentrifugeBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import static dev.mayaqq.estrogen.Estrogen.id;

public class BlockRegistry {
    public static Block MOLTEN_SLIME;
    public static Block TESTOSTERONE_MIXTURE;
    public static Block LIQUID_ESTROGEN;
    public static Block HORSE_URINE;
    public static Block FILTRATED_HORSE_URINE;

    public static Block CENTRIFUGE;
    public static BlockEntityType<CentrifugeBlockEntity> CENTRIFUGE_BLOCK_ENTITY;
    public static Item CENTRIFUGE_ITEM;

    public static void register() {
        MOLTEN_SLIME = Registry.register(Registry.BLOCK, id("molten_slime"), new FluidBlock(FluidRegistry.STILL_MOLTEN_SLIME, Block.Settings.copy(Blocks.LAVA)));
        TESTOSTERONE_MIXTURE = Registry.register(Registry.BLOCK, id("testosterone_mixture"), new FluidBlock(FluidRegistry.STILL_TESTOSTERONE_MIXTURE, Block.Settings.copy(Blocks.WATER)));
        LIQUID_ESTROGEN = Registry.register(Registry.BLOCK, id("liquid_estrogen"), new FluidBlock(FluidRegistry.STILL_LIQUID_ESTROGEN, Block.Settings.copy(Blocks.WATER)));
        HORSE_URINE = Registry.register(Registry.BLOCK, id("horse_urine"), new FluidBlock(FluidRegistry.STILL_HORSE_URINE, Block.Settings.copy(Blocks.WATER)));
        FILTRATED_HORSE_URINE = Registry.register(Registry.BLOCK, id("filtrated_horse_urine"), new FluidBlock(FluidRegistry.STILL_FILTRATED_HORSE_URINE, Block.Settings.copy(Blocks.WATER)));

        CENTRIFUGE = Registry.register(Registry.BLOCK, id("centrifuge"), new CentrifugeBlock(Block.Settings.copy(Blocks.IRON_BLOCK)));
        CENTRIFUGE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, id("centrifuge_block_entity"), FabricBlockEntityTypeBuilder.create(CentrifugeBlockEntity::new, CENTRIFUGE).build());
        CENTRIFUGE_ITEM = Registry.register(Registry.ITEM, id("centrifuge"), new BlockItem(CENTRIFUGE, new FabricItemSettings()));
    }
}