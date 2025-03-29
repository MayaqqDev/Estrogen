package dev.mayaqq.estrogen.client.content.blockRenderers.cookieJar

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.content.blockEntities.CookieJarBlockEntity
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.world.item.ItemDisplayContext

class CookieJarRenderer(val ctx: BlockEntityRendererProvider.Context) : BlockEntityRenderer<CookieJarBlockEntity> {

    override fun render(
        be: CookieJarBlockEntity,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        light: Int,
        overlay: Int
    ) {
        // IDEA is wrong here, block entity level can be null
        if (be.level == null) return

        val itemRenderer = Minecraft.getInstance().itemRenderer

        poseStack.pushPose()
        poseStack.mulPose(Axis.XN.rotationDegrees(90f))
        poseStack.translate(0.5, -0.625, 0.032)

        Estrogen.info(be.count.toString())
        for (jarItem in be.items) {
            if (jarItem.isEmpty) {
                continue
            }
            poseStack.translate(-0.025, -0.025, 0.032)
            itemRenderer.renderStatic(
                jarItem, ItemDisplayContext.GROUND,
                light, overlay, poseStack, bufferSource,
                be.level, 0
            )
            poseStack.translate(0.05, 0.05, 0.032)
            itemRenderer.renderStatic(
                jarItem, ItemDisplayContext.GROUND,
                light, overlay, poseStack, bufferSource,
                be.level, 0
            )
            poseStack.translate(-0.025, -0.025, 0.0)
        }

        poseStack.popPose()
    }
}