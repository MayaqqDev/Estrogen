package dev.mayaqq.estrogen.content.blockEntities

import dev.mayaqq.estrogen.content.EstrogenBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class EstrogenBedBlockEntity(be: BlockEntityType<EstrogenBedBlockEntity>, pos: BlockPos, state: BlockState) : BlockEntity(be, pos, state) {
    constructor(pos: BlockPos, state: BlockState): this(EstrogenBlockEntities.EstrogenBedBlock.get(), pos, state)

    override fun getUpdatePacket(): Packet<ClientGamePacketListener> = ClientboundBlockEntityDataPacket.create(this)
}