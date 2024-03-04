package dev.mayaqq.estrogen.registry.recipes.inventory;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class EntityInteractionInventory extends Inventory {

    public final EntityType<?> entity;
    public final ItemStack stack;

    public EntityInteractionInventory(Player player, EntityType<?> entity, ItemStack stack) {
        super(player);
        this.entity = entity;
        this.stack = stack;
    }
}
