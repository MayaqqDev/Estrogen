package dev.mayaqq.estrogen.client.content.screen

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.*
import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.cynosure.client.utils.translate
import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.helpers.openUri
import dev.mayaqq.cynosure.text.Text
import dev.mayaqq.cynosure.text.TextStyle.bold
import dev.mayaqq.cynosure.text.TextStyle.color
import dev.mayaqq.cynosure.utils.colors.Color
import dev.mayaqq.cynosure.utils.colors.LightBlue
import dev.mayaqq.cynosure.utils.colors.White
import dev.mayaqq.estrogen.client.content.EstrogenRenderer
import dev.mayaqq.estrogen.client.content.blockRenderers.dreamBlock.texture.DynamicDreamTexture
import dev.mayaqq.estrogen.client.content.blockRenderers.dreamBlock.texture.DynamicDreamTexture.ID
import dev.mayaqq.estrogen.id
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Tooltip
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.sounds.SoundEvents
import org.joml.Matrix4f

class EstrogenMenuScreen(val previous: Screen?) : Screen(Text.translatable("estrogen.screen.menu.title")) {

    override fun isPauseScreen(): Boolean = false
    override fun shouldCloseOnEsc(): Boolean = true

    val titleText = Text.of("Estrogen") {
        color = LightBlue
        bold = true
    }

    val bConfig = EstrogenButton.Builder(EstrogenButton.TextRenderer(Text.translatable("estrogen.button.config"))) {
        //TODO: open screen
    }
    val bModuleConfigs = EstrogenButton.Builder(EstrogenButton.TextRenderer(Text.translatable("estrogen.button.module_configs"))) {
        //TODO: open screen
    }
    val bCosmetics = EstrogenButton.Builder(EstrogenButton.TextRenderer(Text.translatable("estrogen.button.cosmetics"))) {
        //TODO: open cosmetics screen
    }
    val bColonThree = EstrogenButton.Builder(EstrogenButton.TextRenderer(Text.translatable("estrogen.button.colon_three"))) {
        McClient.soundManager.play(SimpleSoundInstance.forUI(SoundEvents.CAT_AMBIENT, 1.0F))
    }
    val bClose = EstrogenButton.Builder(EstrogenButton.TextRenderer(Text.translatable("estrogen.button.close"))) {
        this.onClose()
    }

    val bModrinth = EstrogenButton.Builder(EstrogenButton.IconRenderer(id("textures/gui/icons/modrinth.png"))) {
        McClient.openUri("https://mods.gay/estrogen")
    }
    val bCurseforge = EstrogenButton.Builder(EstrogenButton.IconRenderer(id("textures/gui/icons/curseforge.png"))) {
        McClient.openUri("https://www.curseforge.com/minecraft/mc-mods/estrogen")
    }
    val bGithub = EstrogenButton.Builder(EstrogenButton.IconRenderer(id("textures/gui/icons/github.png"))) {
        McClient.openUri("https://github.com/MayaqqDev/Estrogen")
    }
    val bPatreon = EstrogenButton.Builder(EstrogenButton.IconRenderer(id("textures/gui/icons/patreon.png"))) {
        McClient.openUri("https://www.patreon.com/mayaqq")
    }
    val bDiscord = EstrogenButton.Builder(EstrogenButton.IconRenderer(id("textures/gui/icons/discord.png"))) {
        McClient.openUri("https://discord.gg/hue")
    }

    override fun init() {
        if (shouldGenerateNewTexture) {
            DynamicDreamTexture.prepare()
            DynamicDreamTexture.changeSeed(0xB00B5)
            generatedTexture = true
        }
        val y = this.height / 4 + 48
        val rowHeight = 24

        bConfig.bounds(this.width / 2 - 100, y, 200, 20).color(transBlue).buildAndAdd()
        bModuleConfigs.bounds(this.width / 2 - 100, y + rowHeight * 1, 200, 20).color(transPink).buildAndAdd()
        bCosmetics.bounds(this.width / 2 - 100, y + rowHeight * 2, 200, 20).color(transWhite).buildAndAdd()
        bColonThree.bounds(this.width / 2 - 100, y + rowHeight * 3, 200, 20).color(transPink).buildAndAdd()
        bClose.bounds(this.width / 2 - 100, y + rowHeight * 4, 200, 20).color(transBlue).buildAndAdd()

        bModrinth.bounds(this.width / 2 - 100 - 24, y, 20, 20).color(transBlue)
            .tooltip(Tooltip.create(Text.of("Modrinth")))
            .buildAndAdd()
        bCurseforge.bounds(this.width / 2 - 100 - 24, y + rowHeight * 1, 20, 20).color(transPink)
            .tooltip(Tooltip.create(Text.of("Curseforge")))
            .buildAndAdd()
        bGithub.bounds(this.width / 2 - 100 - 24, y + rowHeight * 2, 20, 20).color(transWhite)
            .tooltip(Tooltip.create(Text.of("Github")))
            .buildAndAdd()
        bPatreon.bounds(this.width / 2 - 100 - 24, y + rowHeight * 3, 20, 20).color(transPink)
            .tooltip(Tooltip.create(Text.of("Patreon")))
            .buildAndAdd()
        bDiscord.bounds(this.width / 2 - 100 - 24, y + rowHeight * 4, 20, 20).color(transBlue)
            .tooltip(Tooltip.create(Text.of("Discord")))
            .buildAndAdd()
    }

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        renderBackground(graphics)
        graphics.pushPop {
            translate(width / 2 - ((font.width(titleText) / 2) * 3), height / 4 + 48 - 48, 0F)
            scale(3F, 3F, 0F)
            graphics.drawString(
                font,
                titleText,
                0,
                0,
                White.toInt(),
                false
            )
        }

        super.render(graphics, mouseX, mouseY, partialTick)
    }

    override fun renderBackground(gui: GuiGraphics) {
        renderDream(gui, 0, this@EstrogenMenuScreen.width, 0, this@EstrogenMenuScreen.height)
        gui.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680)
    }

    override fun onClose() {
        previous?.let { McClient.setScreen(it) }?: super.onClose()
    }

    fun EstrogenButton.Builder.buildAndAdd() {
        addRenderableWidget(this.build())
    }

    private fun renderDream(graphics: GuiGraphics, minX: Int, maxX: Int, minY: Int, maxY: Int) {
        RenderSystem.setShaderTexture(0, ID)
        RenderSystem.setShader(EstrogenRenderer::dreamBlockShader)
        val bufferBuilder = Tesselator.getInstance().builder
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK)
        vertex(bufferBuilder, graphics.pose().last().pose(), minX, minY)
        vertex(bufferBuilder, graphics.pose().last().pose(), minX, maxY)
        vertex(bufferBuilder, graphics.pose().last().pose(), maxX, maxY)
        vertex(bufferBuilder, graphics.pose().last().pose(), maxX, minY)
        BufferUploader.drawWithShader(bufferBuilder.end())
    }

    private fun vertex(bufferBuilder: BufferBuilder, pose: Matrix4f, x: Int, y: Int) {
        bufferBuilder.vertex(pose, x.toFloat(), y.toFloat(), 0f)
            .color(0, 0, 0, 0)
            .uv(x.toFloat(), y.toFloat())
            .uv2(LightTexture.FULL_BRIGHT)
            .normal(0f, 0f, 0f)
            .endVertex()
    }

    companion object {
        private var generatedTexture = false
        private val shouldGenerateNewTexture
            get() = !generatedTexture && !DynamicDreamTexture.init

        val transWhite = Color(0xFFFFFFFFu)
        val transPink = Color(0xFFF5A9B8u)
        val transBlue = Color(0xFF5BCEFAu)
    }
}