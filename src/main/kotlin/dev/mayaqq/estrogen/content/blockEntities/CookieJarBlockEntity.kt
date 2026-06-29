package dev.mayaqq.estrogen.content.blockEntities

import dev.engine_room.flywheel.lib.visualization.VisualizationHelper
import dev.mayaqq.estrogen.content.EstrogenTags
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.Container
import net.minecraft.world.ContainerHelper
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class CookieJarBlockEntity(type: BlockEntityType<*>, blockPos: BlockPos, blockState: BlockState) : BlockEntity(type, blockPos, blockState), Container {

    val items: NonNullList<ItemStack> = NonNullList.withSize(8, ItemStack.EMPTY)

    /**
     * Remove and returns 1 count of the last-most item from the jar.
     * returns ItemStack.EMPTY if jar was empty
     */
    fun remove1Item(): ItemStack {
        for (i in items.indices.reversed()) {
            val jarItemStack = items[i]
            if (jarItemStack.isEmpty) {
                continue
            }
            val itemStack = jarItemStack.split(1)
            setChanged()
            sync(false)
            return itemStack
        }
        return ItemStack.EMPTY
    }

    /**
     * Adds the item stack to the jar, and returns the item stack not added to the jar.
     * @param itemStack The stack to be added to the jar.
     * @return The stack not added to the jar.
     */
    fun addItemStack(itemStack: ItemStack): ItemStack {
        if (!canPlaceItem(0, itemStack)) return itemStack

        val itemStackCopy = itemStack.copy()
        var i = 0
        for (jarItemStack in items) {
            if (!jarItemStack.isEmpty && !ItemStack.isSameItemSameComponents(jarItemStack, itemStackCopy)) continue

            val addToJar = itemStackCopy.split(itemStackCopy.maxStackSize - jarItemStack.count)
            addToJar.grow(jarItemStack.count)
            items[i] = addToJar

            if (itemStackCopy.isEmpty) break

            i++
        }
        setChanged()
        sync(false)
        return itemStackCopy
    }

    /**
     * Removes and returns a stack of items from the jar.
     * @return The stack taken from the jar.
     */
    fun removeItemStack(): ItemStack {
        var result = ItemStack.EMPTY
        for (i in items.indices.reversed()) {
            val jarItemStack = items[i]
            if (jarItemStack.isEmpty) {
                continue
            }
            if (result.isEmpty) {
                result = jarItemStack.split(jarItemStack.maxStackSize)
                continue
            }

            if (!ItemStack.isSameItemSameComponents(jarItemStack, result)) break
            val fromJarStack = jarItemStack.split(result.maxStackSize - result.count)
            result.grow(fromJarStack.count)
            if (result.count == result.maxStackSize) break
        }
        setChanged()
        sync(false)
        return result
    }

    val count: Int
        get() {
            var count = 0
            for (jarItemStack in items) {
                if (jarItemStack.isEmpty) {
                    continue
                }
                count += jarItemStack.count
            }
            return count
        }

    /**
     * Returns true if the provided ItemStack matches the first item in the jar,
     * or if the jar is empty.
     */
    fun matchesFirstItem(itemStack: ItemStack): Boolean {
        // this is because chutes do NOT obey Container.canTakeItem.
        // so they ignore FILO, which can allow for different cookies in the same jar.
        for (jarItemStack in items) {
            if (jarItemStack.isEmpty) {
                continue
            }
            return ItemStack.isSameItemSameComponents(jarItemStack, itemStack)
        }
        return true
    }

    override fun saveAdditional(compoundTag: CompoundTag, holder: HolderLookup.Provider) {
        super.saveAdditional(compoundTag, holder)
        ContainerHelper.saveAllItems(compoundTag, items, holder)
    }

    override fun loadAdditional(compoundTag: CompoundTag, holder: HolderLookup.Provider) {
        super.loadAdditional(compoundTag, holder)
        items.clear()
        ContainerHelper.loadAllItems(compoundTag, items, holder)
        if(level?.isClientSide == true) updateOnClient() else sync(false)
    }

    fun update() {
        sync(false)
    }

    fun updateOnClient() {
        VisualizationHelper.queueUpdate(this)
    }

    override fun getContainerSize(): Int {
        return items.size
    }

    override fun isEmpty(): Boolean {
        return items.stream().allMatch { obj: ItemStack -> obj.isEmpty }
    }

    override fun getItem(slot: Int): ItemStack {
        if (slot >= items.size) {
            return ItemStack.EMPTY
        }
        return items[slot]
    }

    override fun removeItem(slot: Int, count: Int): ItemStack {
        val itemStack = ContainerHelper.removeItem(items, slot, count)
        if (!itemStack.isEmpty) {
            setChanged()
            sync(false)
        }
        return itemStack
    }

    override fun removeItemNoUpdate(slot: Int): ItemStack {
        return ContainerHelper.takeItem(items, slot)
    }

    override fun setItem(slot: Int, itemStack: ItemStack) {
        // teehee! this doesn't actually test for the item type!
        // do the testing in CookieJarBlock.onUse()!
        items[slot] = itemStack
        if (itemStack.count > this.maxStackSize) {
            itemStack.count = this.maxStackSize
        }

        setChanged()
        sync(false)
    }

    override fun stillValid(player: Player): Boolean {
        return Container.stillValidBlockEntity(this, player)
    }

    override fun canPlaceItem(slot: Int, itemStack: ItemStack): Boolean =
        itemStack.`is`(EstrogenTags.Items.COOKIES) && matchesFirstItem(itemStack)

    override fun clearContent() {
        items.clear()
    }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener> {
        return ClientboundBlockEntityDataPacket.create(this)
    }

    override fun getUpdateTag(holder: HolderLookup.Provider): CompoundTag = saveWithFullMetadata(holder)

    private fun sync(saveAswell: Boolean = true) {
        if(saveAswell) setChanged()
        if(this.level?.isClientSide == true) {
            updateOnClient()
            return
        }
        val level = this.level as? ServerLevel ?: return
        for (player in level.server.playerList.players) {
            player.connection.send(updatePacket)
        }
    }
}