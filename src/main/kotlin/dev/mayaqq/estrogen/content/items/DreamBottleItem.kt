package dev.mayaqq.estrogen.content.items

import dev.mayaqq.cynosure.utils.toVector3d
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.EstrogenSounds
import dev.mayaqq.estrogen.content.blockEntities.DreamBlockEntity
import dev.mayaqq.estrogen.content.blocks.DreamBlock
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemNameBlockItem
import net.minecraft.world.item.Items
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.shapes.BooleanOp
import net.minecraft.world.phys.shapes.Shapes

class DreamBottleItem(p0: Block, p1: Properties) : ItemNameBlockItem(p0, p1) {

    public override fun getPlaceSound(state: BlockState): SoundEvent = EstrogenSounds.DREAM_BLOCK_PLACE

    override fun getPlacementState(context: BlockPlaceContext): BlockState? {
        return super.getPlacementState(context)?.setValue(DreamBlock.PERSISTENT, true)
    }

    override fun useOn(context: UseOnContext): InteractionResult {
        val level = context.level
        val actionResult = super.useOn(context)
        if (!level.isClientSide && actionResult.consumesAction() && context.player != null && !context.player!!.isCreative) {
            context.player!!.inventory.placeItemBackInInventory(Items.GLASS_BOTTLE.defaultInstance)
        }
        return actionResult
    }
}