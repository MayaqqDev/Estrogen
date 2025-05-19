package dev.mayaqq.estrogen.client.content.blockRenderers.dreamBlock.texture
// TODO: This cool shit
//import com.mojang.blaze3d.pipeline.RenderTarget
//import com.mojang.blaze3d.pipeline.TextureTarget
//import com.mojang.blaze3d.platform.GlStateManager
//import com.mojang.blaze3d.platform.NativeImage
//import com.mojang.blaze3d.systems.RenderSystem
//import com.mojang.blaze3d.vertex.DefaultVertexFormat
//import com.mojang.blaze3d.vertex.Tesselator
//import com.mojang.blaze3d.vertex.VertexFormat
//import com.mojang.serialization.JsonOps
//import dev.mayaqq.cynosure.Cynosure
//import dev.mayaqq.cynosure.client.events.ClientTickEvent
//import dev.mayaqq.cynosure.client.events.render.EndHudRenderEvent
//import dev.mayaqq.cynosure.client.events.render.LevelRenderEvent
//import dev.mayaqq.cynosure.client.events.render.ReloadLevelRendererEvent
//import dev.mayaqq.cynosure.client.utils.color
//import dev.mayaqq.cynosure.events.api.EventSubscriber
//import dev.mayaqq.cynosure.events.api.Subscription
//import dev.mayaqq.cynosure.core.Environment
//import dev.mayaqq.cynosure.utils.colors.Color
//import dev.mayaqq.cynosure.utils.json.toGson
//import dev.mayaqq.estrogen.MOD_ID
//import dev.mayaqq.estrogen.recipeId
//import it.unimi.dsi.fastutil.ints.IntArrayList
//import it.unimi.dsi.fastutil.ints.IntList
//import kotlinx.serialization.ExperimentalSerializationApi
//import kotlinx.serialization.json.*
//import net.minecraft.client.Minecraft
//import net.minecraft.resources.ResourceLocation
//import net.minecraft.server.packs.resources.ResourceManager
//import net.minecraft.server.packs.resources.ResourceManagerReloadListener
//import kotlin.jvm.optionals.getOrNull
//
//@EventSubscriber(env = [Environment.CLIENT])
//object DreamTextureGenerator : ResourceManagerReloadListener {
//
//    private lateinit var framebuffer: RenderTarget
//    val textureId: Int
//        get() = framebuffer.colorTextureId
//
//    private lateinit var gooberTextures: List<Goober.Texture>
//    private lateinit var gooberColors: List<Color>
//
//    private val goobers: MutableList<Goober> = mutableListOf()
//    private var ticks: Int = 0
//    private var shouldRedraw: Boolean = false
//
//    @OptIn(ExperimentalSerializationApi::class)
//    override fun onResourceManagerReload(p0: ResourceManager) {
//        gooberTextures = p0.listResources("goobers") { it.namespace == MOD_ID && it.path.endsWith(".png") }.map { (location, resource) ->
//            val image = resource.open().use { NativeImage.read(it) }
//            val animation = p0.getResource(ResourceLocation(location.namespace, location.path + ".animation.json")).getOrNull()
//            val frames: IntList; val frametime: Int; val frameHeight: Int;
//            if (animation != null) {
//                val json = animation.open().use { Json.decodeFromStream<JsonObject>(it) }
//                frametime = json["frametime"]?.jsonPrimitive?.int ?: 1
//                frames = json["frames"]?.jsonArray?.mapTo(IntArrayList()) { it.jsonPrimitive.int } ?: IntArrayList.of(0)
//                frameHeight = json["frameheight"]?.jsonPrimitive?.int ?: (image.height / frames.size)
//            } else {
//                frames = IntArrayList.of()
//                frametime = 1
//                frameHeight = image.height
//            }
//            return@map Goober.Texture(location, image.width, image.height, frametime, frameHeight, frames)
//        }
//
//        val set: MutableList<Color> = mutableListOf()
//        p0.getResourceStack(recipeId("goober_colors.json")).forEach { resource ->
//            val json = resource.open().use { Json.decodeFromStream<JsonObject>(it) }
//            val replace = json["replace"]?.jsonPrimitive?.booleanOrNull ?: false
//            if (replace) set.clear()
//
//            val values = json.getValue("values").jsonArray
//            for (value in values) {
//                Color.CODEC.parse(JsonOps.INSTANCE, value.toGson())
//                    .resultOrPartial(Cynosure::error)
//                    .getOrNull()
//                    ?.let(set::add)
//            }
//        }
//
//        gooberColors = set
//    }
//
//    internal fun draw() {
//        if (!::framebuffer.isInitialized) framebuffer = TextureTarget(256, 256, false, false)
//        framebuffer.clear(false)
//        framebuffer.bindWrite(false)
//        RenderSystem.enableBlend()
//        RenderSystem.blendFuncSeparate(
//            GlStateManager.SourceFactor.SRC_ALPHA,
//            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
//            GlStateManager.SourceFactor.ZERO,
//            GlStateManager.DestFactor.ONE
//        )
//        RenderSystem.depthMask(false)
//        RenderSystem.disableDepthTest()
//        RenderSystem.setShader(Minecraft.getInstance().gameRenderer::blitShader)
//
//
//        val buffer = Tesselator.getInstance().builder
//
//        for (goober in goobers) {
//            RenderSystem.setShaderTexture(0, goober.texture.location)
//
//            val texOffs = goober.texture.getFrameOffset(ticks)
//            val minV = texOffs / goober.texture.height.toFloat()
//            val maxV = (texOffs + goober.texture.frameHeight) / goober.texture.height.toFloat()
//            val x = goober.x / 256.0
//            val y = goober.y / 256.0
//            val width = goober.texture.width / 256.0
//            val height = goober.texture.frameHeight / 256.0
//
//            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX)
//            buffer.vertex(x, y, 1.0).color(goober.color).uv(0f, minV)
//            buffer.vertex(x + width, y, 1.0).color(goober.color).uv(1f, minV)
//            buffer.vertex(x + width, y + height, 1.0).color(goober.color).uv(1f, maxV)
//            buffer.vertex(x, y + height, 1.0).color(goober.color).uv(0f, maxV)
//
//            Tesselator.getInstance().end()
//        }
//
//        RenderSystem.disableBlend()
//        RenderSystem.defaultBlendFunc()
//        RenderSystem.depthMask(true)
//        RenderSystem.enableDepthTest()
//        Minecraft.getInstance().mainRenderTarget.bindWrite(false)
//        //shouldRedraw = false
//    }
//
//    @Subscription
//    internal fun tick(event: ClientTickEvent.End) {
//        ticks++
//        // = true
//        //draw()
//        //RenderSystem.recordRenderCall(::draw)
//            //draw()
//    }
//
//    @Subscription
//    internal fun afterHudRender(event: LevelRenderEvent.Start) {
//        //if (shouldRedraw) draw()
//    }
//}