package dev.mayaqq.estrogen.registry.blockEntities;

import com.simibubi.create.foundation.blockEntity.SyncedBlockEntity;
import dev.mayaqq.estrogen.registry.EstrogenBlockEntities;
import dev.mayaqq.estrogen.registry.EstrogenTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CookieJarBlockEntity extends SyncedBlockEntity implements WorldlyContainer {
    private final NonNullList<ItemStack> items = NonNullList.withSize(8, ItemStack.EMPTY);

    public CookieJarBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(EstrogenBlockEntities.COOKIE_JAR.get(), blockPos, blockState);
    }

    public boolean canRemoveCookie() {
        return this.getCookieCount() > 0;
    }

    public boolean canAddItem(ItemStack stack) {
        return this.getCookieCount() < 512 && (this.getCookieCount() == 0 ? stack.is(EstrogenTags.Items.COOKIES) : stack.is(this.getItem(0).getItem()));
    }

    private void addStack(ItemStack stack) {
        int toAdd = stack.getCount();
        for (int i = 0; i < this.items.size(); i++) {
            ItemStack stackInSlot = getItem(i);
            int canAdd = stackInSlot.getMaxStackSize() - stackInSlot.getCount();
            if (canAdd >= toAdd) {
                this.setItem(i, stack.copyWithCount(stackInSlot.getCount() + toAdd));
                stackInSlot.setCount(stackInSlot.getCount() + toAdd);
                break;
            } else {
                this.setItem(i, stack.copyWithCount(stackInSlot.getCount() + canAdd));
                toAdd -= canAdd;
            }
        }
    }

    /**
     * Don't call without calling canRemoveCookie
     */
    public void removeCookie() {
        for (int i = this.items.size() - 1; i >= 0; i--) {
            ItemStack stackInSlot = getItem(i);
            if (stackInSlot.isEmpty()) {
                continue;
            }
            this.setItem(i, stackInSlot.copyWithCount(stackInSlot.getCount() - 1));
            break;
        }
    }

    /**
     * Don't call without calling canAddCookie
     */
    public void addCookie(ItemStack stack) {
        this.addStack(stack.copyWithCount(1));
    }

    public int getCookieCount() {
        int count = 0;
        for (ItemStack item : items) {
            count += item.getCount();
        }
        return count;
    }

    public BlockEntity getContainerBlockEntity() {
        return this;
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.items) {
            if (itemStack.isEmpty()) continue;
            return false;
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.items.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(this.items, slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(this.items, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.items.set(slot, stack);
        this.setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return canAddItem(stack);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, items);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        ContainerHelper.saveAllItems(tag, items);
        super.saveAdditional(tag);
    }

    @Override
    public CompoundTag writeClient(CompoundTag tag) {
        ContainerHelper.saveAllItems(tag, items);
        return super.writeClient(tag);
    }

    @Override
    public void readClient(CompoundTag tag) {
        super.readClient(tag);
        ContainerHelper.loadAllItems(tag, items);
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return new int[8];
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
        return canAddItem(itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return canRemoveCookie();
    }
}
