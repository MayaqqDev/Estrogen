package dev.mayaqq.estrogen.client.features.dash

import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferBuilder
import com.mojang.blaze3d.vertex.BufferUploader
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import dev.mayaqq.cynosure.client.render.gui.HudOverlay
import dev.mayaqq.cynosure.client.utils.lastPose
import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.cynosure.utils.colors.Color
import dev.mayaqq.cynosure.utils.colors.floatBlue
import dev.mayaqq.cynosure.utils.colors.floatGreen
import dev.mayaqq.cynosure.utils.colors.floatRed
import dev.mayaqq.estrogen.client.content.EstrogenRenderer
import dev.mayaqq.estrogen.client.content.blockRenderers.dreamBlock.texture.DynamicDreamTexture
import dev.mayaqq.estrogen.client.features.TextRendererFeatures
import dev.mayaqq.estrogen.client.features.dash.ClientDash.getDashLevel
import dev.mayaqq.estrogen.client.features.dash.ClientDash.isOnCooldown
import dev.mayaqq.estrogen.config.EstrogenClientConfig
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.utils.EstrogenColors
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.resources.ResourceLocation
import org.joml.Matrix4f

object DashOverlay : HudOverlay {
    private val DASH_OVERLAY = ResourceLocation("textures/misc/nausea.png")

    override fun render(gui: Gui, graphics: GuiGraphics, partialTick: Float) {
        val player = Minecraft.getInstance().player ?: return
        if (player.hasEffect(EstrogenEffects.Estrogen) && isOnCooldown() && EstrogenClientConfig.UI.dashOverlay) {
            val dc: Color = EstrogenColors.getDashColor(getDashLevel(), false)
            renderOverlay(graphics, dc.floatRed, dc.floatGreen, dc.floatBlue)
        }
        if (DreamBlockEffect.isInDreamBlock) {
            if (DreamBlockEffect.isEyeInDream) {
                renderDream(graphics, partialTick)
            } else {
                renderOverlay(graphics, 0.2f, 0.0f, 0.2f)
            }
        }
        if (TextRendererFeatures.obfuscate) {
            renderOverlay(graphics, 0.1f, 0.3f, 0.3f, 0.1f)
        }
    }

    private fun renderOverlay(graphics: GuiGraphics, red: Float, green: Float, blue: Float, alpha: Float = 1.0f) {
        graphics.pushPop {
            translate(graphics.guiWidth().toFloat() / 2.0f, graphics.guiHeight().toFloat() / 2.0f, 0.0f)
            scale(1.5f, 1.5f, 1.5f)
            translate((-graphics.guiWidth()).toFloat() / 2.0f, (-graphics.guiHeight()).toFloat() / 2.0f, 0.0f)

            RenderSystem.disableDepthTest()
            RenderSystem.depthMask(false)
            RenderSystem.enableBlend()
            RenderSystem.blendFuncSeparate(
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ONE,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ONE
            )
            graphics.setColor(red, green, blue, 1.0f)
            graphics.blit(
                DASH_OVERLAY,
                0,
                0,
                -90,
                0.0f,
                0.0f,
                graphics.guiWidth(),
                graphics.guiHeight(),
                graphics.guiWidth(),
                graphics.guiHeight()
            )
            RenderSystem.setShaderColor(red, green, blue, alpha)
            RenderSystem.defaultBlendFunc()
            RenderSystem.disableBlend()
            RenderSystem.depthMask(true)
            RenderSystem.enableDepthTest()
            graphics.setColor(1f, 1f, 1f, 1f)
        }
    }

    private fun renderDream(graphics: GuiGraphics, partialTick: Float) {
        graphics.pushPop {
            translate(graphics.guiWidth().toFloat() / 2.0f, graphics.guiHeight().toFloat() / 2.0f, 0.0f)
            scale(1.5f, 1.5f, 1.5f)
            translate((-graphics.guiWidth()).toFloat() / 2.0f, (-graphics.guiHeight()).toFloat() / 2.0f, 0.0f)

            RenderSystem.setShaderTexture(0, DynamicDreamTexture.ID)
            RenderSystem.setShader { EstrogenRenderer.dreamBlockOverlayShader }
            val bufferBuilder = Tesselator.getInstance().builder
            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR)
            val width = graphics.guiWidth()
            val height = graphics.guiHeight()
            vertex(bufferBuilder, lastPose, 0, 0, partialTick)
            vertex(bufferBuilder, lastPose, 0, height, partialTick)
            vertex(bufferBuilder, lastPose, width, height, partialTick)
            vertex(bufferBuilder, lastPose, width, 0, partialTick)
            BufferUploader.drawWithShader(bufferBuilder.end())
        }
    }
}

private fun vertex(bufferBuilder: BufferBuilder, pose: Matrix4f, x: Int, y: Int, partialTick: Float) {
    val eyeTime = (DreamBlockEffect.eyeDreamTick.toFloat() + partialTick - 5f).coerceIn(0f, 5f) / 5f
    bufferBuilder
        .vertex(pose, x.toFloat(), y.toFloat(), -90f)
        .color(eyeTime, 0f, 0f, 0f)
        .endVertex()
}