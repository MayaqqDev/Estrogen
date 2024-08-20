package dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.mayaqq.estrogen.Estrogen;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.function.Consumer;

public final class DreamBlockShader {
    @Nullable
    private static ShaderInstance shaderInstance;
    public static final ResourceLocation DREAM_TEXTURE_LOCATION = Estrogen.id("textures/entity/dream.png");

    private DreamBlockShader() {}

    @Nullable
    public static ShaderInstance getDreamShader() {
        return shaderInstance;
    }

    public static void register(CoreShaderCallback callback) {
        try {
            callback.accept(
                Estrogen.id("rendertype_estrogen_dream"),
                DefaultVertexFormat.BLOCK,
                program -> {
                    shaderInstance = program;
                });
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public interface CoreShaderCallback {
        void accept(ResourceLocation id, VertexFormat format, Consumer<ShaderInstance> shaderConsumer) throws IOException;
    }
}