package dev.mayaqq.estrogen.client.cosmetics.model.animation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.joml.Vector3f;

import static dev.mayaqq.estrogen.client.cosmetics.model.animation.Animations.INTERPOLATIONS;

public record Keyframe(float timestamp, Vector3f target, Interpolation interpolation) {

    public static final Codec<Keyframe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.FLOAT.fieldOf("timestamp").forGetter(Keyframe::timestamp),
        VectorType.VEC_CODEC.fieldOf("target").forGetter(Keyframe::target),
        INTERPOLATIONS.codec().fieldOf("interpolation").forGetter(Keyframe::interpolation)
    ).apply(instance, Keyframe::new));

    @FunctionalInterface
    public interface Interpolation {
        Vector3f apply(Vector3f vector, float delta, Keyframe[] keyframes, int currentFrame, int targetFrame, float strength);
    }
}
