package dev.mayaqq.estrogen.client.content.screen.cosmetics

import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.helpers.openUri
import dev.mayaqq.cynosure.text.unaryMinus
import dev.mayaqq.cynosure.text.unaryPlus
import dev.mayaqq.estrogen.client.content.EstrogenRenderer
import dev.mayaqq.estrogen.client.content.screen.EstrogenButton
import dev.mayaqq.estrogen.client.content.screen.EstrogenMenuScreen
import dev.mayaqq.estrogen.client.cosmetics.CosmeticAPI
import dev.mayaqq.estrogen.id
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Tooltip
import net.minecraft.client.gui.screens.Screen

class CosmeticsSelectionScreen(previous: Screen?) : CosmeticsBaseScreen(previous, -"gui.estrogen.cosmetics.title", true) {

    val bPatreonAd = EstrogenButton.Builder(EstrogenButton.TextRenderer(-"gui.estrogen.cosmetics.patreon_ad")) {
        McClient.openUri("https://patreon.com/mayaqq")
    }.color(EstrogenMenuScreen.transWhite)
    val bRefresh = EstrogenButton.Builder(object : EstrogenButton.Renderer {
        override fun EstrogenButton.renderComponents(
            graphics: GuiGraphics,
            mouseX: Int,
            mouseY: Int,
            partialTick: Float
        ) {
            graphics.blit(id("textures/gui/icons/refresh.png"),
                this.x + 14,
                this.y + 15,
                0F,
                0F,
                16,
                16,
                16,
                16
            )
        }
    }) {
        // Refresh
    }.color(EstrogenMenuScreen.transWhite)

    override fun baseInit() {
        val fakeThirdWidth = (width - 30) / 3
        super.baseInit()
        val cosmeticHeight = 45
        bRefresh.bounds(20 + fakeThirdWidth, 45, cosmeticHeight, cosmeticHeight).buildAndAdd()
        val perLine = (fakeThirdWidth * 2) / (cosmeticHeight + 10)
        val cosmetics = CosmeticAPI.getAvailableCosmetics()
        cosmetics.forEachIndexed { index, cosmetic ->
            val cosmetic = CosmeticAPI.getCosmetic(cosmetic)?: return@forEachIndexed
            val line = index / perLine
            val currentIndex = index + if (line == 0) 1 else 0
            EstrogenButton.Builder(EstrogenButton.CosmeticRenderer(cosmetic)) {
                CosmeticAPI.setCosmetic(cosmetic)
            }.bounds(
                (currentIndex - (line * perLine)) * (10 + cosmeticHeight) + 20 + fakeThirdWidth,
                line * (10 + cosmeticHeight) + 45,
                cosmeticHeight,
                cosmeticHeight
            ).color(EstrogenMenuScreen.transBlue).tooltip(Tooltip.create(+cosmetic.name)).buildAndAdd()
        }
        if (cosmetics.isEmpty()) {
            bPatreonAd.bounds(80 + fakeThirdWidth, 45 + ((height - 90) / 2), fakeThirdWidth * 2 - 140, 25).buildAndAdd()
        }
    }

    override fun afterRender(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        EstrogenRenderer.drawCelOutlineOutsideLevelRender() // Do this here
    }
}