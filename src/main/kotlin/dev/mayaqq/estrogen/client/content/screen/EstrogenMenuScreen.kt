package dev.mayaqq.estrogen.client.content.screen

import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.cynosure.client.utils.translate
import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.helpers.openUri
import dev.mayaqq.cynosure.text.Text
import dev.mayaqq.cynosure.text.TextStyle.bold
import dev.mayaqq.cynosure.text.TextStyle.color
import dev.mayaqq.cynosure.utils.colors.Color
import dev.mayaqq.cynosure.utils.colors.LightBlue
import dev.mayaqq.cynosure.utils.colors.White
import dev.mayaqq.estrogen.client.content.screen.modules.ModulesScreen
import dev.mayaqq.estrogen.id
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Tooltip
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.sounds.SoundEvents

class EstrogenMenuScreen(previous: Screen?) : BaseEstrogenScreen(previous, Text.translatable("estrogen.screen.menu.title")) {

    override fun isPauseScreen(): Boolean = true

    val titleText = Text.of("Estrogen") {
        color = LightBlue
        bold = true
    }

    val bConfig = EstrogenButton.Builder(EstrogenButton.TextRenderer(Text.translatable("estrogen.button.config"))) {
        //TODO: open screen
    }
    val bModuleConfigs = EstrogenButton.Builder(EstrogenButton.TextRenderer(Text.translatable("estrogen.button.module_configs"))) {
        McClient.setScreen(ModulesScreen(this))
    }
    val bCosmetics = EstrogenButton.Builder(EstrogenButton.TextRenderer(Text.translatable("estrogen.button.cosmetics"))) {
        //TODO: open cosmetics screen
    }
    val bColonThree = EstrogenButton.Builder(EstrogenButton.TextRenderer(Text.translatable("estrogen.button.colon_three"))) {
        McClient.soundManager.play(SimpleSoundInstance.forUI(SoundEvents.CAT_AMBIENT, 1.0F))
    }
    val bClose = EstrogenButton.Builder(EstrogenButton.TextRenderer(Text.translatable("estrogen.button.close"))) {
        this.onClose()
    }

    val bModrinth = EstrogenButton.Builder(EstrogenButton.IconRenderer(id("textures/gui/icons/modrinth.png"))) {
        McClient.openUri("https://mods.gay/estrogen")
    }
    val bCurseforge = EstrogenButton.Builder(EstrogenButton.IconRenderer(id("textures/gui/icons/curseforge.png"))) {
        McClient.openUri("https://www.curseforge.com/minecraft/mc-mods/estrogen")
    }
    val bGithub = EstrogenButton.Builder(EstrogenButton.IconRenderer(id("textures/gui/icons/github.png"))) {
        McClient.openUri("https://github.com/MayaqqDev/Estrogen")
    }
    val bPatreon = EstrogenButton.Builder(EstrogenButton.IconRenderer(id("textures/gui/icons/patreon.png"))) {
        McClient.openUri("https://www.patreon.com/mayaqq")
    }
    val bDiscord = EstrogenButton.Builder(EstrogenButton.IconRenderer(id("textures/gui/icons/discord.png"))) {
        McClient.openUri("https://discord.gg/hue")
    }

    override fun baseInit() {
        val y = this.height / 4 + 48
        val rowHeight = 24

        bConfig.bounds(this.width / 2 - 100, y, 200, 20).color(transBlue).buildAndAdd()
        bModuleConfigs.bounds(this.width / 2 - 100, y + rowHeight * 1, 200, 20).color(transPink).buildAndAdd()
        bCosmetics.bounds(this.width / 2 - 100, y + rowHeight * 2, 200, 20).color(transWhite).buildAndAdd()
        bColonThree.bounds(this.width / 2 - 100, y + rowHeight * 3, 200, 20).color(transPink).buildAndAdd()
        bClose.bounds(this.width / 2 - 100, y + rowHeight * 4, 200, 20).color(transBlue).buildAndAdd()

        bModrinth.bounds(this.width / 2 - 100 - 24, y, 20, 20).color(transBlue)
            .tooltip(Tooltip.create(Text.of("Modrinth")))
            .buildAndAdd()
        bCurseforge.bounds(this.width / 2 - 100 - 24, y + rowHeight * 1, 20, 20).color(transPink)
            .tooltip(Tooltip.create(Text.of("Curseforge")))
            .buildAndAdd()
        bGithub.bounds(this.width / 2 - 100 - 24, y + rowHeight * 2, 20, 20).color(transWhite)
            .tooltip(Tooltip.create(Text.of("Github")))
            .buildAndAdd()
        bPatreon.bounds(this.width / 2 - 100 - 24, y + rowHeight * 3, 20, 20).color(transPink)
            .tooltip(Tooltip.create(Text.of("Patreon")))
            .buildAndAdd()
        bDiscord.bounds(this.width / 2 - 100 - 24, y + rowHeight * 4, 20, 20).color(transBlue)
            .tooltip(Tooltip.create(Text.of("Discord")))
            .buildAndAdd()
    }

    override fun baseRender(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
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
    }

    companion object {
        val transWhite = Color(0xFFFFFFFFu)
        val transPink = Color(0xFFF5A9B8u)
        val transBlue = Color(0xFF5BCEFAu)
    }
}