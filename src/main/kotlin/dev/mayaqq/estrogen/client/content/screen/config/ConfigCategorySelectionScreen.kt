package dev.mayaqq.estrogen.client.content.screen.config

import dev.mayaqq.cynosure.helpers.McClient
import invoke.kitty.kritter.config.api.Config
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component

class ConfigCategorySelectionScreen(val previous: Screen?, val configs: List<Config>) : Screen(Component.literal("Select a Config Category")) {
    override fun init() {
        configs.forEachIndexed { index, config ->
            val config = config
            val size = 100
            val fullSize = ((size + 30) * configs.size) - 30
            val index0 = (width / 2) - (fullSize / 2)
            val button = ConfigSelectionButton(index0 + ((size + 30) * index), (height / 2) - (size / 2), size, size, config.id) {
                McClient.setScreen(ConfigScreen.make(config, this))
            }
            addRenderableWidget(button)
        }

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE) {
            this.onClose()
        }.bounds(this.width / 2 - 100, this.height - 27, 200, 20).build())
    }

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 16777215)
        this.renderBackground(graphics, mouseX, mouseY, delta)
        super.render(graphics, mouseX, mouseY, delta)
    }

    override fun onClose() {
        if (hasShiftDown()) McClient.setScreen(null) else previous?.let { McClient.setScreen(it) }?: super.onClose()
    }
}