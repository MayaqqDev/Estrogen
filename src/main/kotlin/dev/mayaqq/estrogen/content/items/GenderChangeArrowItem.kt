package dev.mayaqq.estrogen.content.items

import dev.mayaqq.estrogen.content.entities.GenderChangeArrow
import net.minecraft.core.Direction
import net.minecraft.core.Position
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.AbstractArrow
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.item.ArrowItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class GenderChangeArrowItem(properties: Properties) : ArrowItem(properties) {
    override fun createArrow(level: Level, ammo: ItemStack, shooter: LivingEntity, weapon: ItemStack?): AbstractArrow {
        return GenderChangeArrow(level, shooter, ammo.copyWithCount(1), weapon)
    }

    override fun asProjectile(level: Level, pos: Position, stack: ItemStack, dir: Direction): Projectile =
        GenderChangeArrow(level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1), null).apply {
            pickup = AbstractArrow.Pickup.ALLOWED
        }
}