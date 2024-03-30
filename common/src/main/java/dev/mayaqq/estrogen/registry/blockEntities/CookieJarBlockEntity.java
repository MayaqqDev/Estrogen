package dev.mayaqq.estrogen.registry.blockEntities;

import dev.mayaqq.estrogen.backport.BlockContainerSingleItem;
import dev.mayaqq.estrogen.registry.EstrogenBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.concurrent.atomic.AtomicInteger;

public class CookieJarBlockEntity extends BlockEntity implements BlockContainerSingleItem {
    private final NonNullList<ItemStack> items = NonNullList.withSize(8, ItemStack.EMPTY);

    public CookieJarBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(EstrogenBlockEntities.COOKIE_JAR.get(), blockPos, blockState);
    }

    public boolean canRemoveCookie() {
        return getCookieCount() > 0;
    }

    public boolean canAddCookie() {
        return getCookieCount() < 512;
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
    public void addCookie() {
        this.addStack(new ItemStack(Items.COOKIE, 1));
    }

    public int getCookieCount() {
        AtomicInteger count = new AtomicInteger();
        this.items.iterator().forEachRemaining(
                stack -> count.addAndGet(stack.getCount())
        );
        return count.get();
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
}
