package dev.mayaqq.estrogen.registry.blockEntities;

import dev.mayaqq.estrogen.backport.BlockContainerSingleItem;
import dev.mayaqq.estrogen.registry.EstrogenBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;


public class CookieJarBlockEntity extends BlockEntity implements BlockContainerSingleItem {
    private ItemStack item;

    public CookieJarBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(EstrogenBlockEntities.COOKIE_JAR.get(), blockPos, blockState);
        this.item = ItemStack.EMPTY;
    }

    public ItemStack getTheItem() {
        return this.item;
    }

    public void setTheItem(ItemStack itemStack) {
        this.item = itemStack;
    }

    public BlockEntity getContainerBlockEntity() {
        return this;
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.item;
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.item = stack;
    }
}
