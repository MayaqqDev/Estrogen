package dev.mayaqq.estrogen.content.items

import dev.mayaqq.estrogen.content.EstrogenDamageSources
import dev.mayaqq.estrogen.content.EstrogenDamageSources.getSource
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class ColonThreeItem(properties: Properties) : Item(properties) {
    override fun getUseDuration(stack: ItemStack, entity: LivingEntity): Int = 64

    override fun finishUsingItem(stack: ItemStack, level: Level, entity: LivingEntity): ItemStack {
        entity.hurt(EstrogenDamageSources.COLON_THREE.getSource(level), Float.MAX_VALUE)
        return super.finishUsingItem(stack, level, entity)
    }
}