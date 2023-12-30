package dev.mayaqq.estrogen.registry.common;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenDamageSources {
    public static final RegistryKey<DamageType> GIRLPOWER_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, id("girlpower"));

    public static DamageSource of(World world, RegistryKey<DamageType> key) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).getHolder(key).get());
    }
}
