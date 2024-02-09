package dev.mayaqq.estrogen.platformSpecific;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.mayaqq.estrogen.registry.common.blockEntities.CentrifugeBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import org.jetbrains.annotations.Contract;

public class CentrifugeRendererRenderSafe {
    private CentrifugeRendererRenderSafe() {
    }

    @Contract
    @ExpectPlatform
    public static void renderSafe(CentrifugeBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        throw new UnsupportedOperationException();
    }
}
