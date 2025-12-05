package dev.mayaqq.estrogen.client.content

import com.mojang.blaze3d.pipeline.RenderTarget
import com.mojang.blaze3d.pipeline.TextureTarget
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.*
import dev.engine_room.flywheel.lib.model.baked.PartialModel
import dev.engine_room.flywheel.lib.util.ShadersModHelper
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
import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.id
import dev.mayaqq.estrogen.mixin.client.accessor.LevelRendererAccessor
import dev.mayaqq.estrogen.utils.render.blitWithDepth
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.PostChain
import net.minecraft.client.renderer.RenderStateShard.OutputStateShard
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.ShaderInstance
import net.minecraft.resources.ResourceLocation
import org.lwjgl.opengl.GL11

@EventSubscriber(Environment.CLIENT)
object EstrogenRenderer {

    // Render state shards
    val SHADER_BYPASS: OutputStateShard = OutputStateShard(
        "shader_bypass",
        EstrogenRenderer::beginShaderpackBypass,
        EstrogenRenderer::endShaderpackBypass
    )

    // Outline thing
    val OUTLINE: MultiBufferSource by ::celshadeSource

    // Models
    val THIGH_HIGH: PartialModel = PartialModel.of(id("trinket/thigh_high_base"))
    val THIGH_HIGH_OVERLAY: PartialModel = PartialModel.of(id("trinket/thigh_high_overlay"))

    // Shaders
    lateinit var dreamBlockShader: ShaderInstance
        private set

    lateinit var dreamBlockOverlayShader: ShaderInstance
        private set

    lateinit var dashTrailParticleShader: ShaderInstance
        private set

    lateinit var blitWithDepthShader: ShaderInstance
        private set

    lateinit var renderTypeEntityCutoutNoDiffuseShader: ShaderInstance
        private set

    lateinit var renderTypeEntityTranslucentNoDiffuseShader: ShaderInstance
        private set

    // Post chains and framebuffers
    private var dreamingEffect: PostChain? = null

    private var celshadeEffect: PostChain? = null

    private lateinit var celshadeTarget: RenderTarget

    private var shaderBypassTarget: RenderTarget? = null

    // Misc
    private lateinit var celshadeSource: OutlineBufferSource
    internal var celshadeCounter: Int = 0


    fun getCelShadedBuffer(
        texture: ResourceLocation,
        isTranslucent: Boolean = false
    ): VertexConsumer {
        return celshadeSource.getBuffer(
            if (isTranslucent) EstrogenRenderTypes.entityTranslucentNoDiffuse(texture)
            else EstrogenRenderTypes.entityCutoutNoDiffuse(texture)
        )
    }

    @Subscription
    fun onLoadShaders(event: CoreShaderRegistrationEvent) {
        event.register(id("rendertype_estrogen_dream"), DefaultVertexFormat.BLOCK, ::dreamBlockShader)
        event.register(id("dreamblock_overlay"), DefaultVertexFormat.POSITION_COLOR, ::dreamBlockOverlayShader)
        event.register(id("blit_with_depth"), DefaultVertexFormat.BLIT_SCREEN, ::blitWithDepthShader)
        event.register(id("rendertype_entity_cutout_no_diffuse"), DefaultVertexFormat.NEW_ENTITY, ::renderTypeEntityCutoutNoDiffuseShader)
        event.register(id("rendertype_entity_translucent_no_diffuse"), DefaultVertexFormat.NEW_ENTITY, ::renderTypeEntityTranslucentNoDiffuseShader)
        event.register(id("dash_trail_particle"), DefaultVertexFormat.PARTICLE, ::dashTrailParticleShader)
    }

    @Subscription
    fun onReloadRenderer(event: ReloadLevelRendererEvent) {
        val minecraft = Minecraft.getInstance()
        dreamingEffect?.close()
        celshadeEffect?.close()
        dreamingEffect = PostChain(
            minecraft.textureManager,
            minecraft.resourceManager,
            minecraft.mainRenderTarget,
            id("shaders/post/dreaming.json")
        )
        dreamingEffect?.resize(minecraft.window.width, minecraft.window.height)

        val celshade = PostChain(
            minecraft.textureManager,
            minecraft.resourceManager,
            minecraft.mainRenderTarget,
            id("shaders/post/cel.json")
        )
        celshade.resize(minecraft.window.width, minecraft.window.height)
        celshadeEffect = celshade
        celshadeTarget = celshade.getTempTarget("input")

        if (isShaderPackInUse) {
            if (shaderBypassTarget == null) shaderBypassTarget = TextureTarget(
                minecraft.window.width, minecraft.window.height, true, Minecraft.ON_OSX
            )
        } else {
            shaderBypassTarget?.destroyBuffers()
            shaderBypassTarget = null
        }

        val buffers = (minecraft.levelRenderer as LevelRendererAccessor).renderBuffers
        celshadeSource = OutlineBufferSource(buffers.bufferSource())
    }

    @Subscription
    fun afterEntities(event: LevelRenderEvent.AfterEntities) {
        shaderBypassTarget?.clear(Minecraft.ON_OSX)
        shaderBypassTarget?.copyDepthFrom(McClient.mainRenderTarget)
        McClient.mainRenderTarget.bindWrite(false)
    }

    @Subscription
    fun afterParticles(event: LevelRenderEvent.AfterParticles) {
        if (celshadeCounter > 0 && !ShadersModHelper.isRenderingShadowPass()) {
            celshadeTarget.clear(Minecraft.ON_OSX)
            celshadeTarget.copyDepthFrom(McClient.mainRenderTarget)
            RenderSystem.depthFunc(GL11.GL_LESS)
            celshadeSource.endOutlineBatch()
            celshadeEffect?.process(event.partialTick)
            RenderSystem.depthFunc(GL11.GL_LEQUAL)
            McClient.mainRenderTarget.bindWrite(false)
        }
    }

    @Subscription
    fun onEndRender(event: LevelRenderEvent.End) {
        val window = Minecraft.getInstance().window

        RenderSystem.enableBlend()
        RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ZERO,
            GlStateManager.DestFactor.ONE
        )
        if(celshadeCounter > 0) celshadeTarget.blitToScreen(window.width, window.height, false)
        if (isShaderPackInUse) shaderBypassTarget?.blitWithDepth(window.width, window.height)
        RenderSystem.disableBlend()
        RenderSystem.defaultBlendFunc()

        if (Minecraft.getInstance().player?.hasEffect(EstrogenEffects.Dreaming) == true) {
            dreamingEffect?.process(event.partialTick)
        }
        McClient.mainRenderTarget.bindWrite(false)
        celshadeCounter = 0
    }

    @Subscription
    fun onResizeRenderer(event: ResizeRendererEvent) {
        dreamingEffect?.resize(event.width, event.height)
        celshadeEffect?.apply {
            resize(event.width, event.height)
            celshadeTarget.resize(event.width, event.height, Minecraft.ON_OSX)
        }
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

    fun beginShaderpackBypass() {
        if (isShaderPackInUse) shaderBypassTarget?.bindWrite(Minecraft.ON_OSX)
    }

    fun endShaderpackBypass() {
        if (isShaderPackInUse) Minecraft.getInstance().mainRenderTarget.bindWrite(false)
    }

    private class OutlineBufferSource(
        private val bufferSource: MultiBufferSource
    ) : MultiBufferSource {

        private val outlineBuffers: MutableMap<RenderType, BufferBuilder> = Object2ObjectOpenHashMap()

        override fun getBuffer(renderType: RenderType): VertexConsumer {
            if (ShadersModHelper.isRenderingShadowPass()) return bufferSource.getBuffer(renderType)

            celshadeCounter++
            val outlineBuffer = outlineBuffers.getOrPut(renderType) { BufferBuilder(renderType.bufferSize()) }
            if (!outlineBuffer.building()) outlineBuffer.begin(renderType.mode(), renderType.format())

            return VertexMultiConsumer.create(bufferSource.getBuffer(renderType), outlineBuffer)
        }

        fun endOutlineBatch() {
            outlineBuffers.forEach { (renderType, buffer) ->
                if (!buffer.building()) return@forEach
                renderType.setupRenderState()
                celshadeTarget.bindWrite(Minecraft.ON_OSX)
                BufferUploader.drawWithShader(buffer.end())
                renderType.clearRenderState()
            }
            Minecraft.getInstance().mainRenderTarget.bindWrite(false)

        }

    }
}