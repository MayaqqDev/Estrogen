package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.id
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.damagesource.DamageType
import net.minecraft.world.level.Level

object EstrogenDamageSources {
    val GIRLPOWER = ResourceKey.create(Registries.DAMAGE_TYPE, id("girlpower"))
    val COLON_THREE = ResourceKey.create(Registries.DAMAGE_TYPE, id("colon_three"))

    fun of(level: Level, key: ResourceKey<DamageType>): DamageSource {

        return DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolder(key).get())
    }

    fun ResourceKey<DamageType>.getSource(level: Level) = of(level, this)
}