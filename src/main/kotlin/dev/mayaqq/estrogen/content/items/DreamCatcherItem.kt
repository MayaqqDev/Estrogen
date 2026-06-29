package dev.mayaqq.estrogen.content.items

import dev.mayaqq.estrogen.content.EstrogenComponents
import dev.mayaqq.estrogen.utils.TriColor
import net.minecraft.core.cauldron.CauldronInteraction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.LayeredCauldronBlock

class DreamCatcherItem(block: Block, properties: Properties) : BlockItem(block, properties) {
    fun setDyes(stack: ItemStack, triColor: TriColor) {
        stack.set(EstrogenComponents.TriColorComponent, triColor)
    }
    fun isBlank(stack: ItemStack): Boolean = stack.get(EstrogenComponents.TriColorComponent) == null

    fun triColor(stack: ItemStack): TriColor? {
        return stack.get(EstrogenComponents.TriColorComponent)
    }

    fun getColor(stack: ItemStack, tintIndex: Int): Int {
        return when (tintIndex) {
            1 -> triColor(stack)?.left?.toInt()?: -1
            2 -> triColor(stack)?.middle?.toInt()?: -1
            3 -> triColor(stack)?.right?.toInt()?: -1
            else -> -1
        }
    }

    fun clear(stack: ItemStack) {
        stack.remove(EstrogenComponents.TriColorComponent)
    }

    companion object {
        fun getItemColor(stack: ItemStack, tintIndex: Int): Int {
            val item = stack.item as DreamCatcherItem
            return item.getColor(stack, tintIndex)
        }

        val CAULDRON_INTERACTION: CauldronInteraction = CauldronInteraction { blockState, level, blockPos, player, _, itemStack ->
            val item = itemStack.item
            if (item !is DreamCatcherItem || item.isBlank(itemStack)) {
                return@CauldronInteraction ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
            }

            if (!level.isClientSide) {
                item.clear(itemStack)
                player.awardStat(Stats.CLEAN_ARMOR)
                level.playSound(null, blockPos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 0.5f, 1.8f)
                LayeredCauldronBlock.lowerFillLevel(blockState, level, blockPos)
            } else {
                val fillHeight = blockState.getValue(LayeredCauldronBlock.LEVEL) / 3f
                for (i in 0..7) {
                    val xOff = level.random.nextGaussian() / 5 + 0.5
                    val zOff = level.random.nextGaussian() / 5 + 0.5

                    level.addParticle(
                        ParticleTypes.BUBBLE_POP,
                        blockPos.x + xOff,
                        blockPos.y + fillHeight * 0.8,
                        blockPos.z + zOff,
                        0.0,
                        0.05,
                        0.0
                    )
                }
            }

            ItemInteractionResult.sidedSuccess(level.isClientSide)
        }
    }
}