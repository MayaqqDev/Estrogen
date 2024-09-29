package dev.mayaqq.estrogen.client.cosmetics.model.animation;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.utils.Registry;
import net.minecraft.util.StringRepresentable;
import org.joml.Vector3fc;

import java.util.List;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.Function;

public record Animation(Target target, Keyframe... keyframes) {

    public static final Codec<Animation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Target.CODEC.fieldOf("target").forGetter(Animation::target),
        Keyframe.CODEC.listOf().fieldOf("keyframes").forGetter(anim -> List.of(anim.keyframes()))
    ).apply(instance, (target, keyframes) -> new Animation(target, keyframes.toArray(Keyframe[]::new))));


    public interface Target {
        Codec<Target> CODEC = Codec.either(
            StringRepresentable.fromEnum(Targets::values),
            Animations.TARGETS.createCodec()
        ).xmap(either -> either.map(Function.identity(), Function.identity()), Either::right);

        void apply(Animatable animatable, Vector3fc offset);
    }

    public enum Targets implements Target, StringRepresentable {
        POSITION(Animatable::offsetPosition),
        ROTATION(Animatable::offsetRotation),
        SCALE(Animatable::offsetScale);

        private final BiConsumer<Animatable, Vector3fc> action;

        Targets(BiConsumer<Animatable, Vector3fc> action) {
            Animations.TARGETS.register(Estrogen.id(name()), this);
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
