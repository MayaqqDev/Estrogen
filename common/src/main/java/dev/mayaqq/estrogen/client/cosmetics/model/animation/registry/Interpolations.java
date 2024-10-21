package dev.mayaqq.estrogen.client.cosmetics.model.animation.registry;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.cosmetics.model.animation.Keyframe;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

import static dev.mayaqq.estrogen.client.cosmetics.model.animation.Animations.INTERPOLATIONS;

public final class Interpolations {

    public static final Keyframe.Interpolation LINEAR = INTERPOLATIONS.register(Estrogen.id("linear"),
        (vector, delta, keyframes, currentFrame, targetFrame, strength) -> {
            Vector3f vector3f = keyframes[currentFrame].target();
            Vector3f vector3f2 = keyframes[targetFrame].target();
            return vector3f.lerp(vector3f2, delta, vector).mul(strength);
        }
    );

    public static final Keyframe.Interpolation CATMULLROM = INTERPOLATIONS.register(Estrogen.id("catmullrom"),
        (vector, delta, keyframes, currentFrame, targetFrame, strength) -> {
            Vector3f vector3f = keyframes[Math.max(0, currentFrame - 1)].target();
            Vector3f vector3f2 = keyframes[currentFrame].target();
            Vector3f vector3f3 = keyframes[targetFrame].target();
            Vector3f vector3f4 = keyframes[Math.min(keyframes.length - 1, targetFrame + 1)].target();
            vector.set(
                Mth.catmullrom(delta, vector3f.x(), vector3f2.x(), vector3f3.x(), vector3f4.x()) * strength,
                Mth.catmullrom(delta, vector3f.y(), vector3f2.y(), vector3f3.y(), vector3f4.y()) * strength,
                Mth.catmullrom(delta, vector3f.z(), vector3f2.z(), vector3f3.z(), vector3f4.z()) * strength
            );
            return vector;
        }
    );


    public static final Keyframe.Interpolation NONE = INTERPOLATIONS.register(Estrogen.id("none"),
        (vector, delta, keyframes, currentFrame, targetFrame, strength) -> keyframes[targetFrame].target());

    public static void register() {}
}
