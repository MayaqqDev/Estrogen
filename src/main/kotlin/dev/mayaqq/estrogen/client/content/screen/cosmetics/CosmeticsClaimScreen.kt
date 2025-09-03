package dev.mayaqq.estrogen.client.content.screen.cosmetics

import dev.mayaqq.cynosure.helpers.McFont
import dev.mayaqq.cynosure.text.unaryMinus
import dev.mayaqq.estrogen.client.content.screen.EstrogenButton
import dev.mayaqq.estrogen.client.content.screen.EstrogenMenuScreen
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen

class CosmeticsClaimScreen(previous: Screen?) : CosmeticsBaseScreen(previous, -"gui.estrogen.cosmetics.claim") {
    val rClaimWidget = EstrogenButton.Builder(object : EstrogenButton.Renderer {
        override fun EstrogenButton.renderComponents(
            graphics: GuiGraphics,
            mouseX: Int,
            mouseY: Int,
            partialTick: Float
        ) {
        }
    }) {}.color(EstrogenMenuScreen.transWhite).renderOnly(true)
    override fun baseInit() {
        super.baseInit()
        val fakeThirdWidth = (width - 30) / 3
        val widgetHeight = ((height - 90) / 3) * 2
        rClaimWidget.bounds(20 + fakeThirdWidth + 40, 45 + (((height - 90) / 2) - (widgetHeight / 2)), fakeThirdWidth * 2 - 80, widgetHeight).build().addRenderable()
    }
}