package dev.mayaqq.estrogen.client.content.screen.config

import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.cynosure.client.utils.translate
import dev.mayaqq.cynosure.helpers.McFont
import dev.mayaqq.estrogen.client.extensions.posX
import dev.mayaqq.estrogen.client.extensions.posY
import dev.mayaqq.estrogen.client.extensions.widgetHeight
import dev.mayaqq.estrogen.client.extensions.widgetWidth
import dev.mayaqq.estrogen.id
import invoke.kitty.kritter.utils.color.Red
import invoke.kitty.kritter.utils.color.Teal
import invoke.kitty.kritter.utils.color.Yellow
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.sounds.SoundManager
import net.minecraft.network.chat.Component

class ConfigSelectionButton(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    id: String,
    onPress: OnPress
) : Button(
    x,
    y,
    width,
    height,
    Component.literal(id),
    onPress,
    DEFAULT_NARRATION
) {

    var disabled = false
    var enlarged: Boolean? = null

    override fun renderWidget(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        if (!disabled) {
            if (isHoveredOrFocused && (enlarged == null || !enlarged!!)) {
                this.posX -= 5
                this.posY -= 5
                this.widgetWidth += 10
                this.widgetHeight += 10
                enlarged = true
            } else if (!isHoveredOrFocused && enlarged == true) {
                this.posX += 5
                this.posY += 5
                this.widgetWidth -= 10
                this.widgetHeight -= 10
                enlarged = false
            }
        }
        val color = if (disabled) {
            Red.toInt()
        } else if (isHoveredOrFocused) {
            Yellow.toInt()
        } else {
            Teal.toInt()
        }
        graphics.fill(posX, posY, posX + widgetWidth, posY + widgetHeight, -0x2FEFEFF0)
        graphics.renderOutline(posX + 1, posY + 1, widgetWidth - 2, widgetHeight - 2, color)
        graphics.drawCenteredString(McFont, message, posX + (widgetWidth / 2), posY + 100 + 10 + if (enlarged == true) 5 else 0, 0xFFFFFFFFu.toInt())
        graphics.pushPop {
            val scale = 4F + if (enlarged == true) 0.4F else 0.0F
            translate(posX + (widgetWidth / 2 - (16 * scale / 2)), posY + (widgetHeight / 2 - (16 * scale / 2)), 0)
            scale(scale, scale, 0F)
            graphics.blit(id("textures/gui/icons/cog.png"),
                0,
                0,
                0F,
                0F,
                16,
                16,
                16,
                16
            )
        }
    }

    override fun onClick(p0: Double, p1: Double) {
        if (!disabled) super.onClick(p0, p1)
    }

    override fun playDownSound(p0: SoundManager) {
        if (!disabled) super.playDownSound(p0)
    }
}