package dev.mayaqq.estrogen.client.content

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import dev.mayaqq.cynosure.client.isShaderPackInUse
import dev.mayaqq.cynosure.client.render.BufferOutputStage
import dev.mayaqq.cynosure.client.render.fixed
import dev.mayaqq.estrogen.client.content.blockRenderers.dreamBlock.texture.DynamicDreamTexture
import net.minecraft.Util
import net.minecraft.client.renderer.RenderStateShard
import net.minecraft.client.renderer.RenderStateShard.ShaderStateShard
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.RenderType.CompositeState
import net.minecraft.resources.ResourceLocation

object EstrogenRenderTypes {

    val DREAM_BLOCK: RenderType = RenderType.create(
        "estrogen:dream_block",
        DefaultVertexFormat.BLOCK,
        VertexFormat.Mode.QUADS,
        512,
        false,
        false,
        CompositeState.builder()
            .setShaderState(ShaderStateShard(EstrogenRenderer::dreamBlockShader))
            .setTextureState(TextureStateShard(DynamicDreamTexture.ID, false, false))
            .setOutputState(EstrogenRenderer.SHADER_BYPASS)
            .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
            .createCompositeState(false)
    ).fixed(BufferOutputStage.BLOCK_ENTITY)

    private val ENTITY_TRANSLUCENT_NO_DIFFUSE = Util.memoize<ResourceLocation, RenderType> { texture ->
        RenderType.create(
            "entity_translucent_no_diffuse",
            DefaultVertexFormat.NEW_ENTITY,
            VertexFormat.Mode.QUADS,
            256,
            true,
            true,
            CompositeState.builder()
                .setShaderState(ShaderStateShard(EstrogenRenderer::renderTypeEntityTranslucentNoDiffuseShader))
                .setTextureState(TextureStateShard(texture, false, false))
                .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                .setCullState(RenderStateShard.NO_CULL)
                .setLightmapState(RenderStateShard.LIGHTMAP)
                .setOverlayState(RenderStateShard.OVERLAY)
                .createCompositeState(true)
        )
    }

    private val ENTITY_CUTOUT_NO_DIFFUSE = Util.memoize<ResourceLocation, RenderType> { texture ->
        RenderType.create(
            "entity_cutout_no_diffuse",
            DefaultVertexFormat.NEW_ENTITY,
            VertexFormat.Mode.QUADS,
            256,
            true,
            false,
            CompositeState.builder()
                .setShaderState(ShaderStateShard(EstrogenRenderer::renderTypeEntityCutoutNoDiffuseShader))
                .setTextureState(TextureStateShard(texture, false, false))
                .setTransparencyState(RenderStateShard.NO_TRANSPARENCY)
                .setCullState(RenderStateShard.CULL)
                .setLightmapState(RenderStateShard.LIGHTMAP)
                .setOverlayState(RenderStateShard.OVERLAY)
                .createCompositeState(true)
        )
    }

    fun entityTranslucentNoDiffuse(texture: ResourceLocation): RenderType {
        return if (isShaderPackInUse) RenderType.entityTranslucent(texture) else ENTITY_TRANSLUCENT_NO_DIFFUSE.apply(texture)
    }

    fun entityCutoutNoDiffuse(texture: ResourceLocation): RenderType {
        return if (isShaderPackInUse) RenderType.entityCutout(texture) else ENTITY_CUTOUT_NO_DIFFUSE.apply(texture)
    }
}