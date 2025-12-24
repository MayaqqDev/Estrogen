package dev.mayaqq.estrogen.utils.entity

import net.minecraft.world.entity.MobCategory

val moth: MobCategory = MobCategory.create("MOTH", "moth", 50, true, true, 128)

actual fun getMothMobCategory(): MobCategory = moth