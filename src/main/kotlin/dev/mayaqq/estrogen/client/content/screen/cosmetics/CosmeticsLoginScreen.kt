package dev.mayaqq.estrogen.client.content.screen.cosmetics

import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.helpers.openUri
import dev.mayaqq.cynosure.text.unaryMinus
import dev.mayaqq.cynosure.text.unaryPlus
import dev.mayaqq.estrogen.client.content.screen.EstrogenButton
import dev.mayaqq.estrogen.client.content.screen.EstrogenMenuScreen
import net.minecraft.client.gui.screens.Screen

class CosmeticsLoginScreen(previous: Screen?) : CosmeticsBaseScreen(previous, -"gui.estrogen.cosmetics.title", /*TODO: this to false*/true) {
    val bLogin = EstrogenButton.Builder(EstrogenButton.TextRenderer(-"gui.estrogen.cosmetics.login")) {
        //TODO: Login here
    }.color(EstrogenMenuScreen.transWhite)
    override fun baseInit() {
        super.baseInit()
        val fakeThirdWidth = (width - 30) / 3
        bLogin.bounds(20 + fakeThirdWidth + 80, 45 + ((height - 90) / 2), fakeThirdWidth * 2 - 160, 25).buildAndAdd()
    }
}