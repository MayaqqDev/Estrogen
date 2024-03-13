package dev.mayaqq.estrogen.client;

import com.jozufozu.flywheel.backend.instancing.InstancedRenderRegistry;
import dev.mayaqq.estrogen.client.registry.EstrogenKeybinds;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderer;
import dev.mayaqq.estrogen.client.registry.blockRenderers.centrifuge.CentrifugeCogInstance;
import dev.mayaqq.estrogen.client.registry.blockRenderers.centrifuge.CentrifugeRenderer;
import dev.mayaqq.estrogen.client.registry.blockRenderers.cookieJar.CookieJarRenderer;
import dev.mayaqq.estrogen.client.registry.trinkets.EstrogenPatchesRenderer;
import dev.mayaqq.estrogen.integrations.ears.EarsCompat;
import dev.mayaqq.estrogen.registry.EstrogenBlockEntities;
import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.EstrogenPonderScenes;
import earth.terrarium.botarium.client.ClientHooks;
import earth.terrarium.botarium.util.CommonHooks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class EstrogenClient {
    public static void init() {
        EstrogenRenderer.register();
        EstrogenPonderScenes.register();
        EstrogenKeybinds.register();
        EstrogenPatchesRenderer.register();
        ClientHooks.setRenderLayer(EstrogenBlocks.CENTRIFUGE.get(), RenderType.cutout());
        ClientHooks.registerBlockEntityRenderers(EstrogenBlockEntities.CENTRIFUGE.get(), CentrifugeRenderer::new);
        ClientHooks.registerBlockEntityRenderers(EstrogenBlockEntities.COOKIE_JAR.get(), CookieJarRenderer::new);

        InstancedRenderRegistry.configure(EstrogenBlockEntities.CENTRIFUGE.get())
                .factory(CentrifugeCogInstance::new)
                .skipRender(be -> false)
                .apply();

        // mod compat
        if (CommonHooks.isModLoaded("ears")) {
            EarsCompat.boob();
        }
    }
}
