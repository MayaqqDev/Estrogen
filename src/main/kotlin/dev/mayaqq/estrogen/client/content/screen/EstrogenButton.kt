package dev.mayaqq.estrogen.client.content.screen

import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.text.CommonText
import dev.mayaqq.cynosure.utils.colors.Color
import dev.mayaqq.cynosure.utils.colors.Red
import dev.mayaqq.cynosure.utils.colors.White
import dev.mayaqq.cynosure.utils.colors.Yellow
import dev.mayaqq.estrogen.Estrogen
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.Tooltip
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth

class EstrogenButton(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    val renderer: Renderer,
    onPress: OnPress,
    createNarration: CreateNarration,
    val color: Color
) : Button(x, y, width, height, CommonText.EMPTY, onPress, createNarration) {

    override fun renderWidget(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        graphics.fill(x, y, x + width, y + height, -0x2FEFEFF0)
        graphics.renderOutline(x + 1, y + 1, width - 2, height - 2,  if (isHoveredOrFocused) Yellow.toInt() else color.toInt())

        with(renderer) {
            this@EstrogenButton.renderComponents(graphics, mouseX, mouseY, partialTick)
        }
    }

    class Builder(private val renderer: Renderer, private val onPress: OnPress) {
        private var tooltip: Tooltip? = null
        private var x = 0
        private var y = 0
        private var width = 150
        private var height = 20
        private var createNarration: CreateNarration
        private var color: Color = Red

        init {
            this.createNarration = DEFAULT_NARRATION
        }

        fun pos(x: Int, y: Int): Builder {
            this.x = x
            this.y = y
            return this
        }

        fun width(width: Int): Builder {
            this.width = width
            return this
        }

        fun size(width: Int, height: Int): Builder {
            this.width = width
            this.height = height
            return this
        }

        fun bounds(x: Int, y: Int, width: Int, height: Int): Builder {
            return this.pos(x, y).size(width, height)
        }

        fun tooltip(tooltip: Tooltip?): Builder {
            this.tooltip = tooltip
            return this
        }

        fun createNarration(createNarration: CreateNarration): Builder {
            this.createNarration = createNarration
            return this
        }

        fun color(color: Color): Builder {
            this.color = color
            return this
        }

        fun build(): EstrogenButton {
            return EstrogenButton(
                this.x,
                this.y,
                this.width,
                this.height,
                this.renderer,
                this.onPress,
                this.createNarration,
                this.color
            ).apply { this@apply.tooltip = this@Builder.tooltip }
        }
    }

    interface Renderer {
        fun EstrogenButton.renderComponents(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float)
    }

    class TextRenderer(val text: Component) : Renderer {
        override fun EstrogenButton.renderComponents(
            graphics: GuiGraphics,
            mouseX: Int,
            mouseY: Int,
            partialTick: Float
        ) {
            if (this.message == CommonText.EMPTY) this.message = text
            val activeColor = if (this.active) 16777215 else 10526880
            this.renderString(graphics, McClient.font, activeColor or (Mth.ceil(this.alpha * 255.0f) shl 24))
        }
    }

    class IconRenderer(val icon: ResourceLocation) : Renderer {
        override fun EstrogenButton.renderComponents(
            graphics: GuiGraphics,
            mouseX: Int,
            mouseY: Int,
            partialTick: Float
        ) {
            graphics.blit(icon, this.x + 6, this.y + 6, 0F, 0F, 8, 8, 8, 8)
        }
    }
}