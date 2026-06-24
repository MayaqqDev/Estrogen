package dev.mayaqq.estrogen.client.content.screen.config

import dev.mayaqq.cynosure.helpers.McClient
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component
import uwu.serenity.kittyconfig.elements.ConfigElement
import uwu.serenity.kittyconfig.minecraft.KittyConfigRegistry
import uwu.serenity.kittyconfig.save

class KittyConfigScreen(
    val previous: Screen?,
    val entries: List<ConfigElement>,
    val id: String,
    val previousCategory: KittyConfigScreen? = null,
    ) : Screen(Component.literal(id)) {

    var entryList: ConfigEntryList? = null

    override fun init() {
        entryList = ConfigEntryList(entries, 32, this.height - 32, this.width, this.height, 30)

        entryList?.let { list ->
            entries.forEach { entry ->
                list.add(previous?: return, entry, id, this)
            }

            this.addWidget(list)
        }

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE) {
            this.onClose()
        }.bounds(if (previousCategory != null) this.width / 2 + 5 else this.width / 2 - 100, this.height - 27, if (previousCategory != null) 95 else 200, 20).build())
        previousCategory?.let { screen ->
            this.addRenderableWidget(Button.builder(CommonComponents.GUI_BACK) {
                McClient.setScreen(screen)
            }.bounds(this.width / 2 - 100, this.height - 27, 95, 20).build())
        }
    }

    override fun render(
        guiGraphics: GuiGraphics,
        mouseX: Int,
        mouseY: Int,
        partialTick: Float
    ) {
        this.renderBackground(guiGraphics)
        entryList?.render(guiGraphics, mouseX, mouseY, partialTick)
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 16777215)
        super.render(guiGraphics, mouseX, mouseY, partialTick)
    }

    override fun onClose() {
        KittyConfigRegistry[id]?.save()
        if (hasShiftDown()) {
            McClient.setScreen(null)
        } else {
            previous?.let { McClient.setScreen(it) }?: super.onClose()
        }
    }
}