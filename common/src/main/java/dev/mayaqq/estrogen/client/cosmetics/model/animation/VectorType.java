package dev.mayaqq.estrogen.client.cosmetics.model.animation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.mayaqq.estrogen.client.cosmetics.model.animation.registry.VectorTypes;
import dev.mayaqq.estrogen.utils.EstrogenCodecs;
import net.minecraft.util.ExtraCodecs;
import org.joml.Vector3f;

import java.util.function.UnaryOperator;

public record VectorType(UnaryOperator<Vector3f> transform) {

    public static final Codec<ConfiguredVec3f> CONFIGURED_CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Animations.VECTOR_TYPES.codec().fieldOf("type").forGetter(ConfiguredVec3f::type),
        ExtraCodecs.VECTOR3F.fieldOf("value").forGetter(ConfiguredVec3f::value)
    ).apply(instance, ConfiguredVec3f::new));

    public static final Codec<Vector3f> VEC_CODEC = EstrogenCodecs.alternatives(
        ExtraCodecs.VECTOR3F,
        CONFIGURED_CODEC.xmap(
            configured -> configured.type.transform.apply(configured.value),
            vec -> new ConfiguredVec3f(VectorTypes.DEFAULT, vec)
        )
    );

    @Override
    public String toString() {
        return Animations.VECTOR_TYPES.getKey(this).toString();
    }

    public record ConfiguredVec3f(VectorType type, Vector3f value) {}
}
