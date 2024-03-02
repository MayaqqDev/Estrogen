package dev.mayaqq.estrogen.platform.fabric;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.world.entity.LivingEntity;

public class CommonHooksImpl {
    public static double getReach(LivingEntity entity) {
        return ReachEntityAttributes.getReachDistance(entity, 3);
    }
}
