package dev.mayaqq.estrogen.client.registry;

import com.mojang.blaze3d.vertex.VertexFormat;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class EstrogenRenderTypes extends RenderType {

    public static final ResourceLocation DREAM_TEXTURE_LOCATION = Estrogen.id("textures/dream.png");

    // This mostly does nothing
    public static final Function<ResourceLocation, RenderType> DREAM_BLOCK = tex -> RenderType.create(
        "dream_block",
        EstrogenRenderer.POS_TEX_NORMAL_FORMAT,
        VertexFormat.Mode.QUADS,
        256,
        false,
        false,
        CompositeState
            .builder()
            .setShaderState(RenderStateShard.POSITION_TEX_SHADER)
            .setTextureState(new RenderStateShard.TextureStateShard(tex, false, false))
            .createCompositeState(false));


    public EstrogenRenderTypes(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }
}
