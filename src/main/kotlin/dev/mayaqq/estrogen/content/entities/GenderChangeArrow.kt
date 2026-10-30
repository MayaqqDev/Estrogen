package dev.mayaqq.estrogen.content.entities

import dev.mayaqq.estrogen.content.EstrogenEntities
import dev.mayaqq.estrogen.content.EstrogenItems
import dev.mayaqq.estrogen.content.items.GenderChangePotionItem
import dev.mayaqq.estrogen.utils.defaultInstance
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.AbstractArrow
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import java.util.concurrent.ConcurrentLinkedDeque
import java.util.concurrent.ConcurrentLinkedQueue

class GenderChangeArrow: AbstractArrow {

    val tailPoints = ConcurrentLinkedDeque<Vec3>()
    var lastRemoved: Vec3? = null

    constructor(entityType: EntityType<GenderChangeArrow>, level: Level): super(entityType, level)

    constructor(level: Level, x: Double, y: Double, z: Double, pickupStack: ItemStack, firedFromStack: ItemStack?):
            super(EstrogenEntities.GenderChangeArrow.get(), x, y, z, level, pickupStack, firedFromStack)

    constructor(level: Level, owner: LivingEntity, pickupStack: ItemStack, firedFromStack: ItemStack?):
            super(EstrogenEntities.GenderChangeArrow.get(), owner, level, pickupStack, firedFromStack)

    override fun tick() {
        tailPoints.addFirst(this.position())
        super.tick()
        if (tailPoints.size > 32) {
            lastRemoved = tailPoints.last
            tailPoints.removeLast()
        }
    }

    override fun getBoundingBoxForCulling(): AABB {
        return super.getBoundingBoxForCulling().apply {
            if (tailPoints.isNotEmpty()) expandTowards(tailPoints.last)
        }
    }

    override fun doPostHurtEffects(entity: LivingEntity) {
        super.doPostHurtEffects(entity)
        GenderChangePotionItem.changeGender(this.level(), entity)
        GenderChangePotionItem.playParticlesServer(this.level(), entity)
    }

    fun shouldRenderTail(): Boolean = tailPoints.size < 2 || tailPoints.first != tailPoints.last

    override fun getDefaultPickupItem(): ItemStack = EstrogenItems.GenderChangeArrow.defaultInstance()
}