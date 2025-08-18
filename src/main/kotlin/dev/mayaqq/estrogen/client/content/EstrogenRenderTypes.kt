package dev.mayaqq.estrogen.client.content

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import dev.mayaqq.cynosure.client.render.BufferOutputStage
import dev.mayaqq.cynosure.client.render.fixed
import dev.mayaqq.estrogen.client.content.blockRenderers.dreamBlock.texture.DynamicDreamTexture
import net.minecraft.client.renderer.RenderStateShard
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.RenderType.CompositeState

object EstrogenRenderTypes {
    val DREAM_BLOCK: RenderType = RenderType.create(
        "estrogen:dream_block",
        DefaultVertexFormat.BLOCK,
        VertexFormat.Mode.QUADS,
        512,
        false,
        false,
        CompositeState.builder()
            .setShaderState(RenderStateShard.ShaderStateShard(EstrogenRenderer::dreamBlockShader))
            .setTextureState(RenderStateShard.TextureStateShard(DynamicDreamTexture.ID, false, false))
            .setOutputState(EstrogenRenderer.SHADER_BYPASS)
            .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
            .createCompositeState(false)
    ).fixed(BufferOutputStage.BLOCK_ENTITY)
}