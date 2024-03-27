package dev.mayaqq.estrogen.registry.blockEntities;

import dev.mayaqq.estrogen.Estrogen;
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


/* TODO: Change to Container instead of BlockContainerSingleItem
 *  Should allow easier implementation of cookie jar
 */
public class CookieJarBlockEntity extends BlockEntity implements BlockContainerSingleItem {
    private final NonNullList<ItemStack> items = NonNullList.withSize(8, ItemStack.EMPTY);

    public CookieJarBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(EstrogenBlockEntities.COOKIE_JAR.get(), blockPos, blockState);
    }

    @Deprecated
    public ItemStack getAllCookies() {
        return this.items.get(0);
    }

    public boolean canAddStack(ItemStack stack) {
        return stack.is(Items.COOKIE) && getCookieCount() + stack.getCount() > 512;
    }

    public boolean canRemoveCookie() {
        return getCookieCount() > 0;
    }

    public boolean canAddCookie() {
        return getCookieCount() < 512;
    }

    /**
     * Don't call without calling canAddStack
     */
    public void addStack(ItemStack stack) {
        AtomicInteger toAdd = new AtomicInteger(stack.getCount());
        Estrogen.LOGGER.info(toAdd.toString());
        this.items.iterator().forEachRemaining(
             item -> {
                 int canAdd = item.getMaxStackSize() - item.getCount();
                 if (canAdd >= toAdd.get()) {
                     item.setCount(item.getCount() + toAdd.get());
                 } else {
                     item.setCount(item.getCount() + canAdd);
                     toAdd.addAndGet(-canAdd);
                 }
             }
        );
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
            setItem(i, stackInSlot.copyWithCount(stackInSlot.getCount() - 1));
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

    @Deprecated
    public void setTheItem(ItemStack itemStack) {
        this.items.set(0, itemStack);
    }

    public BlockEntity getContainerBlockEntity() {
        return this;
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.items.get(0);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.items.set(0, stack);
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
