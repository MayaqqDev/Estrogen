package dev.mayaqq.estrogen.registry.common.recipes.common;

import com.simibubi.create.foundation.blockEntity.SyncedBlockEntity;
import com.simibubi.create.foundation.item.SmartInventory;
import dev.mayaqq.estrogen.registry.common.blockEntities.CentrifugeBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class DataInventory extends SmartInventory implements Container {

    private final CentrifugeBlockEntity be;

    public DataInventory(int slots, SyncedBlockEntity be) {
        super(slots, be);
        this.be = (CentrifugeBlockEntity) be;
    }

    public CentrifugeBlockEntity getCentrifuge() {
        return be;
    }

    public int getContainerSize() {
        return 0;
    }

    public boolean isEmpty() {
        return false;
    }

    public ItemStack getItem(int i) {
        return null;
    }

    public ItemStack removeItem(int i, int j) {
        return null;
    }

    public ItemStack removeItemNoUpdate(int i) {
        return null;
    }

    public void setItem(int i, ItemStack itemStack) {

    }

    public void setChanged() {}

    public boolean stillValid(Player player) {
        return false;
    }

    public void clearContent() {}
}