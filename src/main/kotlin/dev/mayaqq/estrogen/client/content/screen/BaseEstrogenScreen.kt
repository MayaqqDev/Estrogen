package dev.mayaqq.estrogen.client.content.screen

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.*
import dev.mayaqq.cynosure.client.utils.light
import dev.mayaqq.cynosure.client.utils.normal
import dev.mayaqq.cynosure.client.utils.uv
import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.text.Text
import dev.mayaqq.cynosure.text.TextStyle.bold
import dev.mayaqq.cynosure.text.TextStyle.color
import dev.mayaqq.estrogen.client.content.EstrogenRenderer
import dev.mayaqq.estrogen.client.content.blockRenderers.dreamBlock.texture.DynamicDreamTexture
import dev.mayaqq.estrogen.client.content.blockRenderers.dreamBlock.texture.DynamicDreamTexture.ID
import invoke.kitty.kritter.utils.color.Red
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.renderer.LightTexture
import net.minecraft.network.chat.Component
import org.joml.Matrix4f

abstract class BaseEstrogenScreen(val previous: Screen?, title: Component) : Screen(title) {

    val closeButton = EstrogenButton.Builder(EstrogenButton.TextRenderer(Text.of("X") {
        color = Red
        bold = true
    })) {
        McClient.setScreen(null)
    }

    abstract fun baseInit()

    override fun init() {
        DynamicDreamTexture.prepareIfNeeded()
        DynamicDreamTexture.generateIfNeeded()
        baseInit()
        val buttonSize = 25
        val cornerOffset = 10
        closeButton.bounds(width - buttonSize - cornerOffset, height - buttonSize - cornerOffset, buttonSize, buttonSize).buildAndAdd()
    }

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        renderBackground(graphics, mouseX, mouseY, partialTick)
        beforeRender(graphics, mouseX, mouseY, partialTick)
        super.render(graphics, mouseX, mouseY, partialTick)
        afterRender(graphics, mouseX, mouseY, partialTick)
    }

    open fun beforeRender(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {}

    open fun afterRender(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {}

    fun EstrogenButton.Builder.buildAndAdd(): EstrogenButton = addRenderableWidget(this.build())

    fun AbstractWidget.add(): AbstractWidget = addRenderableWidget(this)

    fun AbstractWidget.addRenderable(): AbstractWidget = addRenderableOnly(this)

    override fun renderBackground(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        renderDream(graphics, 0, width, 0, height)
        graphics.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680)
    }

    private fun renderDream(graphics: GuiGraphics, minX: Int, maxX: Int, minY: Int, maxY: Int) {
        RenderSystem.setShaderTexture(0, ID)
        RenderSystem.setShader(EstrogenRenderer::dreamBlockShader)
        val bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK)
        dreamVertex(bufferBuilder, graphics.pose().last().pose(), minX, minY)
        dreamVertex(bufferBuilder, graphics.pose().last().pose(), minX, maxY)
        dreamVertex(bufferBuilder, graphics.pose().last().pose(), maxX, maxY)
        dreamVertex(bufferBuilder, graphics.pose().last().pose(), maxX, minY)
        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow())
    }

    private fun dreamVertex(bufferBuilder: BufferBuilder, pose: Matrix4f, x: Int, y: Int) {
        bufferBuilder.addVertex(pose, x.toFloat(), y.toFloat(), 0f)
            .setColor(0, 0, 0, 0)
            .uv(x.toFloat(), y.toFloat())
            .light(LightTexture.FULL_BRIGHT)
            .normal(0f, 0f, 0f)
    }

    override fun shouldCloseOnEsc(): Boolean = true
    override fun onClose() {
        if (hasShiftDown()) McClient.setScreen(null) else previous?.let { McClient.setScreen(it) }?: super.onClose()
    }
}