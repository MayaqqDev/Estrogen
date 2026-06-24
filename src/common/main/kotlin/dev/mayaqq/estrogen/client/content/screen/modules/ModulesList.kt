package dev.mayaqq.estrogen.client.content.screen.modules

import com.mojang.blaze3d.platform.NativeImage
import com.mojang.blaze3d.systems.RenderSystem
import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.utils.colors.Purple
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.client.content.screen.EstrogenButton
import dev.mayaqq.estrogen.id
import dev.mayaqq.estrogen.modules.ModuleContainer
import dev.mayaqq.estrogen.modules.getModules
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.ContainerObjectSelectionList
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.narration.NarratableEntry
import net.minecraft.client.renderer.texture.DynamicTexture
import net.minecraft.resources.ResourceLocation
import kotlin.io.path.Path

class ModulesList(val screen: ModulesScreen) : ContainerObjectSelectionList<ModulesList.Entry>(
    McClient,
    screen.width,
    screen.height,
    32,
    screen.height - 32,
    150
) {

    init {
        val modules = getModules().sortedBy { it.mod.name }
        modules.forEach { module ->
            Estrogen.info(module.modname)
            this.addEntry(ModuleEntry(module, this))
            this.addEntry(ModuleEntry(module, this))
            this.addEntry(ModuleEntry(module, this))
            this.addEntry(ModuleEntry(module, this))
            this.addEntry(ModuleEntry(module, this))
        }
    }

    abstract class Entry : ContainerObjectSelectionList.Entry<Entry>()

    class ModuleEntry(module: ModuleContainer, modulesList: ModulesList) : Entry() {

        val button = EstrogenButton.Builder(ModuleButtonRenderer(module)) {
            McClient.setScreen(module.module.createConfigScreen().invoke(modulesList.screen))
        }.bounds(0, 0, modulesList.rowWidth, 150).build()

        override fun render(graphics: GuiGraphics, index: Int,
            top: Int, left: Int, width: Int, height: Int,
            mouseX: Int, mouseY: Int, hovering: Boolean, partialTicks: Float
        ) {
            graphics.renderOutline(left, top, width, height, Purple.toInt())
            this.button.setPosition(left, top)
            this.button.render(graphics, mouseX, mouseY, partialTicks)
        }

        override fun children(): List<GuiEventListener> = listOf(button)

        override fun narratables(): List<NarratableEntry> = listOf(button)
    }

    class ModuleButtonRenderer(val module: ModuleContainer): EstrogenButton.Renderer {
        override fun EstrogenButton.renderComponents(
            graphics: GuiGraphics,
            mouseX: Int,
            mouseY: Int,
            partialTick: Float
        ) {
            graphics.pushPop {
                module.mod.logoFile?.let { logoFilePath ->
                    val icon = logos.getOrElse(module.mod.modid) {
                        val file = Path(logoFilePath).toFile()
                        val image = NativeImage.read(file.inputStream())
                        val texture = DynamicTexture(image)
                        val id = id(module.mod.name + "_logo_texture_estrogen")
                        McClient.textureManager.register(id, texture)
                        val imageClass = Image(id, image.width, image.height)
                        logos.put(module.mod.modid, imageClass)
                        imageClass
                    }

                    RenderSystem.setShaderTexture(0, icon.icon)
                    graphics.blit(icon.icon, this@renderComponents.x + 6, this@renderComponents.y + 6, 0F, 0F, icon.width, icon.height, icon.width, icon.height)
                }
            }
        }
    }
}

private val logos = hashMapOf<String, Image>()

private data class Image(val icon: ResourceLocation, val width: Int, val height: Int)