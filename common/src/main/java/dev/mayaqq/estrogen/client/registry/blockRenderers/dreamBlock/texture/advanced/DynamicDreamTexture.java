package dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.texture.advanced;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderType;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.DreamBlockRenderer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedEntry;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class DynamicDreamTexture {

    public static final DynamicDreamTexture INSTANCE = new DynamicDreamTexture();
    public static boolean enableAnimation = true;

    private static final AtomicBoolean shouldAnimate = new AtomicBoolean();

    private final List<Goober> goobers = new ObjectArrayList<>(); //:
    private DynamicTexture texture;
    private ResourceLocation texID;
    private RenderType renderType;
    private long seed = 80085L;
    private int animationTick = 0;
    private boolean init = false;

    public void prepare() {
        if(init) return;
        texture = new DynamicTexture(128, 128, false);
        texID = Minecraft.getInstance().getTextureManager().register("dreamy", texture);
        renderType = EstrogenRenderType.DREAM_BLOCK.apply(texID);
        this.draw();
        init = true;
    }

    // I'll leave this here unused cs it might be useful in the future
    private void _release() {
        if(!init) return;
        Minecraft.getInstance().getTextureManager().release(texID);
        texture = null;
        texID = null;
        renderType = null;
        init = false;
    }

    public void release() {
        if(RenderSystem.isOnRenderThread()) {
            this._release();
        } else {
            RenderSystem.recordRenderCall(this::_release);
        }
    }

    public RenderType getRenderType() {
        return renderType;
    }

    public void changeSeed(long seed) {
        this.seed = seed;
        this.generateGoobers();
        this.redraw();
    }

    public void generateGoobers() {
        RandomSource random = RandomSource.create(seed);

        if(!goobers.isEmpty()) goobers.clear();

        int count = random.nextIntBetweenInclusive(50, 60);
        int attempts = 16;

        while (count > 0) {
            boolean canPlace = true;

            int posX = random.nextInt(4, 124);
            int posY = random.nextInt(4, 124);

            for (Goober goob : goobers) {
                if (goob.tooClose(posX, posY)) {
                    canPlace = false;
                    break;
                }
            }

            if(canPlace) {
                Goober.Style style = Goober.Style.weighted(random);
                Goober.Color color = Goober.Color.values()[random.nextIntBetweenInclusive(0, 5)];
                int animTick = style.hasAnimation() ? random.nextIntBetweenInclusive(0, 10) : 0;
                int beginFrame = random.nextInt(0, style.frameCount());
                int transparency = Goober.TRANSPARENCY.getRandom(random)
                    .map(WeightedEntry.Wrapper::getData)
                    .orElse(0);

                Goober goober = new Goober(posX, posY, color, style, animTick, beginFrame, transparency);
                goobers.add(goober);
                count--;
            } else {
                attempts--;
                if(attempts == 0) {
                    count--;
                    attempts = 16;
                }
            }
        }
    }

    protected void draw() {
        NativeImage pixels = texture.getPixels();

        pixels.applyToAllPixels(i -> 0xff000000);

        for(Goober goober : goobers) {
            goober.draw(pixels);
        }

        texture.upload();
    }

    public void tick() {
        animationTick++;
        if(animationTick == 10) {
            animationTick = 0;
        }
        boolean redraw = false;
        for (Goober goober : goobers) {
            if(goober.tickAnimation(animationTick)) redraw = true;
        }
        if(shouldAnimate() && redraw) redraw();
    }

    public void redraw() {
        if(!init) return;
        if(RenderSystem.isOnRenderThread()) {
            this.draw();
        } else {
            RenderSystem.recordRenderCall(this::draw);
        }
    }

    private boolean shouldAnimate() {
        return enableAnimation && DreamBlockRenderer.useAdvancedRenderer() && shouldAnimate.get();
    }

    public static void setActive() {
        shouldAnimate.set(true);
    }

    public static void resetActive() {
        shouldAnimate.set(false);
    }

}
