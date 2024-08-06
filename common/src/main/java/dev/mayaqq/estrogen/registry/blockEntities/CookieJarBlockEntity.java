package dev.mayaqq.estrogen.registry.blockEntities;

import com.simibubi.create.foundation.blockEntity.SyncedBlockEntity;
import dev.mayaqq.estrogen.registry.EstrogenBlockEntities;
import dev.mayaqq.estrogen.registry.EstrogenTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.ticks.ContainerSingleItem;
import org.jetbrains.annotations.Nullable;

public class CookieJarBlockEntity extends SyncedBlockEntity implements Container {
    private NonNullList<ItemStack> items = NonNullList.withSize(8, ItemStack.EMPTY);

    public CookieJarBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(EstrogenBlockEntities.COOKIE_JAR.get(), blockPos, blockState);
    }

    /**
     * Add 1 count of the item to the jar.
     * Does not decrement the input itemStack.
     */
    public void add1Item(ItemStack itemStack) {
        int i = 0;
        for (ItemStack jarItemStack : items) {
            if (jarItemStack.isEmpty()) {
                items.set(i, itemStack.copyWithCount(1));
                notifyUpdate();
                return;
            }
            if (ItemStack.isSameItemSameTags(jarItemStack, itemStack) && jarItemStack.getCount() < jarItemStack.getMaxStackSize()) {
                jarItemStack.grow(1);
                notifyUpdate();
                return;
            }
            i++;
        }
    }

    /**
     * Remove and returns 1 count of the last-most item from the jar.
     * returns ItemStack.EMPTY if jar was empty
     */
    public ItemStack remove1Item() {
        for (int i = items.size() - 1; i >= 0; i--) {
            ItemStack jarItemStack = items.get(i);
            if (jarItemStack.isEmpty()) {
                continue;
            }
            ItemStack itemStack = jarItemStack.split(1);
            notifyUpdate();
            return itemStack;
        }
        return ItemStack.EMPTY;
    }

    /**
     * Returns true if 1 of the item can be added to the jar
     * and that item is a cookie
     * and that cookie matches the cookie already in the jar.
     */
    public boolean canAddItem(ItemStack itemStack) {
        // cookie condition
        if (!canPlaceItem(0, itemStack)) {
            return false;
        }

        // if the top condition is removed, then the jar can work for any item
        for (ItemStack jarItemStack : items) {
            if (jarItemStack.isEmpty()) {
                return true;
            }
            if (!ItemStack.isSameItemSameTags(jarItemStack, itemStack)) {
                continue;
            }
            if (jarItemStack.getCount() >= jarItemStack.getMaxStackSize()) {
                continue;
            }
            return true;
        }
        return false;
    }

    /**
     * Returns number of items.
     */
    public int getCount() {
        int count = 0;
        for (ItemStack jarItemStack : items) {
            if (jarItemStack.isEmpty()) {
                continue;
            }
            count += jarItemStack.getCount();
        }
        return count;
    }

    /**
     * Gets items. For use in rendering.
     */
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    /**
     * Returns true if the provided ItemStack matches the first item in the jar,
     * or if the jar is empty.
     */
    public boolean matchesFirstItem(ItemStack itemStack) {
        // this is because chutes do NOT obey Container.canTakeItem.
        // so they ignore FILO, which can allow for different cookies in the same jar.
        for (ItemStack jarItemStack : items) {
            if (jarItemStack.isEmpty()) {
                continue;
            }
            if (!ItemStack.isSameItemSameTags(jarItemStack, itemStack)) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }
    
    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        ContainerHelper.saveAllItems(compoundTag, items);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        this.items.clear();
        ContainerHelper.loadAllItems(compoundTag, items);
    }


    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int slot) {
        if (slot >= items.size()) {
            return null;
        }
        return getItems().get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int count) {
        ItemStack itemStack = ContainerHelper.removeItem(items, slot, count);
        if (!itemStack.isEmpty()) {
            notifyUpdate();
        }

        return itemStack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(items, slot);
    }

    @Override
    public void setItem(int slot, ItemStack itemStack) {
        // teehee! this doesn't actually test for the item type!
        // do the testing in CookieJarBlock.onUse()!
        items.set(slot, itemStack);
        if (itemStack.getCount() > this.getMaxStackSize()) {
            itemStack.setCount(this.getMaxStackSize());
        }

        notifyUpdate();
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack itemStack) {
        if (!itemStack.is(EstrogenTags.Items.COOKIES)) {
            return false;
        }
        if (!matchesFirstItem(itemStack)) {
            return false;
        }
        return true;
    }

    @Override
    public void clearContent() {
        items.clear();
    }
}