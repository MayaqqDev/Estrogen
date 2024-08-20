package dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.texture;

import com.mojang.blaze3d.platform.NativeImage;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import java.util.ArrayList;
import java.util.List;

public class DynamicDreamTexture {

    public static final DynamicDreamTexture INSTANCE = new DynamicDreamTexture();
    private static int activeCount = 0;

    private final List<Goober> goobers = new ArrayList<>(); //:
    private final DynamicTexture texture;
    private final ResourceLocation texID;
    private final RenderType renderType;
    private int animationTick = 0;

    public DynamicDreamTexture() {
        texture = new DynamicTexture(128, 128, false);
        texID = Minecraft.getInstance().getTextureManager().register("dreamy", texture);
        renderType = EstrogenRenderTypes.DREAM_BLOCK.apply(texID);
        generateGoobers(8008135L); // TODO: world-based seed
    }

    public RenderType getRenderType() {
        return renderType;
    }

    public void generateGoobers(long seed) {
        RandomSource random = RandomSource.create(seed);

        if(!goobers.isEmpty()) goobers.clear();

        int count = random.nextIntBetweenInclusive(50, 60);
        int attempts = 16;

        while (count > 0) {
            boolean canPlace = true;

            int posX = random.nextInt(4, 124);
            int posY = random.nextInt(4, 124);

            Goober.Style style = Goober.Style.weighted(random);

            if(style != Goober.Style.PIXEL) {
                for (Goober goob : goobers) {
                    if (goob.tooClose(posX, posY)) {
                        canPlace = false;
                        break;
                    }
                }
            }

            if(canPlace) {
                Goober.Color color = Goober.Color.values()[random.nextIntBetweenInclusive(0, 5)];
                int animTick = style.hasAnimation() ? random.nextIntBetweenInclusive(0, 10) : 0;
                int beginFrame = random.nextInt(0, style.frameCount());

                Goober goober = new Goober(posX, posY, color, style, animTick, beginFrame);
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
        Estrogen.LOGGER.info(String.valueOf(activeCount));
    }

    public void tick() {
        if(activeCount == 0) return;
        animationTick++;
        if(animationTick == 10) {
            animationTick = 0;
        }
        for(Goober goober : goobers) {
            goober.tickAnimation(animationTick, this::draw);
        }
    }

    public static void tickInstance() {
        if(INSTANCE != null) INSTANCE.tick();
    }

    public static void addActive() {
        activeCount++;
    }

    public static void removeActive() {
        if(activeCount > 0) {
            activeCount--;
        }
    }

    public static void clearActive() {
        activeCount = 0;
    }

}
