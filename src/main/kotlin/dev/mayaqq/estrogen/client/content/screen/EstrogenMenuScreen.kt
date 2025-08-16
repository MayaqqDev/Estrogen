package dev.mayaqq.estrogen.client.content.screen

import com.mojang.blaze3d.systems.RenderSystem
import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.cynosure.client.utils.translate
import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.text.Text
import dev.mayaqq.cynosure.text.TextStyle.bold
import dev.mayaqq.cynosure.text.TextStyle.color
import dev.mayaqq.cynosure.utils.colors.LightBlue
import dev.mayaqq.cynosure.utils.colors.White
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.TitleScreen
import net.minecraft.client.renderer.PanoramaRenderer
import net.minecraft.resources.ResourceLocation

class EstrogenMenuScreen(val previous: Screen?) : Screen(Text.translatable("estrogen.screen.menu.title")) {
    val panoramaRenderer = PanoramaRenderer(TitleScreen.CUBE_MAP)

    override fun isPauseScreen(): Boolean = true
    override fun shouldCloseOnEsc(): Boolean = true

    val titleText = Text.of("Estrogen") {
        color = LightBlue
        bold = true
    }

    val bConfig: Button.Builder = Button.builder(Text.translatable("estrogen.button.config")) {
        //TODO: open screen
    }
    val bModuleConfigs: Button.Builder = Button.builder(Text.translatable("estrogen.button.module_configs")) {
        //TODO: open screen
    }
    val bCosmetics: Button.Builder = Button.builder(Text.translatable("estrogen.button.cosmetics")) {
        //TODO: open cosmetics screen
    }
    val bClose: Button.Builder = Button.builder(Text.translatable("estrogen.button.close")) { this.onClose() }

    override fun init() {
        val y = this.height / 4 + 48
        val rowHeight = 24

        bConfig.bounds(this.width / 2 - 100, y, 200, 20).buildAndAdd()
        bModuleConfigs.bounds(this.width / 2 - 100, y + rowHeight * 1, 200, 20).buildAndAdd()
        bCosmetics.bounds(this.width / 2 - 100, y + rowHeight * 2, 200, 20).buildAndAdd()
        bClose.bounds(this.width / 2 - 100, y + rowHeight * 3, 200, 20).buildAndAdd()
    }

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        panoramaRenderer.render(partialTick, 1.0F)
        graphics.pushPop {
            RenderSystem.enableBlend()
            graphics.setColor(
                1.0f,
                1.0f,
                1.0f,
                1.0f
            )
            graphics.blit(PANORAMA_OVERLAY, 0, 0, width, height, 0.0f, 0.0f, 16, 128, 16, 128)
            graphics.setColor(1.0f, 1.0f, 1.0f, 1.0f)
        }
        graphics.pushPop {
            translate(width / 2 - ((font.width(titleText) / 2) * 3), height / 4 + 48 - 48, 0F)
            scale(3F, 3F, 0F)
            graphics.drawString(
                font,
                titleText,
                0,
                0,
                White.toInt(),
                false
            )
        }

        super.render(graphics, mouseX, mouseY, partialTick)
    }

    override fun renderBackground(gui: GuiGraphics) {}

    override fun onClose() {
        previous?.let { McClient.setScreen(it) }?: super.onClose()
    }

    fun Button.Builder.buildAndAdd() {
        addRenderableWidget(this.build())
    }

    companion object {
        private val PANORAMA_OVERLAY = ResourceLocation("textures/gui/title/background/panorama_overlay.png")
    }
}