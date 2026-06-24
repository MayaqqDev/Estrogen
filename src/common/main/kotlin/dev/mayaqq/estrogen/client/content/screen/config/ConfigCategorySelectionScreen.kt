package dev.mayaqq.estrogen.client.content.screen.config

import dev.mayaqq.cynosure.helpers.McClient
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component
import uwu.serenity.kittyconfig.elements.elements
import uwu.serenity.kittyconfig.minecraft.KittyConfigRegistry

class ConfigCategorySelectionScreen(val previous: Screen?, val ids: List<String>) : Screen(Component.literal("Select a Config Category")) {
    override fun init() {
        ids.forEachIndexed { index, id ->
            val config = KittyConfigRegistry[id]?: return
            val size = 100
            val fullSize = ((size + 30) * ids.size) - 30
            val index0 = (width / 2) - (fullSize / 2)
            val button = ConfigSelectionButton(index0 + ((size + 30) * index), (height / 2) - (size / 2), size, size, id) {
                McClient.setScreen(KittyConfigScreen(this, config.elements.toList(), id))
            }
            addRenderableWidget(button)
        }

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE) {
            this.onClose()
        }.bounds(this.width / 2 - 100, this.height - 27, 200, 20).build())
    }

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 16777215)
        this.renderBackground(graphics)
        super.render(graphics, mouseX, mouseY, delta)
    }

    override fun onClose() {
        if (hasShiftDown()) McClient.setScreen(null) else previous?.let { McClient.setScreen(it) }?: super.onClose()
    }
}