package dev.mayaqq.estrogen.client.content

import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import dev.mayaqq.cynosure.client.events.CoreShaderRegistrationEvent
import dev.mayaqq.cynosure.client.events.render.LevelRenderEvent
import dev.mayaqq.cynosure.client.events.render.ReloadLevelRendererEvent
import dev.mayaqq.cynosure.client.events.render.ResizeRendererEvent
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.utils.Environment
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.id
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.PostChain
import net.minecraft.client.renderer.ShaderInstance

@EventSubscriber(env = [Environment.CLIENT])
object EstrogenRenderer {

    lateinit var dreamBlockShader: ShaderInstance
        private set

    private var dreamingEffect: PostChain? = null

    @Subscription
    fun onLoadShaders(event: CoreShaderRegistrationEvent) {
        event.register(id("rendertype_estrogen_dream"), DefaultVertexFormat.BLOCK, ::dreamBlockShader)
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
    }

    @Subscription
    fun onEndRender(event: LevelRenderEvent.End) {
        if (Minecraft.getInstance().player?.hasEffect(EstrogenEffects.DREAMING) == true)
            dreamingEffect?.process(event.partialTick)
    }

    @Subscription
    fun onResizeRenderer(event: ResizeRendererEvent) {
        val window = Minecraft.getInstance().window
        dreamingEffect?.resize(window.width, window.height)
    }
}