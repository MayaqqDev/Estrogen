package dev.mayaqq.estrogen.client.content.screen.modules

import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.text.CommonText
import dev.mayaqq.cynosure.text.Text
import dev.mayaqq.cynosure.text.TextBuilder.append
import dev.mayaqq.cynosure.text.TextStyle.bold
import dev.mayaqq.cynosure.text.TextStyle.color
import dev.mayaqq.cynosure.text.TextStyle.italic
import dev.mayaqq.cynosure.utils.colors.LightBlue
import dev.mayaqq.cynosure.utils.colors.McDarkGray
import dev.mayaqq.cynosure.utils.colors.McGray
import dev.mayaqq.cynosure.utils.colors.McGreen
import dev.mayaqq.cynosure.utils.colors.McRed
import dev.mayaqq.cynosure.utils.colors.Red
import dev.mayaqq.estrogen.api.EstrogenFlag
import dev.mayaqq.estrogen.client.content.screen.BaseEstrogenScreen
import dev.mayaqq.estrogen.client.content.screen.EstrogenButton
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import dev.mayaqq.estrogen.modules.getModules
import net.minecraft.client.gui.components.Tooltip

class ModulesScreen(previous: Screen?) : BaseEstrogenScreen(previous, Text.of("estrogen.screen.modules.title")) {

    override fun baseInit() {
        getModules().forEachIndexed { index, (module, mod, modid, modname) ->
            EstrogenButton.Builder(EstrogenButton.TextRenderer(Text.of(modname))) {
                McClient.setScreen(module.createConfigScreen().invoke(this))
            }.bounds(20 + (70 * (index)), height / 2, 70, 30).color(module.color)
                .tooltip(Tooltip.create(Text.of {
                    append(modname) {
                        color = module.color
                    }
                    append(" ($modid)") {
                        color = McDarkGray
                    }
                    append(CommonText.NEWLINE)
                    append(mod.description) {
                        color = McGray
                        italic = true
                    }
                    append(CommonText.NEWLINE)
                    append(CommonText.NEWLINE)
                    append("Modifies Base Estrogen: ") {
                        color = LightBlue
                    }
                    val modifies = module.hasFlag(EstrogenFlag.MODIFIES_BASE_ESTROGEN)
                    append(modifies.toString()) {
                        color = if (modifies) McRed else McGreen
                    }
                }))
                .buildAndAdd()
        }
    }

    override fun baseRender(
        graphics: GuiGraphics,
        mouseX: Int,
        mouseY: Int,
        partialTick: Float
    ) {}

    override fun isPauseScreen(): Boolean = false
}