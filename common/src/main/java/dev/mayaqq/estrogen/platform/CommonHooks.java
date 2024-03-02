package dev.mayaqq.estrogen.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.LivingEntity;

public class CommonHooks {

    private CommonHooks() {}

    @ExpectPlatform
    public static double getReach(LivingEntity entity) {
        throw new AssertionError();
    }
}
