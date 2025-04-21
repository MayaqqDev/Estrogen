package dev.mayaqq.estrogen.client.features.dash

import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import dev.mayaqq.cynosure.client.events.render.BeginHudRenderEvent
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.utils.Environment
import dev.mayaqq.cynosure.utils.colors.Color
import dev.mayaqq.cynosure.utils.colors.floatBlue
import dev.mayaqq.cynosure.utils.colors.floatGreen
import dev.mayaqq.cynosure.utils.colors.floatRed
import dev.mayaqq.estrogen.client.features.dash.ClientDash.getDashLevel
import dev.mayaqq.estrogen.client.features.dash.ClientDash.isOnCooldown
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.utils.EstrogenColors
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.resources.ResourceLocation

@EventSubscriber(env = [Environment.CLIENT])
object DashOverlay {
    private val DASH_OVERLAY = ResourceLocation("textures/misc/nausea.png")

    @Subscription
    fun drawOverlay(event: BeginHudRenderEvent) {
        val player = Minecraft.getInstance().player
        if (player == null) return
        if (player.hasEffect(EstrogenEffects.ESTROGEN) && isOnCooldown() && TODO("EstrogenConfig.client().dashOverlay.get()")) {
            val dc: Color = EstrogenColors.getDashColor(getDashLevel(), false)
            renderOverlay(event.graphics, dc.floatRed, dc.floatGreen, dc.floatBlue)
        }
        if (TODO("DreamBlockEffect.isInDreamBlock()")) {
            renderOverlay(event.graphics, 0.2f, 0.0f, 0.2f)
        }
    }

    private fun renderOverlay(graphics: GuiGraphics, red: Float, green: Float, blue: Float) {
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
            RenderSystem.setShaderColor(red, green, blue, 1.0f)
            RenderSystem.defaultBlendFunc()
            RenderSystem.disableBlend()
            RenderSystem.depthMask(true)
            RenderSystem.enableDepthTest()
            graphics.setColor(1f, 1f, 1f, 1f)
        }
    }
}