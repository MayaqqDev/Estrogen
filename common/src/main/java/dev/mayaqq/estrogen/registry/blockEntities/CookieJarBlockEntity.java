package dev.mayaqq.estrogen.registry.blockEntities;

import com.simibubi.create.foundation.blockEntity.SyncedBlockEntity;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.backport.BlockContainerSingleItem;
import dev.mayaqq.estrogen.registry.EstrogenBlockEntities;
import dev.mayaqq.estrogen.registry.EstrogenTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CookieJarBlockEntity extends SyncedBlockEntity implements BlockContainerSingleItem {
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
        Estrogen.LOGGER.info("Removing cookie...");
        for (int i = this.items.size() - 1; i >= 0; i--) {
            ItemStack stackInSlot = getItem(i);
            if (stackInSlot.isEmpty()) {
                Estrogen.LOGGER.info("Empty slot..");
                continue;
            }
            Estrogen.LOGGER.info("Found slot with item");
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
    public ItemStack getItem(int slot) {
        return this.items.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.items.set(slot, stack);
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
}
