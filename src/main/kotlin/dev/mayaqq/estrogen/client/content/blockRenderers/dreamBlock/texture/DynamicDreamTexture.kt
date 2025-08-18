package dev.mayaqq.estrogen.client.content.blockRenderers.dreamBlock.texture

import com.mojang.blaze3d.systems.RenderSystem
import dev.mayaqq.cynosure.client.events.ClientTickEvent
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.estrogen.config.EstrogenClientConfig.DreamBlock.animateTexture
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.DynamicTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.RandomSource
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.jvm.optionals.getOrNull

@EventSubscriber
internal object DynamicDreamTexture {

    val ID: ResourceLocation = ResourceLocation("dream_texture")

    private val goobers: MutableList<Goober> = ObjectArrayList() //:
    private lateinit var texture: DynamicTexture
    private var seed: Long = 0xB00B5
    private var animationTick = 0
    var init = false

    fun prepare() {
        if (init) return
        texture = DynamicTexture(128, 128, false)
        Minecraft.getInstance().textureManager.register(ID, texture)
        this.draw()
        init = true
    }

    fun changeSeed(seed: Long) {
        this.seed = seed
        this.generateGoobers()
        this.redraw()
    }

    private fun generateGoobers() {
        val random = RandomSource.create(seed)
        goobers.clear()

        var count = random.nextIntBetweenInclusive(50, 60)
        var attempts = 16

        while (count > 0) {
            var canPlace = true

            val posX = random.nextInt(4, 124)
            val posY = random.nextInt(4, 124)

            for (goob in goobers) {
                if (goob.tooClose(posX, posY)) {
                    canPlace = false
                    break
                }
            }

            if (canPlace) {
                val style = Goober.Style.weighted(random)
                val color: Goober.Colors = Goober.Colors.entries[random.nextIntBetweenInclusive(0, 5)]
                val animTick = if (style.hasAnimation()) random.nextIntBetweenInclusive(0, 10) else 0
                val beginFrame = random.nextInt(0, style.frameCount())
                val transparency: Int = Goober.TRANSPARENCY.getRandom(random).getOrNull()?.data ?: 0

                val goober = Goober(posX, posY, color, style, animTick, beginFrame, transparency)
                goobers.add(goober)
                count--
            } else {
                attempts--
                if (attempts == 0) {
                    count--
                    attempts = 16
                }
            }
        }
    }

    private fun draw() {
        val pixels = texture.pixels ?: return

        pixels.applyToAllPixels { i: Int -> -0x1000000 }

        for (goober in goobers) {
            goober.draw(pixels)
        }

        texture.upload()
    }

    @Subscription
    fun tick(event: ClientTickEvent.End) {
        animationTick++
        if (animationTick == 10) {
            animationTick = 0
        }
        var redraw = false
        for (goober in goobers) {
            if (goober.tickAnimation(animationTick)) redraw = true
        }
        if (shouldAnimate() && redraw) redraw()
    }

    private fun redraw() {
        if (!init) return
        if (RenderSystem.isOnRenderThread()) {
            this.draw()
        } else {
            RenderSystem.recordRenderCall { this.draw() }
        }
    }

    private fun shouldAnimate(): Boolean {
        return animateTexture
    }

    private val shouldAnimate = AtomicBoolean()

    fun setActive() {
        shouldAnimate.set(true)
    }

    fun resetActive() {
        shouldAnimate.set(false)
    }
}
