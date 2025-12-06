package dev.mayaqq.estrogen.client.content.screen.cosmetics

import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.helpers.McPlayer
import dev.mayaqq.cynosure.text.unaryMinus
import dev.mayaqq.estrogen.client.content.screen.BaseEstrogenScreen
import dev.mayaqq.estrogen.client.content.screen.EstrogenButton
import dev.mayaqq.estrogen.client.content.screen.EstrogenMenuScreen
import dev.mayaqq.estrogen.client.content.screen.cosmetics.widget.CosmeticPreview
import dev.mayaqq.estrogen.client.cosmetics.CosmeticAPI
import dev.mayaqq.estrogen.client.cosmetics.StatusCode
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent


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
    override fun isPauseScreen(): Boolean = false

    companion object {
        val LOGIN_DESCRIPTION = -"gui.estrogen.cosmetics.login.description"
        val LOGIN_BUTTON = -"gui.estrogen.cosmetics.login.button"
        val LOGIN_INIT = -"gui.estrogen.cosmetics.login.init"
        val LOGIN_UNAUTHORIZED = -"gui.estrogen.cosmetics.login.unauthorized"
        val LOGIN_SERVER_ERROR = -"gui.estrogen.cosmetics.login.server_error"
        val LOGIN_FAILED = -"gui.estrogen.cosmetics.login.failed"

        val COSMETICS_INIT = -"gui.estrogen.cosmetics.init"
        val COSMETICS_UNAUTHORIZED = -"gui.estrogen.cosmetics.unauthorized"
        val COSMETICS_SERVER_ERROR = -"gui.estrogen.cosmetics.server_error"
        val COSMETICS_FAILED = -"gui.estrogen.cosmetics.failed"

        val CLAIM_DESCRIPTION = -"gui.estrogen.cosmetics.claim.description"
        val CLAIM_BUTTON = -"gui.estrogen.cosmetics.claim.button"
        val CLAIM_INIT = -"gui.estrogen.cosmetics.claim.init"
        val CLAIM_FORBIDDEN = -"gui.estrogen.cosmetics.claim.forbidden"
        val CLAIM_NOT_FOUND = -"gui.estrogen.cosmetics.claim.not_found"
        val CLAIM_FAILED = -"gui.estrogen.cosmetics.claim.failed"

        fun getLoginMessage(code: StatusCode?): Component = when (code) {
            null -> LOGIN_INIT
            StatusCode.UNAUTHORIZED -> LOGIN_UNAUTHORIZED
            StatusCode.INTERNAL_SERVER_ERROR -> LOGIN_SERVER_ERROR
            StatusCode.UNKNOWN_ERROR -> LOGIN_FAILED
            else -> Component.literal("Status: $code")
        }

        fun getCosmeticsMessage(code: StatusCode?): Component = when (code) {
            null -> COSMETICS_INIT
            StatusCode.UNAUTHORIZED -> COSMETICS_UNAUTHORIZED
            StatusCode.INTERNAL_SERVER_ERROR -> COSMETICS_SERVER_ERROR
            StatusCode.UNKNOWN_ERROR -> COSMETICS_FAILED
            else -> Component.literal("Status: $code")
        }

        fun getClaimMessage(code: StatusCode?): Component = when (code) {
            null -> CLAIM_INIT
            StatusCode.FORBIDDEN -> CLAIM_FORBIDDEN
            StatusCode.NOT_FOUND -> CLAIM_NOT_FOUND
            StatusCode.UNKNOWN_ERROR -> CLAIM_FAILED
            else -> Component.literal("Status: $code")
        }

        fun open(previous: Screen?) {
            McClient.setScreen(CosmeticsLoginScreen(previous))
        }
    }
}