package dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.flywheel.iris;

import com.jozufozu.flywheel.backend.ShadersModHandler;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.irisshaders.iris.vertices.BlockSensitiveBufferBuilder;
import net.minecraft.client.renderer.RenderType;

public class ShaderHelper {

    public static void prepareBufferBuilder(BufferBuilder builder, short blockId, RenderType renderType) {
        if(ShadersModHandler.isShaderPackInUse()) {
            if(builder instanceof BlockSensitiveBufferBuilder sensitive) {
               // sensitive.beginBlock(20000, RenderType.cutout(), );
            }
        }
    }
}
