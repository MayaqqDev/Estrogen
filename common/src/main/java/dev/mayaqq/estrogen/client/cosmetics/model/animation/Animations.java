package dev.mayaqq.estrogen.client.cosmetics.model.animation;

import dev.mayaqq.estrogen.client.cosmetics.EstrogenCosmetics;
import dev.mayaqq.estrogen.utils.Registry;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class Animations {

    public static final Registry<Keyframe.Interpolation> INTERPOLATIONS = new Registry<>();
    public static final Registry<VectorType> VECTOR_TYPES = new Registry<>();

    public static void animate(Animatable.Provider source, AnimationDefinition definition, long accumulatedTime, float scale, Vector3f animationVecCache) {
        float elapsed = getElapsedSeconds(definition, accumulatedTime);

        definition.animations().forEach((key, animations) ->
            source.getAny(key).ifPresent(animatable -> animations.forEach(animation -> {
                Keyframe[] keyframes = animation.keyframes();
                int last = Math.max(0, Mth.binarySearch(0, keyframes.length, index -> elapsed <= keyframes[index].timestamp()) - 1);
                int next = Math.min(keyframes.length - 1, last + 1);

                Keyframe lastFrame = keyframes[last];
                Keyframe nextFrame = keyframes[next];
                float sinceLast = elapsed - lastFrame.timestamp();
                float delta = (next != last)
                    ? Mth.clamp(sinceLast / (nextFrame.timestamp() - lastFrame.timestamp()), 0.0F, 1.0F)
                    : 0.0f;

                nextFrame.interpolation().apply(animationVecCache, delta, keyframes, last, next, scale);
                animation.target().apply(animatable, animationVecCache);
            }))
        );
    }

    private static float getElapsedSeconds(AnimationDefinition animationDefinition, long accumulatedTime) {
        double f = accumulatedTime / 1000.0f;
        return (float) f % animationDefinition.seconds();
    }

}
