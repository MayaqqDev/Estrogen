package dev.mayaqq.estrogen.client.cosmetics.model.animation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.utils.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

public record Keyframe(float timestamp, Vector3f target, Interpolation interpolation) {

    public static final Codec<Keyframe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.FLOAT.fieldOf("timestamp").forGetter(Keyframe::timestamp),
        ExtraCodecs.VECTOR3F.fieldOf("target").forGetter(Keyframe::target),
        Interpolation.CODEC.fieldOf("interpolation").forGetter(Keyframe::interpolation)
    ).apply(instance, Keyframe::new));

    public interface Interpolation {

        Codec<Interpolation> CODEC = Animations.INTERPOLATIONS.createCodec();

        Interpolation LINEAR = Animations.INTERPOLATIONS.register(Estrogen.id("linear"), (vector, delta, keyframes, currentFrame, targetFrame, strength) -> {
            Vector3f vector3f = keyframes[currentFrame].target();
            Vector3f vector3f2 = keyframes[targetFrame].target();
            return vector3f.lerp(vector3f2, delta, vector).mul(strength);
        });

        Interpolation CATMULLROM = Animations.INTERPOLATIONS.register(Estrogen.id("catmullrom"), (vector, delta, keyframes, currentFrame, targetFrame, strength) -> {
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
        });

        Vector3f apply(Vector3f vector, float delta, Keyframe[] keyframes, int currentFrame, int targetFrame, float strength);
    }
}
