package dev.mayaqq.estrogen.client.registry;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class EstrogenRenderType extends RenderType {

    public static final Function<ResourceLocation, RenderType> DREAM_BLOCK = Util.memoize(tex ->
        RenderType.create(
            "dream_block",
            DefaultVertexFormat.BLOCK,
            VertexFormat.Mode.QUADS,
            256,
            false,
            false,
            CompositeState.builder()
                .setShaderState(new ShaderStateShard(EstrogenShaders::getDreamShader))
                .setTextureState(new RenderStateShard.TextureStateShard(tex, false, false))
                .createCompositeState(false)
        )
    );

    public EstrogenRenderType(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }
}
