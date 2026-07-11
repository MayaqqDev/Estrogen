package dev.mayaqq.estrogen.content.blockEntities

import dev.engine_room.flywheel.lib.visualization.VisualizationHelper
import dev.mayaqq.estrogen.content.EstrogenComponents
import dev.mayaqq.estrogen.utils.TriColor
import dev.mayaqq.estrogen.utils.getTriColor
import dev.mayaqq.estrogen.utils.putTriColor
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponentMap
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class DreamCatcherBlockEntity(be: BlockEntityType<*>, pos: BlockPos, state: BlockState) : BlockEntity(be, pos, state) {
    var triColor: TriColor? = null

    override fun saveAdditional(tag: CompoundTag, lookup: HolderLookup.Provider) {
        tag
        if (triColor != null) tag.putTriColor(triColor!!)
    }

    override fun loadAdditional(tag: CompoundTag, lookup: HolderLookup.Provider) {
        super.loadAdditional(tag, lookup)
        if (tag.contains("colors")) {
            triColor = tag.getTriColor()
        }
        if(level?.isClientSide == true) updateOnClient() else sync(false)
    }

    override fun applyImplicitComponents(input: DataComponentInput) {
        super.applyImplicitComponents(input)
        this.triColor = input.get(EstrogenComponents.TriColorComponent)
    }

    override fun collectImplicitComponents(components: DataComponentMap.Builder) {
        super.collectImplicitComponents(components)
        triColor?.let { components.set(EstrogenComponents.TriColorComponent, it) }
    }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener> {
        return ClientboundBlockEntityDataPacket.create(this)
    }

    override fun getUpdateTag(lookup: HolderLookup.Provider): CompoundTag {
        return saveWithoutMetadata(lookup)
    }

    fun updateOnClient() {
        VisualizationHelper.queueUpdate(this)
    }

    fun setColors(triColor: TriColor) {
        this.triColor = triColor
        sync()
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