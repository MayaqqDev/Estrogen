package dev.mayaqq.estrogen.client.cosmetics.model.animation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.util.StringRepresentable;
import org.joml.Vector3fc;

import java.util.List;
import java.util.Locale;
import java.util.function.BiConsumer;

public record Animation(float seconds, Target target, Keyframe... keyframes) {

    public static final Codec<Animation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.FLOAT.fieldOf("duration").forGetter(Animation::seconds),
        Target.CODEC.fieldOf("target").forGetter(Animation::target),
        Keyframe.CODEC.listOf().fieldOf("keyframes").forGetter(anim -> List.of(anim.keyframes))
    ).apply(instance, (secs, target, keyframes) ->
        new Animation(secs, target, keyframes.toArray(Keyframe[]::new))));


    public enum Target implements StringRepresentable {
        POSITION(Animatable::offsetPosition),
        ROTATION(Animatable::offsetRotation),
        SCALE(Animatable::offsetScale);

        public static final Codec<Target> CODEC = StringRepresentable.fromEnum(Target::values);

        private final BiConsumer<Animatable, Vector3fc> action;

        Target(BiConsumer<Animatable, Vector3fc> action) {
            this.action = action;
        }

        public void apply(Animatable animatable, Vector3fc offset) {
            action.accept(animatable, offset);
        }

        @Override
        public String getSerializedName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
