package dev.mayaqq.estrogen.content.blockEntities

import dev.engine_room.flywheel.lib.visualization.VisualizationHelper
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class DreamCatcherBlockEntity(be: BlockEntityType<*>, pos: BlockPos, state: BlockState) : BlockEntity(be, pos, state) {
    var colorLeft: DyeColor? = null
    var colorMiddle: DyeColor? = null
    var colorRight: DyeColor? = null

    override fun saveAdditional(tag: CompoundTag) {
        val colors = CompoundTag()
        colors.putString("left", colorLeft?.name ?: "empty")
        colors.putString("middle", colorMiddle?.name ?: "empty")
        colors.putString("right", colorRight?.name ?: "empty")
        tag.put("colors", colors)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        if (tag.contains("colors")) {
            val colors = tag.getCompound("colors")
            colorLeft = DyeColor.entries.firstOrNull { it.name == colors.getString("left") }
            colorMiddle = DyeColor.entries.firstOrNull { it.name == colors.getString("middle") }
            colorRight = DyeColor.entries.firstOrNull { it.name == colors.getString("middle") }
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