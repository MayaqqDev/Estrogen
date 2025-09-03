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

class CosmeticPreview(val player: Player?, x: Int, y: Int, width: Int, height: Int) : EstrogenButton(
    x, y, width, height,
    arrayOf(),
    OnPress {},
    DEFAULT_NARRATION,
    EstrogenMenuScreen.transBlue,
    false, true
) {

    private var rotation = Mth.PI / 4

    override fun renderWidget(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.renderWidget(graphics, mouseX, mouseY, partialTick)

        if (this.message == CommonText.EMPTY) this.message = -"gui.estrogen.cosmetics.no_preview"
        if (player != null) {

            val quaternion = Quaternionf().rotateZ(Mth.PI).rotateY(rotation)
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
                (x + getWidth() / 2f).toInt(), y + getHeight() - 20,
                (getHeight() / 2.5f).toInt(), quaternion, null, player
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


    override fun mouseDragged(d: Double, e: Double, i: Int, f: Double, g: Double): Boolean {
        this.rotation += f.toFloat() * 0.15f
        return true
    }
}