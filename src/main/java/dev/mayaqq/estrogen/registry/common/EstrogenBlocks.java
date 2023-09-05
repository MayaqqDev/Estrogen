package dev.mayaqq.estrogen.registry.common;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.mayaqq.estrogen.registry.common.blocks.CentrifugeBlock;
import net.minecraft.block.MapColor;
import net.minecraft.client.render.RenderLayer;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static dev.mayaqq.estrogen.Estrogen.REGISTRATE;

public class EstrogenBlocks {

    static {
        REGISTRATE.creativeModeTab(() -> EstrogenItems.ESTROGEN_GROUP);
    }

    public static final BlockEntry<CentrifugeBlock> CENTRIFUGE = REGISTRATE.block("centrifuge", CentrifugeBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(p -> p.mapColor(MapColor.ORANGE))
            .transform(pickaxeOnly())
            .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
            .transform(BlockStressDefaults.setImpact(8.0))
            .item()
            .transform(customItemModel())
            .addLayer(() -> RenderLayer::getCutout)
            .register();

    public static void register() {}
}