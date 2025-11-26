package dev.mayaqq.estrogen.client.content.screen.config

import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.helpers.McFont
import dev.mayaqq.estrogen.client.extensions.posX
import dev.mayaqq.estrogen.client.extensions.posY
import dev.mayaqq.estrogen.client.extensions.widgetHeight
import dev.mayaqq.estrogen.client.extensions.widgetWidth
import net.minecraft.ChatFormatting
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.*
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.narration.NarratableEntry
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import uwu.serenity.kittyconfig.elements.ConfigCategory
import uwu.serenity.kittyconfig.elements.ConfigElement
import uwu.serenity.kittyconfig.elements.ConfigField
import uwu.serenity.kittyconfig.serialization.ElementType

class ConfigEntryList(val entries: List<ConfigElement>, x: Int, y: Int, width: Int, height: Int, itemHeight: Int) : ContainerObjectSelectionList<ConfigEntryList.Entry>(
    McClient,
    width,
    height,
    x,
    y,
    itemHeight
) {

    init {
        this.centerListVertically = true
    }

    fun add(screen: Screen, element: ConfigElement, modid: String, kittyScreen: KittyConfigScreen?) {
        this.addEntry(Entry.make(screen, this.width, element, modid, kittyScreen))
    }

    class Entry(
        val screen: Screen,
        val element: ConfigElement,
        val widget: AbstractWidget,
        val nameWidget: AbstractWidget = StringWidget(Component.literal(element.name.split(pascalCaseRegex).joinToString(" ")), McFont)
            .alignLeft()
            .apply { this.tooltip = Tooltip.create(Component.literal(element.comment?: "No Description Provided")) },
        val children: List<AbstractWidget> = listOf(widget, nameWidget)
    ) : ContainerObjectSelectionList.Entry<Entry>() {

        override fun render(graphics: GuiGraphics, index: Int, y: Int, x: Int, width: Int, height: Int, mouseX: Int, mouseY: Int, hovering: Boolean, delta: Float) {
            widget.posY = y
            nameWidget.posX = x
            nameWidget.posY = y
            nameWidget.widgetWidth = width - 100
            nameWidget.widgetHeight = height
            nameWidget.render(graphics, mouseX, mouseY, delta)
            widget.render(graphics, mouseX, mouseY, delta)
        }

        override fun children(): List<GuiEventListener> = children
        override fun narratables(): List<NarratableEntry> = children

        companion object {
            fun make(
                screen: Screen,
                guiWidth: Int,
                element: ConfigElement,
                modid: String,
                previous: KittyConfigScreen?
            ): Entry {
                return when (element.type) {
                    is ElementType.CATEGORY -> {
                        return Entry(screen, element, Button.builder(
                            Component.literal("Open")
                        ) {
                            McClient.setScreen(KittyConfigScreen(screen, (element as ConfigCategory).elements.toList(), modid, previous))
                        }.bounds(guiWidth / 2 + 20, 5, 100, 20).build())
                    }
                    ElementType.BOOLEAN -> {
                        val element = element as ConfigField<Boolean>
                        Entry(screen, element, Button.builder(element.value.toComponent()) {
                            element.value = !element.value
                            it.message = element.value.toComponent()
                        }.bounds(guiWidth / 2 + 20, 5, 100, 20).build())
                    }
                    ElementType.INT -> Entry(screen, element, ConfigTextWidget(element as ConfigField<Int>, guiWidth))
                    ElementType.BYTE -> Entry(screen, element, ConfigTextWidget(element as ConfigField<Byte>, guiWidth))
                    ElementType.DOUBLE -> Entry(screen, element, ConfigTextWidget(element as ConfigField<Double>, guiWidth))
                    ElementType.FLOAT -> Entry(screen, element, ConfigTextWidget(element as ConfigField<Float>, guiWidth))
                    ElementType.LONG -> Entry(screen, element, ConfigTextWidget(element as ConfigField<Long>, guiWidth))
                    ElementType.SHORT -> Entry(screen, element, ConfigTextWidget(element as ConfigField<Short>, guiWidth))
                    ElementType.STRING ->  Entry(screen, element, ConfigTextWidget(element as ConfigField<String>, guiWidth))
                    //is ElementType.COLLECTION -> TODO()
                    //is ElementType.ENUM -> TODO()
                    //is ElementType.OBJECT -> TODO()
                    else -> Entry(screen, element, StringWidget(guiWidth / 2 + 20, 5, 100, 20, Component.literal("ERROR"), McFont))
                }
            }

            val pascalCaseRegex = Regex("(?<!^)(?=[A-Z]|(?<!\\d)\\d)")

            fun Boolean.toComponent(): Component = when(this) {
                true -> Component.literal("Enabled").withStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))
                false -> Component.literal("Disabled").withStyle(Style.EMPTY.withColor(ChatFormatting.RED))
            }
        }
    }
}