package dev.mayaqq.estrogen.config;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;

public record ChestConfig(boolean enabled, boolean armorEnabled, boolean physicsEnabled, float bounciness, float damping) {

    public static final ByteCodec<ChestConfig> BYTE_CODEC = ObjectByteCodec.create(
            ByteCodec.BOOLEAN.fieldOf(ChestConfig::enabled),
            ByteCodec.BOOLEAN.fieldOf(ChestConfig::armorEnabled),
            ByteCodec.BOOLEAN.fieldOf(ChestConfig::physicsEnabled),
            ByteCodec.FLOAT.fieldOf(ChestConfig::bounciness),
            ByteCodec.FLOAT.fieldOf(ChestConfig::damping),
            ChestConfig::new
    );
}
