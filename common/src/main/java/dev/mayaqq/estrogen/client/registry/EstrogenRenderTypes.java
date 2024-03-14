package dev.mayaqq.estrogen.client.registry;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.DreamBlockRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;

public class EstrogenRenderTypes extends RenderType {
    public static final ShaderStateShard RENDERTYPE_DREAM_BLOCK_SHADER = new ShaderStateShard(GameRenderer::getRendertypeEndGatewayShader);
    public static RenderType DREAM_BLOCK = create(
            "dream_block",
            DefaultVertexFormat.POSITION,
            VertexFormat.Mode.QUADS,
            256,
            false,
            false,
            RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_DREAM_BLOCK_SHADER)
                    .setTextureState(MultiTextureStateShard.builder()
                            .add(DreamBlockRenderer.BACKGROUND_LOCATION, false, false)
                            .add(DreamBlockRenderer.DREAM_BLOCK_LOCATION, false, false)
                            .build())
                    .createCompositeState(false)
    );

    public EstrogenRenderTypes(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }
}
