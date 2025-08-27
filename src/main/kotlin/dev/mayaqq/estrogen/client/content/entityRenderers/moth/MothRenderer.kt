package dev.mayaqq.estrogen.client.content.entityRenderers.moth

import com.mojang.blaze3d.vertex.PoseStack
import dev.mayaqq.estrogen.client.content.EstrogenRenderTypes
import dev.mayaqq.estrogen.client.content.EstrogenRenderer
import dev.mayaqq.estrogen.content.entities.MothEntity
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.resources.ResourceLocation

class MothRenderer(
    context: EntityRendererProvider.Context
) : MobRenderer<MothEntity, MothModel>(context, MothModel(context.bakeLayer(MothModel.LAYER_LOCATION)), 0.4f) {
    override fun getTextureLocation(entity: MothEntity): ResourceLocation = if (entity.isFuzzy()) FUZZY_TEXTURE else NORMAL_TEXTURE

    companion object {
        private val FUZZY_TEXTURE: ResourceLocation = ResourceLocation("estrogen", "textures/entity/moth/fuzzy.png")
        private val NORMAL_TEXTURE: ResourceLocation = ResourceLocation("estrogen", "textures/entity/moth/normal.png")
    }
}