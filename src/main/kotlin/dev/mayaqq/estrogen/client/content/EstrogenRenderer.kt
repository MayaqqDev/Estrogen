package dev.mayaqq.estrogen.client.content

import com.mojang.blaze3d.pipeline.RenderTarget
import com.mojang.blaze3d.pipeline.TextureTarget
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.shaders.Uniform
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import dev.mayaqq.cynosure.client.events.ClientTickEvent
import dev.mayaqq.cynosure.client.events.CoreShaderRegistrationEvent
import dev.mayaqq.cynosure.client.events.render.GameRenderEvent
import dev.mayaqq.cynosure.client.events.render.LevelRenderEvent
import dev.mayaqq.cynosure.client.events.render.ReloadLevelRendererEvent
import dev.mayaqq.cynosure.client.events.render.ResizeRendererEvent
import dev.mayaqq.cynosure.client.isShaderPackInUse
import dev.mayaqq.cynosure.core.Environment
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.id
import dev.mayaqq.estrogen.utils.render.blitWithDepth
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.PostChain
import net.minecraft.client.renderer.RenderStateShard
import net.minecraft.client.renderer.RenderStateShard.OutputStateShard
import net.minecraft.client.renderer.ShaderInstance

@EventSubscriber(env = [Environment.CLIENT])
object EstrogenRenderer {

    // Render state shards
    val SHADER_BYPASS: OutputStateShard = OutputStateShard("shader_bypass",
        { if (isShaderPackInUse) shaderBypassTarget?.bindWrite(Minecraft.ON_OSX) },
        { if (isShaderPackInUse) Minecraft.getInstance().mainRenderTarget.bindWrite(false) }
    )

    // Shaders
    lateinit var dreamBlockShader: ShaderInstance
        private set

    lateinit var dreamBlockOverlayShader: ShaderInstance
        private set

    lateinit var cutoutColorShader: ShaderInstance
        private set

    lateinit var blitWithDepthShader: ShaderInstance
        private set

    // Post chains and framebuffers
    private var dreamingEffect: PostChain? = null

    private var shaderBypassTarget: RenderTarget? = null

    @Subscription
    fun onLoadShaders(event: CoreShaderRegistrationEvent) {
        event.register(id("rendertype_estrogen_dream"), DefaultVertexFormat.BLOCK, ::dreamBlockShader)
        event.register(id("dreamblock_overlay"), DefaultVertexFormat.POSITION_COLOR, ::dreamBlockOverlayShader)
        event.register(id("blit_with_depth"), DefaultVertexFormat.BLIT_SCREEN, ::blitWithDepthShader)
        //event.register(recipeId("particle_cutout_color"), DefaultVertexFormat.PARTICLE, ::cutoutColorShader)
    }

    @Subscription
    fun onReloadRenderer(event: ReloadLevelRendererEvent) {
        val minecraft = Minecraft.getInstance()
        dreamingEffect?.close()
        dreamingEffect = PostChain(
            minecraft.textureManager,
            minecraft.resourceManager,
            minecraft.mainRenderTarget,
            id("shaders/post/dreaming.json")
        )
        dreamingEffect?.resize(minecraft.window.width, minecraft.window.height)

        if (isShaderPackInUse) {
            if (shaderBypassTarget == null) shaderBypassTarget = TextureTarget(
                minecraft.window.width, minecraft.window.height, true, Minecraft.ON_OSX
            )
        } else {
            shaderBypassTarget?.destroyBuffers()
            shaderBypassTarget = null
        }
    }

    @Subscription
    fun onBeginRender(event: LevelRenderEvent.AfterEntities) {
        shaderBypassTarget?.clear(Minecraft.ON_OSX)
        shaderBypassTarget?.copyDepthFrom(Minecraft.getInstance().mainRenderTarget)
    }

    @Subscription
    fun onEndRender(event: LevelRenderEvent.End) {

        if (isShaderPackInUse) {
            val window = Minecraft.getInstance().window
            RenderSystem.enableBlend()
            RenderSystem.blendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ZERO,
                GlStateManager.DestFactor.ONE
            )
            shaderBypassTarget?.blitWithDepth(window.width, window.height)
            RenderSystem.disableBlend()
            RenderSystem.defaultBlendFunc()
        }

        if (Minecraft.getInstance().player?.hasEffect(EstrogenEffects.Dreaming) == true)
            dreamingEffect?.process(event.partialTick)
    }

    @Subscription
    fun onResizeRenderer(event: ResizeRendererEvent) {
        dreamingEffect?.resize(event.width, event.height)
        shaderBypassTarget?.resize(event.width, event.height, Minecraft.ON_OSX)
    }

    var clientTickCounter = 0

    @Subscription
    fun onGameRender(event: GameRenderEvent) {
        if (::dreamBlockShader.isInitialized) {
            dreamBlockShader.getUniform("CeaselessGameTime")?.set(((clientTickCounter % 24000L + event.partialTick) / 24000.0F))
        }
    }

    @Subscription
    fun tickEvent(event: ClientTickEvent.Begin) {
        clientTickCounter++
    }
}