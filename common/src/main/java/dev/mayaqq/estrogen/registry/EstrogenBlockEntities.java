package dev.mayaqq.estrogen.registry;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.registry.blockRenderers.centrifuge.CentrifugeCogInstance;
import dev.mayaqq.estrogen.client.registry.blockRenderers.centrifuge.CentrifugeRenderer;
import dev.mayaqq.estrogen.client.registry.blockRenderers.cookieJar.CookieJarInstance;
import dev.mayaqq.estrogen.client.registry.blockRenderers.cookieJar.CookieJarRenderer;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.DreamBlockInstance;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.DreamBlockRenderer;
import dev.mayaqq.estrogen.registry.blockEntities.CentrifugeBlockEntity;
import dev.mayaqq.estrogen.registry.blockEntities.CookieJarBlockEntity;
import dev.mayaqq.estrogen.registry.blockEntities.DreamBlockEntity;
import uwu.serenity.critter.stdlib.blockEntities.BlockEntityEntry;
import uwu.serenity.critter.stdlib.blockEntities.BlockEntityRegistrar;

public class EstrogenBlockEntities {

    public static final BlockEntityRegistrar BLOCK_ENTITIES = BlockEntityRegistrar.create(Estrogen.MOD_ID);

    public static final BlockEntityEntry<CentrifugeBlockEntity> CENTRIFUGE = BLOCK_ENTITIES.entry("centrifuge", CentrifugeBlockEntity::new)
        .validBlock(EstrogenBlocks.CENTRIFUGE)
        .renderer(() -> CentrifugeRenderer::new)
        .transform(Transgenders.instance(() -> CentrifugeCogInstance::new, true))
        .register();

    public static final BlockEntityEntry<DreamBlockEntity> DREAM_BLOCK = BLOCK_ENTITIES.entry("dream_block", DreamBlockEntity::new)
        .validBlock(EstrogenBlocks.DREAM_BLOCK)
        .renderer(() -> DreamBlockRenderer::new)
        .transform(Transgenders.instanceController(() -> () -> DreamBlockInstance.CONTROLLER))
        .register();

    public static final BlockEntityEntry<CookieJarBlockEntity> COOKIE_JAR = BLOCK_ENTITIES.entry("cookie_jar", CookieJarBlockEntity::new)
        .validBlock(EstrogenBlocks.COOKIE_JAR)
        .renderer(() -> CookieJarRenderer::new)
        .transform(Transgenders.instance(() -> CookieJarInstance::new, false))
        .register();
}
