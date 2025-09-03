package dev.mayaqq.estrogen.client.content.screen.cosmetics

import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.helpers.McPlayer
import dev.mayaqq.cynosure.text.unaryMinus
import dev.mayaqq.estrogen.client.content.screen.BaseEstrogenScreen
import dev.mayaqq.estrogen.client.content.screen.EstrogenButton
import dev.mayaqq.estrogen.client.content.screen.EstrogenMenuScreen
import dev.mayaqq.estrogen.client.content.screen.cosmetics.widget.CosmeticPreview
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component


open class CosmeticsBaseScreen(previous: Screen?, title: Component, val ableToClaim: Boolean = false) : BaseEstrogenScreen(previous, title) {
    val rTitle = EstrogenButton.Builder(EstrogenButton.TextRenderer(-"gui.estrogen.cosmetics.title")) {}
        .color(EstrogenMenuScreen.transPink).renderOnly(true)
    val bClaimCosmetics = EstrogenButton.Builder(EstrogenButton.TextRenderer(-"gui.estrogen.cosmetics.claim")) {
        McClient.setScreen(CosmeticsClaimScreen(this))
    }.color(EstrogenMenuScreen.transWhite)

    override fun baseInit() {
        val fakeThirdWidth = (width - 30) / 3
        CosmeticPreview(McPlayer, 10, 10, fakeThirdWidth, height - 55).add()
        rTitle.bounds(20 + fakeThirdWidth, 10, fakeThirdWidth * 2, 25).build().addRenderable()
        bClaimCosmetics.bounds(10, height - 35, fakeThirdWidth, 25)
        if (!ableToClaim) bClaimCosmetics.disabled(true)
        bClaimCosmetics.buildAndAdd()
    }
    override fun baseRender(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {}
}