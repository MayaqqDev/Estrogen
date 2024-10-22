package dev.mayaqq.estrogen.client.cosmetics.model.animation;

import dev.mayaqq.estrogen.utils.Registry;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

public final class Animations {

    public static final Registry<Keyframe.Interpolation> INTERPOLATIONS = new Registry<>();
    public static final Registry<VectorType> VECTOR_TYPES = new Registry<>();

    private static int animationTicks = 0;

    public static void animate(Animatable.Provider source, AnimationDefinition definition, Vector3f animationVecCache) {
        long accumulatedTime = getAnimationTime();

        definition.animations().forEach((key, animations) ->
            source.getAny(key).ifPresent(animatable -> animations.forEach(animation -> {
                Keyframe[] keyframes = animation.keyframes();
                float elapsed = getElapsedSeconds(animation, accumulatedTime);
                int last = Math.max(0, Mth.binarySearch(0, keyframes.length, index -> elapsed <= keyframes[index].timestamp()) - 1);
                int next = Math.min(keyframes.length - 1, last + 1);

                Keyframe lastFrame = keyframes[last];
                Keyframe nextFrame = keyframes[next];
                float sinceLast = elapsed - lastFrame.timestamp();
                float delta = (next != last)
                    ? Mth.clamp(sinceLast / (nextFrame.timestamp() - lastFrame.timestamp()), 0.0F, 1.0F)
                    : 0.0f;

                nextFrame.interpolation().apply(animationVecCache, delta, keyframes, last, next, 1f);
                animation.target().apply(animatable, animationVecCache);
            }))
        );
    }

    private static float getElapsedSeconds(Animation animation, long accumulatedTime) {
        double f = accumulatedTime / 1000.0f;
        return (float) f % animation.seconds();
    }

    public static void tick() {
        if(animationTicks == 30000000) animationTicks = 0;
        animationTicks++;
    }

    public static long getAnimationTime() {
        return (long) (Mth.lerp(Minecraft.getInstance().getFrameTime(), animationTicks, animationTicks + 1) * 50L);
    }
}
