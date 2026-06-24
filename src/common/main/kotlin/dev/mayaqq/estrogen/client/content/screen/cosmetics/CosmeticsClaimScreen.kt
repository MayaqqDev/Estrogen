package dev.mayaqq.estrogen.client.content.screen.cosmetics

import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.helpers.McFont
import dev.mayaqq.cynosure.text.CommonText
import dev.mayaqq.cynosure.text.TextUtils.splitLines
import dev.mayaqq.cynosure.text.unaryMinus
import dev.mayaqq.estrogen.client.content.screen.EstrogenButton
import dev.mayaqq.estrogen.client.content.screen.EstrogenMenuScreen
import dev.mayaqq.estrogen.client.cosmetics.CosmeticAPI
import dev.mayaqq.estrogen.client.cosmetics.StatusCode
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component


class CosmeticsClaimScreen(previous: Screen?) : CosmeticsBaseScreen(previous, -"gui.estrogen.cosmetics.claim") {

    var info: Component? = null

    val bClaim = EstrogenButton.Builder(EstrogenButton.TextRenderer(-"gui.estrogen.cosmetics.claim.button")) {
        CosmeticAPI.claimReward(codeBox.value).thenAcceptAsync { status ->
                if (status === StatusCode.OK) {
                    Minecraft.getInstance().tell {
                        McClient.screen?.onClose()
                    }
                } else {
                    this.info = getClaimMessage(status)
                }
            }
    }.color(EstrogenMenuScreen.transWhite)

    val codeBox = EditBox(McFont, 0, 0, 0, 21, CommonText.EMPTY)

    override fun baseInit() {
        super.baseInit()
        val fakeThirdWidth = (width - 30) / 3
        val claimButton = bClaim.bounds(
            20 + fakeThirdWidth + 80,
            height - 35,
            fakeThirdWidth * 2 - 160,
            25
        ).disabled(codeBox.value.isEmpty()).buildAndAdd()

        codeBox.x = (20 + fakeThirdWidth + 80) + 2
        codeBox.y = height - 70 + 2
        codeBox.width = (fakeThirdWidth * 2 - 160) - 4
        codeBox.setResponder {
            claimButton.disabled = it.isEmpty()
        }
        codeBox.add()
    }

    override fun beforeRender(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        val fakeThirdWidth = (width - 30) / 3
        val x = (fakeThirdWidth * 2) + 20
        var y = ((height - 90) / 2) - (McFont.lineHeight * 3)

        (-"gui.estrogen.cosmetics.claim.description").splitLines().forEach { line ->
            graphics.drawCenteredString(McFont, line, x, y, 0xFFFFFF)
            y += McFont.lineHeight
        }

        info?.let {
            y += McFont.lineHeight
            graphics.drawCenteredString(font, it, x, y, 0xFFFFFF)
        }
    }
}