package dev.mayaqq.estrogen.client.cosmetics.model.animation.registry;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.cosmetics.model.animation.Animations;
import dev.mayaqq.estrogen.client.cosmetics.model.animation.VectorType;
import net.minecraft.util.Mth;

public final class VectorTypes {

    public static final VectorType DEFAULT = Animations.VECTOR_TYPES.register(Estrogen.id("default"), new VectorType(vec -> vec));

    public static final VectorType ANGLE = Animations.VECTOR_TYPES.register(Estrogen.id("angle"), new VectorType(vec ->
        vec.set(vec.x * Mth.DEG_TO_RAD, vec.y * Mth.DEG_TO_RAD, vec.z * Mth.DEG_TO_RAD)));

    public static final VectorType SCALE = Animations.VECTOR_TYPES.register(Estrogen.id("scale"), new VectorType(vec ->
        vec.set(vec.x + 1f, vec.y + 1f, vec.z + 1f)));

    public static void register() {}
}
