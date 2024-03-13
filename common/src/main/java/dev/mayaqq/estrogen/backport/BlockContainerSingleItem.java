package dev.mayaqq.estrogen.backport;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.ticks.ContainerSingleItem;

public interface BlockContainerSingleItem extends ContainerSingleItem {
    BlockEntity getContainerBlockEntity();

    default boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this.getContainerBlockEntity(), player);
    }
}
