package dev.mayaqq.estrogen.utils;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;

/**
 * A Map of 6 textures to be generated dynamically
 */
public class DynamicTextureMap {
    private DynamicTexture mapTex;
    private ResourceLocation mapTexId;
    private final int resolution;
    private boolean released = true;


    public DynamicTextureMap(int resolution) {
        this.resolution = resolution;
    }

    /**
     * Initialize the dynamic textures
     * @param manager the texture manager
     * @param id - id of the texture
     */
    public void init(TextureManager manager, String id) {
        this.released = false;
        this.mapTex = new DynamicTexture(resolution * 3, resolution * 2, false);
        this.mapTexId = manager.register(id, mapTex);


    }

    public void drawAll(Consumer<DrawContext> drawCallback) {
        if(released) return;

        try(DrawContext ctx = new DrawContext(this)) {
            for(Direction dir : Direction.values()) {
                ctx.mapFace(dir);
                drawCallback.accept(ctx);
            }
        }
    }

    public void draw(Direction dir, Consumer<DrawContext> drawCallback) {
        if(released) return;

        try(DrawContext ctx = new DrawContext(this)) {
            ctx.mapFace(dir);
            drawCallback.accept(ctx);
        }
    }

    public ResourceLocation location() {
        return mapTexId;
    }

    public float getU(float u, Direction dir) {
        return switch (dir) {
            case DOWN, UP -> u / 3f;
            case NORTH, SOUTH -> u / 3f + (1f / 3f);
            case WEST, EAST -> u / 3f + (2f / 3f);
        };
    }

    public float getU0(Direction dir) {
        return getU(0, dir);
    }

    public float getU1(Direction dir) {
        return getU(1, dir);
    }

    public float getV(float v, Direction dir) {
        return switch (dir) {
            case UP, NORTH, EAST -> v / 2f;
            case DOWN, SOUTH, WEST -> v / 2f + (1.0f / 2f);
        };
    }

    public float getV0(Direction dir) {
        return getV(0, dir);
    }

    public float getV1(Direction dir) {
        return getV(1, dir);
    }

    public void release() {
        released = true;
        //mapTex.close();
    }

    public static class DrawContext implements AutoCloseable {
        private final DynamicTextureMap map;
        private final NativeImage pixels;
        private Direction face;
        private int offsetX;
        private int offsetY;
        private boolean shouldUpload = true;

        public DrawContext(DynamicTextureMap map) {
            this.map = map;
            this.offsetX = 0;
            this.offsetY = 0;
            this.face = Direction.UP;
            this.pixels = map.mapTex.getPixels();
        }
        public void setPixelColor(int x, int y, int colorABGR) {
            pixels.setPixelRGBA(x + offsetX, y + offsetY, colorABGR);
        }

        public void setPixelColorSafe(int x, int y, int abgr) {
            if(x >= map.resolution || x < 0 || y >= map.resolution || y < 0) return;
            setPixelColor(x, y, abgr);
        }

        public int getPixelColor(int x, int y) {
            return pixels.getPixelRGBA(x, y);
        }

        public void blendPixel(int x, int y, int colorAGBR) {
            pixels.blendPixel(x + offsetX, y + offsetY, colorAGBR);
        }

        public void applyToPixels(IntUnaryOperator operator) {
            for(int y = offsetY; y < map.resolution + offsetY; y++) {
                for(int x = offsetX; x < map.resolution + offsetX; x++) {
                    pixels.setPixelRGBA(x, y, operator.applyAsInt(pixels.getPixelRGBA(x, y)));
                }
            }
        }

        private void mapFace(Direction dir) {
            this.face = dir;
            offsetX = switch (dir) {
                case DOWN, UP -> 0;
                case NORTH, SOUTH -> map.resolution;
                case WEST, EAST -> map.resolution * 2;
            };
            offsetY = switch (dir) {
                case UP, NORTH, EAST -> 0;
                case DOWN, SOUTH, WEST -> map.resolution;
            };
        }

        public DynamicTextureMap getMap() {
            return map;
        }

        public DynamicTexture getMapTex() {
            return map.mapTex;
        }

        public Direction face() {
            return this.face;
        }

        public void cancel() {
            shouldUpload = false;
        }

        @Override
        public void close() {
            if(shouldUpload) map.mapTex.upload();
        }
    }
}
