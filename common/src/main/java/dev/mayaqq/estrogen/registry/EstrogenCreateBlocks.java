package dev.mayaqq.estrogen.registry;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.mayaqq.estrogen.registry.blocks.CentrifugeBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.MapColor;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static dev.mayaqq.estrogen.Estrogen.REGISTRATE;

public class EstrogenCreateBlocks {
    public static final BlockEntry<CentrifugeBlock> CENTRIFUGE = REGISTRATE.block("centrifuge", CentrifugeBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(p -> p.mapColor(MapColor.COLOR_ORANGE))
            .transform(BlockStressDefaults.setImpact(8.0))
            .item()
            .transform(customItemModel())
            .addLayer(() -> RenderType::cutout)
            .register();

    public static void register() {
    }
}