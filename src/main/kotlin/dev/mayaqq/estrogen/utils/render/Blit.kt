package dev.mayaqq.estrogen.utils.render

import com.mojang.blaze3d.pipeline.RenderTarget
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferUploader
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import com.mojang.blaze3d.vertex.VertexSorting
import dev.mayaqq.cynosure.client.utils.color
import dev.mayaqq.cynosure.client.utils.uv
import dev.mayaqq.cynosure.client.utils.vertex
import dev.mayaqq.estrogen.client.content.EstrogenRenderer
import invoke.kitty.kritter.utils.color.White
import net.minecraft.client.Minecraft
import org.joml.Matrix4f


internal fun RenderTarget.blitWithDepth(width: Int, height: Int) {
    RenderSystem.assertOnRenderThread()
    GlStateManager._colorMask(true, true, true, false)
    GlStateManager._disableDepthTest()
    GlStateManager._depthMask(false)
    GlStateManager._viewport(0, 0, width, height)

    val mc = Minecraft.getInstance()
    val shader = EstrogenRenderer.blitWithDepthShader
    shader.setSampler("DiffuseSampler", this.colorTextureId)
    shader.setSampler("DiffuseDepthSampler", this.depthTextureId)
    shader.setSampler("MainDepthSampler", mc.mainRenderTarget.depthTextureId)
    val pose = Matrix4f().setOrtho(0.0f, width.toFloat(), height.toFloat(), 0.0f, 1000.0f, 3000.0f)
    RenderSystem.setProjectionMatrix(pose, VertexSorting.ORTHOGRAPHIC_Z)
    if (shader.MODEL_VIEW_MATRIX != null) {
        shader.MODEL_VIEW_MATRIX!!.set((Matrix4f()).translation(0.0f, 0.0f, -2000.0f))
    }

    if (shader.PROJECTION_MATRIX != null) {
        shader.PROJECTION_MATRIX!!.set(pose)
    }

    shader.apply()
    val outwidth = this.viewWidth.toFloat() / this.width.toFloat()
    val outheight = this.viewHeight.toFloat() / this.height.toFloat()
    val tesselator = RenderSystem.renderThreadTesselator()
    val builder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR).apply {
        vertex(0.0, height.toDouble(), 0.0).uv(0.0f, 0.0f).color(White.withAlpha(255))
        vertex(width.toDouble(), height.toDouble(), 0.0).uv(outwidth, 0.0f).color(White.withAlpha(255))
        vertex(width.toDouble(), 0.0, 0.0).uv(outwidth, outheight).color(White.withAlpha(255))
        vertex(0.0, 0.0, 0.0).uv(0.0f, outheight).color(White.withAlpha(255))
    }
    BufferUploader.draw(builder.build()!!)
    shader.clear()
    GlStateManager._depthMask(true)
    GlStateManager._colorMask(true, true, true, true)
}