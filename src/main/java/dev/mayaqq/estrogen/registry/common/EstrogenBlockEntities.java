package dev.mayaqq.estrogen.registry.common;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import dev.mayaqq.estrogen.registry.client.blockRenderers.centrifuge.CentrifugeCogInstance;
import dev.mayaqq.estrogen.registry.client.blockRenderers.centrifuge.CentrifugeRenderer;
import dev.mayaqq.estrogen.registry.common.blockEntities.CentrifugeBlockEntity;

import static dev.mayaqq.estrogen.Estrogen.REGISTRATE;

public class EstrogenBlockEntities {

    public static final BlockEntityEntry<CentrifugeBlockEntity> CENTRIFUGE = REGISTRATE
            .blockEntity("centrifuge", CentrifugeBlockEntity::new)
            .instance(() -> CentrifugeCogInstance::new)
            .validBlocks(EstrogenBlocks.CENTRIFUGE)
            .renderer(() -> CentrifugeRenderer::new)
            .register();

    public static void register() {}
}
