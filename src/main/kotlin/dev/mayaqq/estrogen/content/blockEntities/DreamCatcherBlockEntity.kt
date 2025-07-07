package dev.mayaqq.estrogen.content.blockEntities

import dev.engine_room.flywheel.lib.visualization.VisualizationHelper
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class DreamCatcherBlockEntity(be: BlockEntityType<*>, pos: BlockPos, state: BlockState) : BlockEntity(be, pos, state) {
    var colorLeft: Int? = null
    var colorMiddle: Int? = null
    var colorRight: Int? = null

    override fun saveAdditional(tag: CompoundTag) {
        val colors = CompoundTag()
        colors.putInt("left", colorLeft ?: -1)
        colors.putInt("middle", colorMiddle ?: -1)
        colors.putInt("right", colorRight ?: -1)
        tag.put("colors", colors)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        if (tag.contains("colors")) {
            val colors = tag.getCompound("colors")
            colorLeft = colors.getInt("left")
            colorMiddle = colors.getInt("middle")
            colorRight = colors.getInt("middle")
        }
        if(level?.isClientSide == true) updateOnClient() else sync(false)
    }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener> {
        return ClientboundBlockEntityDataPacket.create(this)
    }

    fun updateOnClient() {
        VisualizationHelper.queueUpdate(this)
    }

    private fun sync(saveAswell: Boolean = true) {
        if(saveAswell) setChanged()
        if(this.level?.isClientSide == true) return
        val level = this.level as? ServerLevel ?: return
        for (player in level.server.playerList.players) {
            player.connection.send(updatePacket)
        }
    }
}