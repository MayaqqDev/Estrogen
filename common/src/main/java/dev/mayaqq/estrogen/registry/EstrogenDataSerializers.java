package dev.mayaqq.estrogen.registry;

import dev.mayaqq.estrogen.registry.entities.MothEntity;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class EstrogenDataSerializers {
    public static final EstrogenDataSerializers DATA_SERIALIZERS = new EstrogenDataSerializers();

    public static final EntityDataSerializer<MothEntity.State> MothAnimationStateSerializer = EntityDataSerializer.simpleEnum(MothEntity.State.class);

    public void init() {
        EntityDataSerializers.registerSerializer(MothAnimationStateSerializer);
    }
}
