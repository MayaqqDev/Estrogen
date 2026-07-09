package dev.mayaqq.estrogen.content.items

import dev.mayaqq.cynosure.utils.currentTime
import dev.mayaqq.estrogen.content.EstrogenAttributes
import dev.mayaqq.estrogen.utils.holder
import invoke.kitty.kritter.registry.api.entry.holder
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.core.particles.DustParticleOptions
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.*
import net.minecraft.world.level.Level
import org.joml.Vector3f
import kotlin.math.cos
import kotlin.math.sin

class GenderChangePotionItem(properties: Properties) : Item(properties) {
    override fun finishUsingItem(stack: ItemStack, level: Level, livingEntity: LivingEntity): ItemStack {
        super.finishUsingItem(stack, level, livingEntity)
        if (livingEntity is ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(livingEntity, stack)
            livingEntity.awardStat(Stats.ITEM_USED.get(this))
        }

        changeGender(level, livingEntity)

        if (stack.isEmpty) {
            return ItemStack(Items.GLASS_BOTTLE)
        } else if (livingEntity is Player && !livingEntity.abilities.instabuild) {
            stack.shrink(1)
            val itemStack = ItemStack(Items.GLASS_BOTTLE)
            if (!livingEntity.inventory.add(itemStack)) {
                livingEntity.drop(itemStack, false)
            }
        }

        playParticles(level, livingEntity)

        return stack
    }

    companion object {
        fun playParticles(level: Level, livingEntity: LivingEntity) {
            if (level.isClientSide()) {
                val playerPos = livingEntity.position()
                val heightIncrement = livingEntity.boundingBox.ysize / 50

                for (i in 0..49) {
                    val angle = i * Math.PI / 16
                    var x = playerPos.x + 0.5 * cos(angle)
                    var y = playerPos.y + i * heightIncrement
                    var z = playerPos.z + 0.5 * sin(angle)

                    y += level.random.nextFloat() * 0.1 * (if (level.random.nextBoolean()) 1 else -1)

                    level.addParticle(
                        DustParticleOptions(Vector3f(1f, 0.30f, 0.70f), 1f),
                        x, y, z,
                        0.0, 0.0, 0.0
                    )

                    x = playerPos.x + 0.5 * cos(angle + Math.PI)
                    z = playerPos.z + 0.5 * sin(angle + Math.PI)
                    y += level.random.nextFloat() * 0.1 * (if (level.random.nextBoolean()) 1 else -1)

                    level.addParticle(
                        DustParticleOptions(Vector3f(0.15f, 0.20f, 0.81f), 1f),
                        x, y, z,
                        0.0, 0.0, 0.0
                    )
                }
            }
        }

        fun changeGender(level: Level, livingEntity: LivingEntity?) {
            if (!level.isClientSide && livingEntity is Player) {
                val showBoobs = livingEntity.getAttribute(EstrogenAttributes.ShowBoobs.holder)
                val startTime = livingEntity.getAttribute(EstrogenAttributes.BoobGrowingStartTime.holder)
                val initialSize = livingEntity.getAttribute(EstrogenAttributes.BoobInitialSize.holder)
                if (showBoobs != null && startTime != null && initialSize != null) {
                    if (showBoobs.baseValue > 0.0) {
                        showBoobs.baseValue = 0.0
                        startTime.baseValue = -1.0
                        initialSize.baseValue = 0.0
                    } else {
                        showBoobs.baseValue = 1.0
                        if (startTime.baseValue < 0.0) {
                            val currentTime: Double = currentTime(livingEntity.level())
                            startTime.baseValue = currentTime
                        }
                    }
                }
            }
        }

        fun changeGender(level: Level, livingEntity: LivingEntity?, gender: Int) {
            if (!level.isClientSide && livingEntity is Player) {
                val showBoobs = livingEntity.getAttribute(EstrogenAttributes.ShowBoobs.holder)
                val startTime = livingEntity.getAttribute(EstrogenAttributes.BoobGrowingStartTime.holder)
                val initialSize = livingEntity.getAttribute(EstrogenAttributes.BoobInitialSize.holder)
                if (showBoobs != null && startTime != null && initialSize != null) {
                    if (gender == 0) {
                        showBoobs.baseValue = 0.0
                        startTime.baseValue = -1.0
                        initialSize.baseValue = 0.0
                    } else {
                        showBoobs.baseValue = 1.0
                        if (startTime.baseValue < 0.0) {
                            val currentTime: Double = currentTime(livingEntity.level())
                            startTime.baseValue = currentTime
                        }
                    }
                }
            }
        }
    }
    override fun getUseDuration(stack: ItemStack, entity: LivingEntity): Int = 40
    override fun getUseAnimation(stack: ItemStack): UseAnim = UseAnim.DRINK
    override fun getDrinkingSound(): SoundEvent = SoundEvents.GENERIC_DRINK
    override fun getEatingSound(): SoundEvent = SoundEvents.GENERIC_DRINK

    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack?> {
        return ItemUtils.startUsingInstantly(level, player, hand)
    }
}