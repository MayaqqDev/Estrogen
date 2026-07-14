package dev.mayaqq.estrogen.client.content

import com.google.common.collect.MapMaker
import com.mojang.blaze3d.pipeline.RenderTarget
import com.mojang.blaze3d.pipeline.TextureTarget
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferBuilder
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexMultiConsumer
import dev.engine_room.flywheel.lib.model.baked.PartialModel
import dev.engine_room.flywheel.lib.util.ShadersModHelper
import dev.mayaqq.cynosure.client.events.ClientTickEvent
import dev.mayaqq.cynosure.client.events.CoreShaderRegistrationEvent
import dev.mayaqq.cynosure.client.events.render.GameRenderEvent
import dev.mayaqq.cynosure.client.events.render.ReloadLevelRendererEvent
import dev.mayaqq.cynosure.client.events.render.ResizeRendererEvent
import dev.mayaqq.cynosure.client.isShaderPackInUse
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.id
import dev.mayaqq.estrogen.utils.holder
import dev.mayaqq.estrogen.utils.render.blitWithDepth
import invoke.kitty.kritter.client.events.render.LevelRenderContext
import invoke.kitty.kritter.client.events.render.LevelRenderEvent
import invoke.kitty.kritter.client.events.render.ReloadLevelRendererEvent
import invoke.kitty.kritter.client.events.render.ResizeLevelRendererEvent
import invoke.kitty.kritter.platform.Side
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.PostChain
import net.minecraft.client.renderer.RenderStateShard.OutputStateShard
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.ShaderInstance
import org.lwjgl.opengl.GL11

@EventSubscriber(Side.CLIENT)
object EstrogenRenderer {

    // Render state shards
    val SHADER_BYPASS: OutputStateShard = OutputStateShard(
        "shader_bypass",
        EstrogenRenderer::beginShaderpackBypass,
        EstrogenRenderer::endShaderpackBypass
    )

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

    lateinit var cosmeticOutlineShader: ShaderInstance
        private set

    // Post chains and framebuffers
    private var dreamingEffect: PostChain? = null

    private var celshadeEffect: PostChain? = null

    private lateinit var celshadeTarget: RenderTarget

    private var shaderBypassTarget: RenderTarget? = null

    // Misc
    internal var celshadeCounter: Int = 0

    fun initialize() {
        LevelRenderEvent.AfterEntities calls ::afterEntities
        LevelRenderEvent.AfterParticles calls ::afterParticles
        LevelRenderEvent.End calls ::onEndRender
        ReloadLevelRendererEvent calls ::onReloadRenderer
        ResizeLevelRendererEvent calls ::onResizeRenderer
    }

    fun getCelShaded(bufferSource: MultiBufferSource): MultiBufferSource =
        OutlineBufferSource.getCelShadeWrapper(bufferSource)

    @Subscription
    internal fun onLoadShaders(event: CoreShaderRegistrationEvent) {
        event.register(id("rendertype_estrogen_dream"), DefaultVertexFormat.BLOCK, ::dreamBlockShader)
        event.register(id("dreamblock_overlay"), DefaultVertexFormat.POSITION_COLOR, ::dreamBlockOverlayShader)
        event.register(id("blit_with_depth"), DefaultVertexFormat.BLIT_SCREEN, ::blitWithDepthShader)
        event.register(id("rendertype_entity_cutout_no_diffuse"), DefaultVertexFormat.NEW_ENTITY, ::renderTypeEntityCutoutNoDiffuseShader)
        event.register(id("rendertype_entity_translucent_no_diffuse"), DefaultVertexFormat.NEW_ENTITY, ::renderTypeEntityTranslucentNoDiffuseShader)
        event.register(id("dash_trail_particle"), DefaultVertexFormat.PARTICLE, ::dashTrailParticleShader)
        event.register(id("cosmetic_outline"), DefaultVertexFormat.NEW_ENTITY, ::cosmeticOutlineShader)
    }

    internal fun onReloadRenderer() {
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

        initCelShade()

        if (isShaderPackInUse) {
            if (shaderBypassTarget == null) shaderBypassTarget = TextureTarget(
                minecraft.window.width, minecraft.window.height, true, Minecraft.ON_OSX
            )
        } else {
            shaderBypassTarget?.destroyBuffers()
            shaderBypassTarget = null
        }
    }

    private fun initCelShade() {
        val celshade = PostChain(
            McClient.textureManager,
            McClient.resourceManager,
            McClient.mainRenderTarget,
            id("shaders/post/cel.json")
        )
        celshade.resize(McClient.window.width, McClient.window.height)
        celshadeEffect = celshade
        celshadeTarget = celshade.getTempTarget("input")
    }

    internal fun afterEntities(context: LevelRenderContext) {
        shaderBypassTarget?.clear(Minecraft.ON_OSX)
        shaderBypassTarget?.copyDepthFrom(McClient.mainRenderTarget)
        McClient.mainRenderTarget.bindWrite(false)
    }

    internal fun afterParticles(context: LevelRenderContext) {
        if (celshadeCounter > 0 && !ShadersModHelper.isRenderingShadowPass()) {
            celshadeTarget.clear(Minecraft.ON_OSX)
            celshadeTarget.copyDepthFrom(McClient.mainRenderTarget)
            RenderSystem.depthFunc(GL11.GL_LESS)
            OutlineBufferSource.endOutlineBatch()
            celshadeEffect?.process(context.partialTick)
            RenderSystem.depthFunc(GL11.GL_LEQUAL)
            McClient.mainRenderTarget.bindWrite(false)
        }
    }

    internal fun onEndRender(context: LevelRenderContext) {
        val window = Minecraft.getInstance().window

        RenderSystem.enableBlend()
        RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ZERO,
            GlStateManager.DestFactor.ONE
        )
        if (celshadeCounter > 0) celshadeTarget.blitToScreen(window.width, window.height, false)
        if (isShaderPackInUse) shaderBypassTarget?.blitWithDepth(window.width, window.height)
        RenderSystem.disableBlend()
        RenderSystem.defaultBlendFunc()

        if (Minecraft.getInstance().player?.hasEffect(EstrogenEffects.Dreaming.holder()) == true) {
            dreamingEffect?.process(context.partialTick)
        }
        McClient.mainRenderTarget.bindWrite(false)
        celshadeCounter = 0
    }

    @Subscription
    internal fun onResizeRenderer(width: Int, height: Int) {
        dreamingEffect?.resize(width, height)
        celshadeEffect?.apply {
            resize(width, height)
            celshadeTarget.resize(width, height, Minecraft.ON_OSX)
        }
        shaderBypassTarget?.resize(width, height, Minecraft.ON_OSX)
    }

    var clientTickCounter = 0

    @Subscription
    internal fun onGameRender(event: GameRenderEvent) {
        if (::dreamBlockShader.isInitialized) {
            dreamBlockShader.getUniform("CeaselessGameTime")?.set(((clientTickCounter % 24000L + event.partialTick) / 24000.0F))
        }
    }

    @Subscription
    internal fun tickEvent(event: ClientTickEvent.Begin) {
        clientTickCounter++
    }

    fun drawCelOutlineOutsideLevelRender() {

        if(celshadeEffect == null) initCelShade()

        RenderSystem.disableDepthTest()

        celshadeTarget.clear(Minecraft.ON_OSX)
        celshadeTarget.copyDepthFrom(McClient.mainRenderTarget)

        OutlineBufferSource.endOutlineBatch()
        celshadeEffect?.process(0f)

        McClient.mainRenderTarget.bindWrite(false)
        RenderSystem.enableBlend()
        RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ZERO,
            GlStateManager.DestFactor.ONE
        )
        celshadeTarget.blitToScreen(McClient.window.width, McClient.window.height, false)
        RenderSystem.disableBlend()
    }

    fun beginShaderpackBypass() {
        if (isShaderPackInUse) shaderBypassTarget?.bindWrite(Minecraft.ON_OSX)
    }

    fun endShaderpackBypass() {
        if (isShaderPackInUse) Minecraft.getInstance().mainRenderTarget.bindWrite(false)
    }

    private object OutlineBufferSource {

        //TODO: yeah this..
        private val bufferSources: MutableMap<MultiBufferSource, MultiBufferSource> = MapMaker().weakKeys().makeMap()
        private val outlineBuffers: MutableMap<RenderType, BufferBuilder> = Object2ObjectOpenHashMap()

        fun getCelShadeWrapper(bufferSource: MultiBufferSource): MultiBufferSource {
            bufferSources[bufferSource]?.also { return it }

            return MultiBufferSource { renderType ->
                if (ShadersModHelper.isRenderingShadowPass()) return@MultiBufferSource bufferSource.getBuffer(renderType)

                celshadeCounter++
                val outlineBuffer = outlineBuffers.get(renderType) /*TODO: .getOrPut(renderType) { BufferBuilder(renderType.bufferSize()) } */
                //TODO: if (!outlineBuffer.building()) outlineBuffer.begin(renderType.mode(), renderType.format())
                return@MultiBufferSource VertexMultiConsumer.create(bufferSource.getBuffer(renderType), outlineBuffer)
            }.also { bufferSources[bufferSource] = it }
        }

        fun endOutlineBatch() {
            outlineBuffers.forEach { (renderType, buffer) ->
                //TODO: if (!buffer.building()) return@forEach
                renderType.setupRenderState()
                celshadeTarget.bindWrite(Minecraft.ON_OSX)
                //TODO: BufferUploader.drawWithShader(buffer.end())
                renderType.clearRenderState()
            }
            Minecraft.getInstance().mainRenderTarget.bindWrite(false)
        }

    }
}