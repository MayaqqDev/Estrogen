package dev.mayaqq.estrogen.utils.render

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import dev.engine_room.flywheel.api.material.Material
import dev.engine_room.flywheel.api.model.Model
import dev.engine_room.flywheel.lib.material.Materials
import dev.engine_room.flywheel.lib.material.SimpleMaterial
import dev.engine_room.flywheel.lib.model.SimpleModel
import dev.engine_room.flywheel.lib.util.RendererReloadCache
import dev.engine_room.flywheel.lib.vertex.FullVertexView
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack

/**
 * This will probably be moved to cynosure
 */

private val ITEM_GLINT: Material = SimpleMaterial.builderOf(Materials.GLINT)
    .light(EstrogenFlywheelShaders.FULL_BRIGHT)
    .build()

private val FLYWHEEL_ABUSE: RendererReloadCache<Unit, MutableMap<ItemCacheKey, Model>> = RendererReloadCache { mutableMapOf() }
private val THREAD_LOCAL: ThreadLocal<ThreadLocalObjects> = ThreadLocal.withInitial(::ThreadLocalObjects)
private val GLINT_TYPES: Set<RenderType> = setOf(
    RenderType.glint(),
    RenderType.armorEntityGlint(),
    RenderType.entityGlint(),
    RenderType.glint(),
    RenderType.glintTranslucent(),
    RenderType.armorEntityGlint()
)

private data class ItemCacheKey(
    val model: BakedModel,
    val itemDisplayContext: ItemDisplayContext
)

fun ItemModel(stack: ItemStack, context: ItemDisplayContext): Model {
    val model = Minecraft.getInstance().itemRenderer.getModel(stack, Minecraft.getInstance().level, null, 42)
    val key = ItemCacheKey(model, context)
    return FLYWHEEL_ABUSE.get(Unit).getOrPut(key) { bufferItemModel(stack, context) }
}

private fun bufferItemModel(stack: ItemStack, context: ItemDisplayContext): Model {
    val itemRenderer = Minecraft.getInstance().itemRenderer
    val objects = THREAD_LOCAL.get()
    objects.stack = stack
    objects.begin()

    itemRenderer.renderStatic(
        stack, context, 0,
        OverlayTexture.NO_OVERLAY,
        objects.poseStack, objects.builderSource,
        Minecraft.getInstance().level,
        42
    )

    return objects.end()
}

private class ThreadLocalObjects {
    val poseStack: PoseStack = PoseStack()
    val modelBuilder: MeshVertexConsumer = MeshVertexConsumer(::FullVertexView)
    val glintBuilder: MeshVertexConsumer = MeshVertexConsumer(::FullVertexView)
    val builderSource = ItemBuilderBufferSource(modelBuilder, glintBuilder)
    lateinit var stack: ItemStack

    fun begin() {
        poseStack.pushPose()
        modelBuilder.begin(64)
        glintBuilder.begin(if (stack.hasFoil()) 64 else 1)
    }

    fun end(): Model {
        poseStack.popPose()
        val modelMesh = modelBuilder.build()
        val glintMesh = glintBuilder.build()
        val itemRenderType = ItemBlockRenderTypes.getRenderType(stack, false)
        val itemMaterial = if (itemRenderType == RenderType.translucent()) Materials.TRANSLUCENT_BLOCK else Materials.CUTOUT_BLOCK

        return SimpleModel(listOf(Model.ConfiguredMesh(itemMaterial, modelMesh), Model.ConfiguredMesh(ITEM_GLINT, glintMesh)))
    }

}

private class ItemBuilderBufferSource(
    private val modelBuilder: MeshVertexConsumer,
    private val glintBuilder: MeshVertexConsumer
) : MultiBufferSource {

    override fun getBuffer(p0: RenderType): VertexConsumer {
        return if (GLINT_TYPES.contains(p0)) glintBuilder else modelBuilder
    }
}