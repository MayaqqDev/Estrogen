package dev.mayaqq.estrogen.client.content.screen.cosmetics

import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.helpers.McFont
import dev.mayaqq.cynosure.helpers.McLevel
import dev.mayaqq.cynosure.helpers.McPlayer
import dev.mayaqq.cynosure.text.CommonText
import dev.mayaqq.cynosure.text.unaryMinus
import dev.mayaqq.cynosure.text.unaryPlus
import dev.mayaqq.estrogen.client.content.screen.BaseEstrogenScreen
import dev.mayaqq.estrogen.client.content.screen.EstrogenButton
import dev.mayaqq.estrogen.client.content.screen.EstrogenMenuScreen
import dev.mayaqq.estrogen.id
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.inventory.InventoryScreen
import net.minecraft.world.entity.player.Inventory
import org.joml.Quaternionf

class CosmeticsBaseScreen(previous: Screen?) : BaseEstrogenScreen(previous, -"gui.estrogen.cosmetics.title") {

    val rCosmeticsPreview = EstrogenButton.Builder(PreviewRenderer()) {}
        .color(EstrogenMenuScreen.transBlue).renderOnly(true)
    val rTitle = EstrogenButton.Builder(EstrogenButton.TextRenderer(-"gui.estrogen.cosmetics.title")) {}
        .color(EstrogenMenuScreen.transPink).renderOnly(true)
    val bClaimCosmetics = EstrogenButton.Builder(EstrogenButton.TextRenderer(-"gui.estrogen.cosmetics.claim")) {
        // Claim Cosmetics
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
        rCosmeticsPreview.bounds(10, 10, fakeThirdWidth, height - 55).build().addRenderable()
        rTitle.bounds(20 + fakeThirdWidth, 10, fakeThirdWidth * 2, 25).build().addRenderable()
        bClaimCosmetics.bounds(10, height - 35, fakeThirdWidth, 25).buildAndAdd()

        val cosmeticHeight = 45
        bRefresh.bounds(20 + fakeThirdWidth, 45, cosmeticHeight, cosmeticHeight).buildAndAdd()
        val perLine = (fakeThirdWidth * 2) / (cosmeticHeight + 10)
        val cosmetics = /*TODO: get cosmetics here */ arrayOf("one", "two", "three", "four", "five", "six", "seven")
        cosmetics.forEachIndexed { index, cosmetic ->
            val line = index / perLine
            val currentIndex = index + if (line == 0) 1 else 0
            EstrogenButton.Builder(EstrogenButton.TextRenderer(+cosmetic)) {

            }.bounds(
                (currentIndex - (line * perLine)) * (10 + cosmeticHeight) + 20 + fakeThirdWidth,
                line * (10 + cosmeticHeight) + 45,
                cosmeticHeight,
                cosmeticHeight
            ).color(EstrogenMenuScreen.transBlue).buildAndAdd()
        }

    }

    override fun baseRender(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {}
}

class PreviewRenderer() : EstrogenButton.Renderer {
    override fun EstrogenButton.renderComponents(
        graphics: GuiGraphics,
        mouseX: Int,
        mouseY: Int,
        partialTick: Float
    ) {
        if (this.message == CommonText.EMPTY) this.message = -"gui.estrogen.cosmetics.no_preview"
        McPlayer?.let { player ->
            graphics.pushPop {
                InventoryScreen.renderEntityInInventory(
                    graphics,
                    width / 2,
                    height / 2,
                    10,
                    Quaternionf(),
                    Quaternionf(),
                    player
                )
            }
        }?: {
            this@renderComponents.renderString(graphics, McFont, 0xFFFFFF)
        }
    }
}