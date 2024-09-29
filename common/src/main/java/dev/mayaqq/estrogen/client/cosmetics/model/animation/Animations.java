package dev.mayaqq.estrogen.client.cosmetics.model.animation;

import dev.mayaqq.estrogen.utils.Registry;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class Animations {

    public static final Registry<Animation.Target> TARGETS = new Registry<>();

    public static final Registry<Keyframe.Interpolation> INTERPOLATIONS = new Registry<>();

    public static void animate(Animatable.Provider source, AnimationDefinition definition, long accumulatedTime, float scale, Vector3f animationVecCache) {
        float elapsed = getElapsedSeconds(definition, accumulatedTime);

        for(Map.Entry<String, List<Animation>> entry : definition.animations().entrySet()) {
            Optional<Animatable> optional = source.getAny(entry.getKey());
            List<Animation> animations = entry.getValue();

            optional.ifPresent(animatable -> animations.forEach(animation -> {
                Keyframe[] keyframes = animation.keyframes();
                int last = Math.max(0, Mth.binarySearch(0, keyframes.length, index -> elapsed <= keyframes[index].timestamp()) - 1);
                int next = Math.min(keyframes.length - 1, last + 1);

                Keyframe lastFrame = keyframes[last];
                Keyframe nextFrame = keyframes[next];
                float sinceLast = elapsed - lastFrame.timestamp();
                float delta;

                if (next != last) {
                    delta = Mth.clamp(sinceLast / (nextFrame.timestamp() - lastFrame.timestamp()), 0.0F, 1.0F);
                } else {
                    delta = 0.0F;
                }

                nextFrame.interpolation().apply(animationVecCache, delta, keyframes, last, next, scale);
                animation.target().apply(animatable, animationVecCache);
            }));
        }
    }

    private static float getElapsedSeconds(AnimationDefinition animationDefinition, long accumulatedTime) {
        float f = (float)accumulatedTime / 1000.0F;
        return animationDefinition.looping() ? f % animationDefinition.seconds() : f;
    }

}
