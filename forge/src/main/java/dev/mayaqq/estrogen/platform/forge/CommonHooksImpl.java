package dev.mayaqq.estrogen.platform.forge;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.ForgeMod;

public class CommonHooksImpl {
    public static double getReach(LivingEntity entity) {
        return entity.getAttributeValue(ForgeMod.BLOCK_REACH.get());
    }
}
