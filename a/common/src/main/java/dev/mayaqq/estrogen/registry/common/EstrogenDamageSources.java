package dev.mayaqq.estrogen.registry.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenDamageSources {
    public static final ResourceKey<DamageType> GIRLPOWER_DAMAGE_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE, id("girlpower"));

    public static DamageSource of(Level world, ResourceKey<DamageType> key) {
        return new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolder(key).get());
    }
}
