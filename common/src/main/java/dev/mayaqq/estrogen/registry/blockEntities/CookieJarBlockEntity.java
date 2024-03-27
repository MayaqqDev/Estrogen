package dev.mayaqq.estrogen.registry.blockEntities;

import dev.mayaqq.estrogen.backport.BlockContainerSingleItem;
import dev.mayaqq.estrogen.registry.EstrogenBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;


public class CookieJarBlockEntity extends BlockEntity implements BlockContainerSingleItem {
    private final NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

    public CookieJarBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(EstrogenBlockEntities.COOKIE_JAR.get(), blockPos, blockState);
    }

    public ItemStack getTheItem() {
        return this.items.get(0);
    }

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
