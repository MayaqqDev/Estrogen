package dev.mayaqq.estrogen.utils.render

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics

//TODO: Cynosure


fun PoseStack.translate(x: Number, y: Number, z: Number) {
    this.translate(x.toFloat(), y.toFloat(), z.toFloat())
}

inline fun GuiGraphics.pushPop(action: PoseStack.() -> Unit) {
    this.pose().pushPop(action)
}

inline fun PoseStack.pushPop(action: PoseStack.() -> Unit) {
    this.pushPose()
    this.action()
    this.popPose()
}

inline fun GuiGraphics.scissor(x: Int, y: Int, width: Int, height: Int, action: () -> Unit) {
    this.enableScissor(x, y, x + width, y + height)
    action()
    this.disableScissor()
}

inline fun GuiGraphics.scissorRange(x: IntRange, y: IntRange, action: () -> Unit) {
    this.enableScissor(x.start, y.start, x.endInclusive, y.endInclusive)
    action()
    this.disableScissor()
}

inline fun GuiGraphics.translated(x: Number = 0, y: Number = 0, z: Number = 0, action: PoseStack.() -> Unit) {
    this.pose().translated(x, y, z, action)
}

inline fun PoseStack.translated(x: Number = 0, y: Number = 0, z: Number = 0, action: PoseStack.() -> Unit) {
    this.pushPop {
        this.translate(x.toFloat(), y.toFloat(), z.toFloat())
        this.action()
    }
}

inline fun GuiGraphics.scaled(x: Number = 1, y: Number = 1, z: Number = 1, action: PoseStack.() -> Unit) {
    this.pose().scaled(x, y, z, action)
}

inline fun PoseStack.scaled(x: Number = 1, y: Number = 1, z: Number = 1, action: PoseStack.() -> Unit) {
    this.pushPop {
        this.scale(x.toFloat(), y.toFloat(), z.toFloat())
        this.action()
    }
}