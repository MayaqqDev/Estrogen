package dev.mayaqq.estrogen.client.content.screen.cosmetics

import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.helpers.McFont
import dev.mayaqq.cynosure.helpers.setScreenAsync
import dev.mayaqq.cynosure.text.TextUtils.splitLines
import dev.mayaqq.cynosure.text.unaryMinus
import dev.mayaqq.estrogen.client.content.screen.EstrogenButton
import dev.mayaqq.estrogen.client.content.screen.EstrogenMenuScreen
import dev.mayaqq.estrogen.client.cosmetics.CosmeticAPI
import dev.mayaqq.estrogen.client.cosmetics.StatusCode
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component


class CosmeticsLoginScreen(previous: Screen?) : CosmeticsBaseScreen(previous, -"gui.estrogen.cosmetics.title", false) {

    var info: Component? = null

    val bLogin = EstrogenButton.Builder(EstrogenButton.TextRenderer(-"gui.estrogen.cosmetics.login")) {
        this.info = getLoginMessage(null)
        CosmeticAPI.login().thenAcceptAsync { status ->
            if (status === StatusCode.OK) {
                this.info = getCosmeticsMessage(null)
                CosmeticAPI.getCosmetics().thenAcceptAsync { cosmeticsStatus ->
                    if (cosmeticsStatus === StatusCode.OK) {
                        McClient.setScreenAsync{ CosmeticsSelectionScreen(previous) }
                    } else {
                        this.info = getCosmeticsMessage(cosmeticsStatus)
                    }
                }
            } else {
                this.info = getLoginMessage(status)
            }
        }
    }.color(EstrogenMenuScreen.transWhite)

    override fun baseInit() {
        super.baseInit()
        val fakeThirdWidth = (width - 30) / 3
        45 + ((height - 90) / 2)
        bLogin.bounds(20 + fakeThirdWidth + 80, height - 35, fakeThirdWidth * 2 - 160, 25).buildAndAdd()
    }

    override fun baseRender(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        val fakeThirdWidth = (width - 30) / 3
        val x = (fakeThirdWidth * 2) + 20
        var y = ((height - 90) / 2) - (McFont.lineHeight * 3)
        (-"gui.estrogen.cosmetics.login.description").splitLines().forEach { line ->
            graphics.drawCenteredString(McFont, line, x, y, 0xFFFFFF)
            y += McFont.lineHeight
        }
        info?.let {
            y += McFont.lineHeight
            graphics.drawCenteredString(font, it, x, y, 0xFFFFFF)
        }
    }
}