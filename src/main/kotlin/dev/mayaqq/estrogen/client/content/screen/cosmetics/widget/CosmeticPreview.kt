package dev.mayaqq.estrogen.client.content.screen.cosmetics.widget

import dev.mayaqq.cynosure.helpers.McFont
import dev.mayaqq.cynosure.text.CommonText
import dev.mayaqq.cynosure.text.unaryMinus
import dev.mayaqq.estrogen.client.content.screen.EstrogenButton
import dev.mayaqq.estrogen.client.content.screen.EstrogenMenuScreen
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.InventoryScreen
import net.minecraft.util.Mth
import net.minecraft.world.entity.player.Player
import org.joml.Quaternionf
import org.joml.Vector3f

class CosmeticPreview(val player: Player?, x: Int, y: Int, width: Int, height: Int) : EstrogenButton(
    x, y, width, height,
    arrayOf(),
    OnPress {},
    DEFAULT_NARRATION,
    EstrogenMenuScreen.transBlue,
    false, true
) {
    override fun renderWidget(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.renderWidget(graphics, mouseX, mouseY, partialTick)

        if (this.message == CommonText.EMPTY) this.message = -"gui.estrogen.cosmetics.no_preview"
        if (player != null) {

            val quaternion = Quaternionf().rotateZYX(Mth.PI, yRotation, 0F)
            val yHeadRot: Float = player.yBodyRot
            val yRot: Float = player.yRot
            val xRot: Float = player.xRot
            val yHeadRotO: Float = player.yHeadRotO
            val yBodyRot: Float = player.yHeadRot
            player.yBodyRot = 180.0f
            player.yRot = 180.0f
            player.xRot = 0f
            player.yHeadRot = player.yRot
            player.yHeadRotO = player.yRot
            InventoryScreen.renderEntityInInventory(
                graphics,
                (x + getWidth() / 2f),
                y + getHeight() - 20f,
                getHeight() / 2.5f,
                Vector3f(),
                quaternion,
                null,
                player
            )
            player.yBodyRot = yHeadRot
            player.yRot = yRot
            player.xRot = xRot
            player.yHeadRot = yHeadRotO
            player.yHeadRotO = yBodyRot
        } else {
            this.renderString(graphics, McFont, 0xFFFFFF)
        }
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean = isMouseOver(mouseX, mouseY) && isValidClickButton(button)


    override fun mouseDragged(mouseX: Double, mouseY: Double, button: Int, dragX: Double, dragY: Double): Boolean {
        yRotation += dragX.toFloat() * 0.15f
        return true
    }

    companion object {
        private var yRotation = Mth.PI / 4
    }
}