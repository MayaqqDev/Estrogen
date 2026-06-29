package dev.mayaqq.estrogen.client.content.screen.modules

import com.mojang.blaze3d.platform.NativeImage
import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.cynosure.client.utils.translate
import dev.mayaqq.cynosure.core.mod.Mod
import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.helpers.McFont
import dev.mayaqq.cynosure.text.Text
import dev.mayaqq.cynosure.text.TextBuilder.append
import dev.mayaqq.cynosure.text.TextStyle.color
import dev.mayaqq.cynosure.text.TextStyle.italic
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.api.EstrogenFlag
import dev.mayaqq.estrogen.api.EstrogenModule
import dev.mayaqq.estrogen.client.content.screen.BaseEstrogenScreen
import dev.mayaqq.estrogen.client.content.screen.EstrogenButton
import dev.mayaqq.estrogen.client.content.screen.EstrogenMenuScreen.Companion.transBlue
import dev.mayaqq.estrogen.id
import dev.mayaqq.estrogen.modules.getModules
import invoke.kitty.kritter.utils.color.LightBlue
import invoke.kitty.kritter.utils.color.MinecraftColors
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.renderer.texture.DynamicTexture
import net.minecraft.resources.ResourceLocation
import java.io.FileNotFoundException
import kotlin.io.path.Path
import kotlin.math.max

class ModulesScreen(previous: Screen?) : BaseEstrogenScreen(previous, Text.of("estrogen.screen.modules.title")) {

    var scrollProgress = 0.0

    val bClose = EstrogenButton.Builder(EstrogenButton.TextRenderer(Text.translatable("estrogen.button.close"))) {
        this.onClose()
    }

    val buttonWidth = max(modules.map { McFont.width(it.mod.name) + 2 + 32 + 12 }.maxOf { it }, 100)

    override fun baseInit() {

        modules.sortedBy { it.modid.length }.forEachIndexed { index, (module, mod, modid, modname) ->
            EstrogenButton.Builder(
                ModInfoRenderer(mod, module)
            ) {
                McClient.setScreen(module.createConfigScreen().invoke(this))
            }.bounds(20 + ((buttonWidth + 10) * (index)) - scrollProgress.toInt(), 40, buttonWidth, height - 80).color(module.color)
                .buildAndAdd()
        }
        bClose.bounds(this.width / 2 - 100, this.height - 30, 200, 20).color(transBlue).buildAndAdd()
    }

    override fun beforeRender(
        graphics: GuiGraphics,
        mouseX: Int,
        mouseY: Int,
        partialTick: Float
    ) {
        val text = Text.translatable("estrogen.button.module_configs") { color = transBlue }
        graphics.drawString(
            font,
            text,
            this.width / 2 - font.width(text) / 2,
            19 - font.lineHeight / 2,
            0xFFFFFF,
            false
        )
    }

    override fun isPauseScreen(): Boolean = true

    override fun mouseScrolled(mouseX: Double, mouseY: Double, scrollX: Double, scrollY: Double): Boolean {
        if (scrollProgress + scrollY > 0.0 && scrollProgress + scrollY < (modules.size * (10 + buttonWidth) - width + 30)) {
            scrollProgress += scrollY
            rebuildWidgets()
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY)
    }

    private val modules
        get() =  getModules() //for testing: List(8) { getModules().first() }

    private class ModInfoRenderer(val mod: Mod, val module: EstrogenModule) : EstrogenButton.Renderer {
        override fun EstrogenButton.renderComponents(
            graphics: GuiGraphics,
            mouseX: Int,
            mouseY: Int,
            partialTick: Float
        ) {
            val id = id("icon_${mod.modid}")

            //Padding: 4
            // Mod name
            graphics.drawString(McClient.font, Text.of {
                append(mod.name) {
                    color = this@renderComponents.color
                }
            }, x + 4, y + 4 + (16 - (McFont.lineHeight / 2)), 0xFFFFFF, false)
            // Description
            graphics.drawString(McClient.font, Text.of {
                append("(${mod.modid})") {
                    color = MinecraftColors.DarkGray
                }
            }, x + 4, y + 4 + (16 - (McFont.lineHeight / 2)) + McFont.lineHeight, 0xFFFFFF, false)
            graphics.drawWordWrap(McFont, Text.of {
                append(module.description) {
                    color = MinecraftColors.Gray
                    italic = true
                }
            }, x + 4, y + 40, width - 8,0xFFFFFF)

            graphics.drawString(McFont, Text.of {
                append("Modifies Base: ") {
                    color = LightBlue
                }
                val modifies = module.hasFlag(EstrogenFlag.MODIFIES_BASE_ESTROGEN)
                append(if (modifies) "✔" else "❌") {
                    color = if (modifies) MinecraftColors.Green else MinecraftColors.Red
                }
            }, x + 4, y + height - McFont.lineHeight - 4, 0xFFFFFF, false)

            try {
                if (iconErrorCache.contains(mod.modid)) return
                // Icon
                if (!iconCache.contains(mod.modid)) {
                    mod.logoFile?.let { string ->
                        //TODO: The icons don't load :(
                        val stream = Path(string).toFile().inputStream()
                        val image = NativeImage.read(stream)
                        val resized = NativeImage(image.format(), 64, 64, true)
                        image.resizeSubRectTo(0, 0, image.width, image.height, resized)
                        val texture = DynamicTexture(resized)
                        McClient.textureManager.register(id, texture)
                        iconCache.put(mod.modid, id)
                    }
                }
                graphics.pushPop {
                    translate(x + width - 32 - 4, y + 4, 0)
                    scale(0.5F, 0.5F, 0.0F)
                    graphics.blit(iconCache.get(mod.modid)?: return, 0, 0, 0F, 0F, 64, 64, 64, 64)
                }
            } catch (e: FileNotFoundException) {
                iconErrorCache.add(mod.modid)
                Estrogen.error("Cannot find icon for mod ${mod.modid}: $e")
            }

        }

        companion object {
            private val iconErrorCache = mutableListOf<String>()
            private val iconCache = hashMapOf<String, ResourceLocation>()
        }
    }

}