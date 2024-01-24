package dev.mayaqq.estrogen.registry.common;

import net.minecraft.world.damagesource.DamageSource;

public class EstrogenDamageSources {
    public static final DamageSource GIRLPOWER_DAMAGE_TYPE = getGirlpowerDamageType();

    private static DamageSource getGirlpowerDamageType() {
        return new DamageSource("girlpower").setIsFall();
    }
}
