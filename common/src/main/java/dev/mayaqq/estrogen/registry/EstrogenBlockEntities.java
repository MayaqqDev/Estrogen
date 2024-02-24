package dev.mayaqq.estrogen.registry;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import dev.mayaqq.estrogen.client.registry.blockRenderers.centrifuge.CentrifugeCogInstance;
import dev.mayaqq.estrogen.client.registry.blockRenderers.centrifuge.CentrifugeRenderer;
import dev.mayaqq.estrogen.registry.blockEntities.CentrifugeBlockEntity;

import static dev.mayaqq.estrogen.Estrogen.REGISTRATE;

public class EstrogenBlockEntities {
    public static final BlockEntityEntry<CentrifugeBlockEntity> CENTRIFUGE = REGISTRATE
            .blockEntity("centrifuge", CentrifugeBlockEntity::new)
            .instance(() -> CentrifugeCogInstance::new)
            .validBlocks(EstrogenCreateBlocks.CENTRIFUGE)
            .renderer(() -> CentrifugeRenderer::new)
            .register();

    public static void register() {}
}
