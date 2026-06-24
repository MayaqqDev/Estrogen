package dev.mayaqq.estrogen.client.content.screen.config

import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.cynosure.client.utils.translate
import dev.mayaqq.cynosure.helpers.McFont
import dev.mayaqq.cynosure.utils.colors.Teal
import dev.mayaqq.cynosure.utils.colors.Yellow
import dev.mayaqq.estrogen.client.extensions.posX
import dev.mayaqq.estrogen.client.extensions.posY
import dev.mayaqq.estrogen.client.extensions.widgetHeight
import dev.mayaqq.estrogen.client.extensions.widgetWidth
import dev.mayaqq.estrogen.id
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
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
    var enlarged: Boolean? = null

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
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
        super.render(graphics, mouseX, mouseY, delta)
    }

    override fun renderWidget(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        graphics.fill(x, y, x + width, y + height, -0x2FEFEFF0)
        graphics.renderOutline(x + 1, y + 1, width - 2, height - 2,  if (isHoveredOrFocused) Yellow.toInt() else Teal.toInt())
        graphics.drawCenteredString(McFont, message, x + (width / 2), y + 100 + 10 + if (enlarged == true) 5 else 0, 0xFFFFFFFFu.toInt())
        graphics.pushPop {
            val scale = 4F + if (enlarged == true) 0.4F else 0.0F
            translate(x + (width / 2 - (16 * scale / 2)), y + (height / 2 - (16 * scale / 2)), 0)
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
}