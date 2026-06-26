package dev.mayaqq.estrogen.client.content.blockRenderers.dreamBlock.texture

import com.mojang.blaze3d.platform.NativeImage
import invoke.kitty.kritter.utils.color.Color
import invoke.kitty.kritter.utils.color.Yellow
import invoke.kitty.kritter.utils.color.rgb
import invoke.kitty.kritter.utils.color.toColor
import net.minecraft.util.FastColor
import net.minecraft.util.RandomSource
import net.minecraft.util.random.SimpleWeightedRandomList
import net.minecraft.util.random.Weight
import net.minecraft.util.random.WeightedEntry
import net.minecraft.util.random.WeightedRandomList
import org.joml.Math

class Goober(
    private val x: Int,
    private val y: Int,
    private val color: Colors,
    private val style: Style,
    private val frameTick: Int,
    private var currentFrame: Int,
    private val transparencyLevel: Int
) {
    fun tooClose(x: Int, y: Int): Boolean {
        val difX = Math.abs(this.x - x)
        val difY = Math.abs(this.y - y)
        if (style == Style.PIXEL) return (difX < 2 && difY < 2)
        return (difX < 8 && difY < 8 || difX < 6 || difY < 6)
    }

    fun draw(pixels: NativeImage) {
        var col = color.color
        when (transparencyLevel) {
            1 -> {
                col *= 0xFEFFFFFFu.toColor()
                col *= 0xFEFFFFFFu.toColor()
                col *= 0xFEFFFFFFu.toColor()
                col *= 0xFEFFFFFFu.toColor()
                col *= 0xFEFFFFFFu.toColor()
            }

            2 -> {
                col *= 0xFEFFFFFFu.toColor()
                col *= 0xFEFFFFFFu.toColor()
                col *= 0xFEFFFFFFu.toColor()
            }
        }
        style.draw(pixels, x, y, col, currentFrame)
    }

    fun tickAnimation(tick: Int): Boolean {
        if (tick == frameTick) {
            currentFrame++
            if (currentFrame == style.frameCount()) {
                currentFrame = 0
            }
            return true
        }
        return false
    }

    enum class Colors(val color: Color) {
        YELLOW(Yellow),
        CYAN(rgb(0, 241, 254)),
        PURPLE(rgb(126, 126, 218)),
        MAGENTA(rgb(255, 71, 231)),
        GREEN1(rgb(60, 145, 97)),
        GREEN2(rgb(40, 198, 53));
    }

    enum class Style(weight: Int, val frames: List<DrawFunction>) : WeightedEntry {
        PIXEL(
            2,
            listOf(
                DrawFunction { obj: NativeImage, x: Int, y: Int, abgrColor: Int -> obj.setPixelRGBA(x, y, abgrColor) })
        ),  // Pixel has a lower weight because it has much more placement possibilities
        STAR(
            5,
            listOf(
                DrawFunction { image: NativeImage, x: Int, y: Int, color: Int ->
                    image.setPixelRGBA(x + 1, y, color)
                    image.setPixelRGBA(x, y + 1, color)
                    image.setPixelRGBA(x - 1, y, color)
                    image.setPixelRGBA(x, y - 1, color)
                }
            )),
        THINGY(
            2,
            listOf(
                DrawFunction { pixels: NativeImage, x: Int, y: Int, col: Int ->
                    pixels.setPixelRGBA(x + 1, y, col)
                    pixels.setPixelRGBA(x, y + 1, col)
                    pixels.setPixelRGBA(x - 1, y, col)
                    pixels.setPixelRGBA(x, y - 1, col)

                    val transCol = FastColor.ARGB32.multiply(col, -0x1666667)
                    pixels.setPixelRGBA(x + 1, y + 1, transCol)
                    pixels.setPixelRGBA(x - 1, y - 1, transCol)
                }
            )),
        STAR_ANIMATED(
            3,
            listOf(
                DrawFunction { image: NativeImage, x: Int, y: Int, color: Int ->
                    image.setPixelRGBA(x + 1, y, color)
                    image.setPixelRGBA(x, y + 1, color)
                    image.setPixelRGBA(x - 1, y, color)
                    image.setPixelRGBA(x, y - 1, color)
                },
                DrawFunction { image: NativeImage, x: Int, y: Int, color: Int ->
                    image.setPixelRGBA(x + 1, y, color)
                    image.setPixelRGBA(x, y + 1, color)
                    image.setPixelRGBA(x - 1, y, color)
                    image.setPixelRGBA(x, y - 1, color)
                    image.setPixelRGBA(x + 2, y, color)
                    image.setPixelRGBA(x - 2, y, color)
                    image.setPixelRGBA(x, y + 2, color)
                    image.setPixelRGBA(x, y - 2, color)

                    val transCol = FastColor.ARGB32.multiply(color, -0x1666667)
                    image.setPixelRGBA(x + 1, y + 1, transCol)
                    image.setPixelRGBA(x - 1, y - 1, transCol)
                    image.setPixelRGBA(x - 1, y + 1, transCol)
                    image.setPixelRGBA(x + 1, y - 1, transCol)
                }
            ));

        @JvmField
        val weight: Weight = Weight.of(weight)

        fun draw(image: NativeImage, x: Int, y: Int, color: Color, frame: Int) {
            // Format: ARGB
            frames[frame].draw(image, x, y, color.toInt())
        }

        fun frameCount(): Int {
            return frames.size
        }

        fun hasAnimation(): Boolean {
            return frames.size > 1
        }

        override fun getWeight(): Weight {
            return this.weight
        }

        fun interface DrawFunction {
            fun draw(image: NativeImage, x: Int, y: Int, color: Int)
        }

        companion object {
            private val weightedRandomList: WeightedRandomList<Style> = WeightedRandomList.create(*entries.toTypedArray())

            fun weighted(rng: RandomSource): Style {
                return weightedRandomList.getRandom(rng).get()
            }
        }
    }

    companion object {
        val TRANSPARENCY: SimpleWeightedRandomList<Int> =
            SimpleWeightedRandomList.Builder<Int>().add(0, 5).add(1, 2).add(2, 1).build()
    }
}