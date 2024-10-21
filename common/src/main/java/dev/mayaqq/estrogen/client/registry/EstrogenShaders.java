package dev.mayaqq.estrogen.client.registry;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.function.Consumer;

public final class EstrogenShaders {
    @Nullable
    private static ShaderInstance dreamShader;
    @Nullable
    private static ShaderInstance depthAwareBlitShader;

    public static final ResourceLocation DREAM_TEXTURE_LOCATION = Estrogen.id("textures/entity/dream.png");

    private EstrogenShaders() {}

    @Nullable
    public static ShaderInstance getDreamShader() {
        return dreamShader;
    }

    @Nullable
    public static ShaderInstance getDepthAwareBlitShader() {
        return depthAwareBlitShader;
    }

    public static void register(CoreShaderCallback callback) {
        try {
            callback.accept(
                Estrogen.id("rendertype_estrogen_dream"),
                DefaultVertexFormat.BLOCK,
                program -> dreamShader = program
            );
//            callback.accept(
//                Estrogen.id("depth_aware_blit"),
//                DefaultVertexFormat.BLIT_SCREEN,
//                program -> depthAwareBlitShader = program
//            );
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public interface CoreShaderCallback {
        void accept(ResourceLocation id, VertexFormat format, Consumer<ShaderInstance> shaderConsumer) throws IOException;
    }
}