package dev.mayaqq.estrogen.client.content.blockRenderers.memorial

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.cynosure.client.utils.translate
import dev.mayaqq.cynosure.helpers.McFont
import dev.mayaqq.cynosure.text.Text
import dev.mayaqq.cynosure.text.TextStyle.color
import dev.mayaqq.cynosure.utils.colors.Color
import dev.mayaqq.cynosure.utils.colors.darker
import dev.mayaqq.estrogen.content.blockEntities.MemorialBlockEntity
import dev.mayaqq.estrogen.content.blocks.MemorialBlock
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class MemorialRenderer(val ctx: BlockEntityRendererProvider.Context) : BlockEntityRenderer<MemorialBlockEntity> {
    val textColor = Color(0xFF5e5e5eu).darker()
    val textOutlineColor = Color(0xFF686868u).darker()

    override fun render(
        be: MemorialBlockEntity,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        light: Int,
        overlay: Int
    ) {
        if (be.blockState.getValue(MemorialBlock.PART) == 3) {
            poseStack.pushPop {
                translate(0, 1, 0.1874)
                mulPose(Axis.ZP.rotationDegrees(180F))
                val scale = 0.0098f
                scale(scale, scale, scale)
                val lineHeight = 26F
                for (x in 1..3) {
                    val text = Text.translatable("block.estrogen.memorial.line$x")
                    McFont.drawInBatch8xOutline(
                        text.visualOrderText,
                        -McFont.width(text) / 2F,
                        11 + (lineHeight * (x - 1)),
                        textOutlineColor.toInt(),
                        textColor.toInt(),
                        poseStack.last().pose(),
                        bufferSource,
                        light
                    )
                }

            }
        }
    }
}