package dev.mayaqq.estrogen.client.content.screen

import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.cynosure.client.utils.translate
import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.text.CommonText
import dev.mayaqq.cynosure.utils.colors.*
import dev.mayaqq.estrogen.client.content.EstrogenRenderTypes
import dev.mayaqq.estrogen.client.content.EstrogenRenderer
import dev.mayaqq.estrogen.client.cosmetics.Cosmetic
import dev.mayaqq.estrogen.client.extensions.widgetHeight
import dev.mayaqq.estrogen.client.extensions.widgetWidth
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.Tooltip
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.client.sounds.SoundManager
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import org.joml.Quaternionf
import kotlin.math.min

open class EstrogenButton(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    val renderers: Array<Renderer>,
    onPress: OnPress,
    createNarration: CreateNarration,
    val color: Color,
    var disabled: Boolean,
    val renderOnly: Boolean
) : Button(x, y, width, height, CommonText.EMPTY, onPress, createNarration) {

    override fun renderWidget(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        graphics.fill(this.x, this.y, x + width, y + height, -0x2FEFEFF0)
        if (disabled) {
            graphics.renderOutline(x + 1, y + 1, width - 2, height - 2,  color.darker().toInt())
        } else {
            graphics.renderOutline(x + 1, y + 1, width - 2, height - 2,  if (isHoveredOrFocused && !renderOnly) Yellow.toInt() else color.toInt())
        }

        renderers.forEach {
            with(it) {
                this@EstrogenButton.renderComponents(graphics, mouseX, mouseY, partialTick)
            }
        }
    }

    override fun onClick(mouseX: Double, mouseY: Double) {
        if (!disabled && !renderOnly) super.onClick(mouseX, mouseY)
    }

    override fun playDownSound(soundManager: SoundManager) {
        if (!disabled && !renderOnly) super.playDownSound(soundManager)
    }

    class Builder(vararg renderers: Renderer, private val onPress: OnPress) {

        private var renderers = arrayOf(*renderers)

        private var tooltip: Tooltip? = null
        private var x = 0
        private var y = 0
        private var width = 150
        private var height = 20
        private var createNarration: CreateNarration
        private var color: Color = Red
        private var disabled: Boolean = false
        private var renderOnly: Boolean = false

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

        fun disabled(disabled: Boolean): Builder {
            this.disabled = disabled
            return this
        }

        fun renderOnly(renderOnly: Boolean): Builder {
            this.renderOnly = renderOnly
            return this
        }

        fun build(): EstrogenButton {
            return EstrogenButton(
                this.x,
                this.y,
                this.width,
                this.height,
                this.renderers,
                this.onPress,
                this.createNarration,
                this.color,
                this.disabled,
                this.renderOnly,
            ).apply { this@apply.tooltip = this@Builder.tooltip }
        }
    }

    interface Renderer {
        fun EstrogenButton.renderComponents(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float)
    }

    open class TextRenderer(val text: Component) : Renderer {
        override fun EstrogenButton.renderComponents(
            graphics: GuiGraphics,
            mouseX: Int,
            mouseY: Int,
            partialTick: Float
        ) {
            if (this.message == CommonText.EMPTY) this.message = text
            val textColor = if (this.disabled) McGray.toInt() else 0xFFFFFF
            this.renderString(graphics, McClient.font, textColor)
        }
    }

    open class IconRenderer(val icon: ResourceLocation) : Renderer {
        override fun EstrogenButton.renderComponents(
            graphics: GuiGraphics,
            mouseX: Int,
            mouseY: Int,
            partialTick: Float
        ) {
            graphics.blit(icon, this.x + 6, this.y + 6, 0F, 0F, 8, 8, 8, 8)
        }
    }

    open class CosmeticRenderer(val cosmetic: Cosmetic) : Renderer {
        override fun EstrogenButton.renderComponents(
            graphics: GuiGraphics,
            mouseX: Int,
            mouseY: Int,
            partialTick: Float
        ) {
            val x = this.x + this.widgetWidth / 2f
            val y = this.y + this.widgetHeight / 2f
            val scale = min(this.widgetWidth, this.widgetHeight) / 32f

            val yRot = Mth.wrapDegrees(System.currentTimeMillis().toDouble() / 25.0).toFloat()
            val rotation = Quaternionf().rotateZYX(Mth.PI, yRot * Mth.DEG_TO_RAD, 6 * Mth.DEG_TO_RAD)

            graphics.pushPop {
                translate(x, y, 1000)
                scale(16f * scale, 16f * scale, 16f * scale)
                translate(-0.5f, -0.5f, 0f)
                rotateAround(rotation, 0.5f, 0.5f, 0.5f)

                cosmetic.render(
                    EstrogenRenderer.getCelShaded(graphics.bufferSource()),
                    EstrogenRenderTypes::entityCutoutNoDiffuse,
                    graphics.pose(),
                    White,
                    LightTexture.FULL_BRIGHT,
                    OverlayTexture.NO_OVERLAY
                )
            }
        }

    }
}