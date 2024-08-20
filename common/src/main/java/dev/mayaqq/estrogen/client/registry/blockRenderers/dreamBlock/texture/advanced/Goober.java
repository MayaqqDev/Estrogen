package dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.texture.advanced;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import org.joml.Math;

import java.util.List;

public class Goober {

    static final SimpleWeightedRandomList<Integer> TRANSPARENCY = new SimpleWeightedRandomList.Builder<Integer>().add(0, 5).add(1, 2).add(2, 1).build();

    private final int x;
    private final int y;
    private final Color color;
    private final Style style;
    private final int frameTick;
    private final int transparencyLevel;
    private int currentFrame;

    public Goober(int x, int y, Color color, Style style, int frameTick, int beginFrame, int transparencyLevel) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.style = style;
        this.frameTick = frameTick;
        this.currentFrame = beginFrame;
        this.transparencyLevel = transparencyLevel;
    }

    public boolean tooClose(int x, int y) {
        int difX = Math.abs(this.x - x);
        int difY = Math.abs(this.y - y);
        return (difX < 8 && difY < 8 || ((difX < 6 || difY < 6) && style != Style.PIXEL));
    }

    public void draw(NativeImage pixels) {
        int col = color.color;
        switch (transparencyLevel) {
            case 1: {
                col = FastColor.ARGB32.multiply(col, 0xFEFFFFFF);
                col = FastColor.ARGB32.multiply(col, 0xFEFFFFFF);
            }
            case 2: {
                col = FastColor.ARGB32.multiply(col, 0xFEFFFFFF);
                col = FastColor.ARGB32.multiply(col, 0xFEFFFFFF);
                col = FastColor.ARGB32.multiply(col, 0xFEFFFFFF);
            }
        }
        style.draw(pixels, x, y, col, currentFrame);
    }

    public void tickAnimation(int tick, Runnable redraw) {
        if(tick == frameTick) {
            currentFrame++;
            if(currentFrame == style.frameCount()) {
                currentFrame = 0;
            }
            RenderSystem.recordRenderCall(redraw::run);
        }
    }

    public enum Color {

        YELLOW(rgb(255, 255, 0)),
        CYAN(rgb(0, 241, 254)),
        PURPLE(rgb(106, 106, 198)),
        MAGENTA(rgb(255, 71, 231)),
        GREEN1(rgb(40, 125, 77)),
        GREEN2(rgb(40, 198, 53));

        final int color;
        Color(int color) {
            this.color = color;
        }

        public static int rgb(int r, int g, int b) {
            return FastColor.ARGB32.color(255, b, g, r); // abgr moment
        }
    }

    public enum Style implements WeightedEntry {
        PIXEL(2, List.of(NativeImage::setPixelRGBA)), // Pixel has a lower weight because any node drawn on a border becomes a pixel
        STAR(5, List.of((image, x, y, color) -> {
                image.setPixelRGBA(x + 1, y, color);
                image.setPixelRGBA(x, y + 1, color);
                image.setPixelRGBA(x - 1, y, color);
                image.setPixelRGBA(x, y - 1, color);
            }
        )),
        THINGY(2, List.of((pixels, x, y, col) -> {
            pixels.setPixelRGBA(x + 1, y, col);
            pixels.setPixelRGBA(x, y + 1, col);
            pixels.setPixelRGBA(x - 1, y, col);
            pixels.setPixelRGBA(x, y - 1, col);

            int transCol = FastColor.ARGB32.multiply(col, 0xFE999999);
            pixels.setPixelRGBA(x + 1, y + 1, transCol);
            pixels.setPixelRGBA(x - 1, y - 1, transCol);
        }
        )),
        STAR_ANIMATED(3, List.of(
            (image, x, y, color) -> {
                image.setPixelRGBA(x + 1, y, color);
                image.setPixelRGBA(x, y + 1, color);
                image.setPixelRGBA(x - 1, y, color);
                image.setPixelRGBA(x, y - 1, color);
            },
            (image, x, y, color) -> {
                image.setPixelRGBA(x + 1, y, color);
                image.setPixelRGBA(x, y + 1, color);
                image.setPixelRGBA(x - 1, y, color);
                image.setPixelRGBA(x, y - 1, color);
                image.setPixelRGBA(x + 2, y, color);
                image.setPixelRGBA(x - 2, y,  color);
                image.setPixelRGBA(x, y + 2, color);
                image.setPixelRGBA(x, y - 2, color);

                int transCol = FastColor.ARGB32.multiply(color, 0xFE999999);
                image.setPixelRGBA(x + 1, y + 1, transCol);
                image.setPixelRGBA(x - 1, y - 1, transCol);
                image.setPixelRGBA(x - 1, y + 1, transCol);
                image.setPixelRGBA(x + 1, y - 1, transCol);
            }
        ));

        final Weight weight;
        final List<DrawFunction> frames;

        Style(int weight, List<DrawFunction> frames) {
            this.weight = Weight.of(weight);
            this.frames = frames;
        }

        void draw(NativeImage image, int x, int y, int color, int frame) {
            frames.get(frame).draw(image, x, y, color);
        }

        public int frameCount() {
            return frames.size();
        }

        public boolean hasAnimation() {
            return frames.size() > 1;
        }

        @Override
        public Weight getWeight() {
            return this.weight;
        }

        private static final WeightedRandomList<Style> weightedRandomList = WeightedRandomList.create(values());

        public static Style weighted(RandomSource rng) {
            return weightedRandomList.getRandom(rng).get();
        }

        interface DrawFunction {
            public void draw(NativeImage image, int x, int y, int color);
        }
    }
}


