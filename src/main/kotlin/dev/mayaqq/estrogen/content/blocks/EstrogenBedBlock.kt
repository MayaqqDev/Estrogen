package dev.mayaqq.estrogen.content.blocks

import dev.mayaqq.cynosure.blocks.model.ModelBedBlock
import dev.mayaqq.estrogen.content.EstrogenBlockEntities
import dev.mayaqq.estrogen.content.blockEntities.EstrogenBedBlockEntity
import invoke.kitty.kritter.blockEntity.BlockWithEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class EstrogenBedBlock(properties: Properties) : ModelBedBlock(properties), BlockWithEntity<EstrogenBedBlockEntity> {
    override val blockEntityClass: Class<out EstrogenBedBlockEntity> get() = EstrogenBedBlockEntity::class.java
    override fun blockEntityType(): BlockEntityType<out EstrogenBedBlockEntity> = EstrogenBlockEntities.EstrogenBedBlock.get()
    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = EstrogenBedBlockEntity(pos, state)
}