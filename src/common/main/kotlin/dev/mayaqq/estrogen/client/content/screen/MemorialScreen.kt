package dev.mayaqq.estrogen.client.content.screen

import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.text.Text
import dev.mayaqq.cynosure.text.TextStyle.color
import dev.mayaqq.estrogen.client.content.screen.EstrogenMenuScreen.Companion.transBlue
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.FittingMultiLineTextWidget
import net.minecraft.client.gui.screens.Screen

class MemorialScreen(previous: Screen? = null) : BaseEstrogenScreen(previous, Text.translatable("estrogen.button.memorial")) {

    val bClose = EstrogenButton.Builder(EstrogenButton.TextRenderer(Text.translatable("estrogen.button.close"))) {
        this.onClose()
    }

    override fun baseInit() {
        object : FittingMultiLineTextWidget(
            10,
            38,
            this.width - 20,
            this.height - 78,
            Text.of(text),
            font
        ) {
            override fun renderBackground(graphics: GuiGraphics) {}
        }.add()
        bClose.bounds(this.width / 2 - 100, this.height - 30, 200, 20).color(transBlue).buildAndAdd()
    }

    override fun beforeRender(
        graphics: GuiGraphics,
        mouseX: Int,
        mouseY: Int,
        partialTick: Float
    ) {
        val text = Text.of("Dedicated to Azzypaaras") { color = transBlue }
        graphics.drawString(
            font,
            text,
            this.width / 2 - font.width(text) / 2,
            19 - font.lineHeight / 2,
            0xFFFFFF,
            false
        )

        /*
        graphics.pushPop {
            val lines = Companion.text.split("\n")
            val width = McFont.width(lines.maxBy { McFont.width(it) })

            graphics.enableScissor(0, 0, this@MemorialScreen.width, this@MemorialScreen.height - 35)

            translate(this@MemorialScreen.width / 2 - width / 2, 40 - scrollProgress, 0)

            val memorial = Text.of {
                lines.forEach { line ->
                    McFont.splitter.splitLines(line, this@MemorialScreen.width, Style.EMPTY).forEach {
                        append(CommonText.NEWLINE)
                        append(it.string)
                    }
                }
            }

            memorial.splitLines().forEachIndexed { index, line ->
                graphics.drawString(
                    font,
                    line,
                    0,
                    index * McFont.lineHeight,
                    0xFFFFFF,
                    false
                )
            }
            graphics.disableScissor()
        }
         */
    }

    override fun isPauseScreen(): Boolean = true

    override fun renderBackground(gui: GuiGraphics) {
        if (McClient.level == null) {
            super.renderBackground(gui)
        } else {
            gui.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680)
        }
    }

    companion object {
        val text = """
            Rest In Peace Azzypaaras 2002 - 2025
            
            An incredibly brilliant artist, an amazing, thoughtful game designer and writer, a great but cursed programmer but to me most importantly, one of my closest friends.
            
            She taught me about self confidence, about who I am, who I want to become and so much about life. I am forever grateful to her, I wish I could repay her more.
            
            She could write this text much better than I could, maybe make it a bit more snarky and use levels of english that Shakespeare would be shivering in his grave (Positive).
            
            She is the one who inspired me and many others to be more, to aspire to be something higher. She is the reason I got into drawing and pixel-art, she is also the one who propelled the idea of making this mod.
            
            I love you Azzy, I miss you so bad. I guess you will never make me that fursona in the end, will you?
            
            "There is a point and a place for noting the struggle, and also one for more harsh critique. But ultimately we are here with the goal of creating something beautiful" - Azzypaaras
            
            This Memorial is dedicated to her, but also to anyone who did not survive through the tough times we get put through, and for everyone to serve as a reminder to stay strong, we are in this together, we can do this.
        """.trimIndent()
    }
}