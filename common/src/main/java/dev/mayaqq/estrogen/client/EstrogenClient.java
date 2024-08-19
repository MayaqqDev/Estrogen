package dev.mayaqq.estrogen.client;

import com.jozufozu.flywheel.backend.instancing.InstancedRenderRegistry;
import com.simibubi.create.CreateClient;
import com.simibubi.create.foundation.block.connected.CTModel;
import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour;
import com.simibubi.create.foundation.block.connected.SimpleCTBehaviour;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import dev.mayaqq.estrogen.client.config.ConfigSync;
import dev.mayaqq.estrogen.client.registry.EstrogenKeybinds;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderer;
import dev.mayaqq.estrogen.client.registry.blockRenderers.centrifuge.CentrifugeCogInstance;
import dev.mayaqq.estrogen.client.registry.blockRenderers.centrifuge.CentrifugeRenderer;
import dev.mayaqq.estrogen.client.registry.blockRenderers.cookieJar.CookieJarRenderer;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.DreamBlockRenderer;
import dev.mayaqq.estrogen.client.registry.entityRenderers.moth.MothRenderer;
import dev.mayaqq.estrogen.client.registry.trinkets.EstrogenPatchesRenderer;
import dev.mayaqq.estrogen.client.registry.trinkets.ThighHighRenderer;
import dev.mayaqq.estrogen.integrations.ears.EarsCompat;
import dev.mayaqq.estrogen.platform.ClientPlatform;
import dev.mayaqq.estrogen.registry.*;
import earth.terrarium.botarium.client.ClientHooks;
import earth.terrarium.botarium.util.CommonHooks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;

public class EstrogenClient {
    public static void init() {
        ConfigSync.cacheConfig();
        EstrogenRenderer.register();
        EstrogenPonderScenes.register();
        EstrogenKeybinds.register();
        // Trinket renderers
        EstrogenPatchesRenderer.register();
        ThighHighRenderer.register();
        // Block renderers
        ClientHooks.setRenderLayer(EstrogenBlocks.CENTRIFUGE.get(), RenderType.cutout());
        ClientHooks.setRenderLayer(EstrogenBlocks.COOKIE_JAR.get(), RenderType.cutout());
        ClientHooks.setRenderLayer(EstrogenBlocks.DORMANT_DREAM_BLOCK.get(), RenderType.translucent());
        ClientHooks.registerBlockEntityRenderers(EstrogenBlockEntities.CENTRIFUGE.get(), CentrifugeRenderer::new);
        ClientHooks.registerBlockEntityRenderers(EstrogenBlockEntities.COOKIE_JAR.get(), CookieJarRenderer::new);
        ClientHooks.registerBlockEntityRenderers(EstrogenBlockEntities.DREAM_BLOCK.get(), DreamBlockRenderer::new);

        ClientHooks.registerEntityRenderer(EstrogenEntities.MOTH, MothRenderer::new);

        CreateClient.MODEL_SWAPPER.getCustomBlockModels().register(EstrogenBlocks.DORMANT_DREAM_BLOCK.getId(), new CTModelProvider(new SimpleCTBehaviour(EstrogenSpriteShifts.DORMANT_DREAM_BLOCK)));

        EstrogenFluids.FLUIDS.stream().forEach(fluid -> ClientPlatform.fluidRenderLayerMap(fluid.get(), RenderType.translucent()));

        InstancedRenderRegistry.configure(EstrogenBlockEntities.CENTRIFUGE.get())
                .factory(CentrifugeCogInstance::new)
                .skipRender(be -> false)
                .apply();

        // mod compat
        if (CommonHooks.isModLoaded("ears")) {
            EarsCompat.boob();
        }
    }

    @Environment(EnvType.CLIENT)
    private record CTModelProvider(ConnectedTextureBehaviour behavior) implements NonNullFunction<BakedModel, BakedModel> {
        @Override
        public BakedModel apply(BakedModel bakedModel) {
            return new CTModel(bakedModel, behavior);
        }
    }
}
