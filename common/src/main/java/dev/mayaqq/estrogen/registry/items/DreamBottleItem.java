package dev.mayaqq.estrogen.registry.items;

import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.EstrogenSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class DreamBottleItem extends ItemNameBlockItem {
    public DreamBottleItem(Properties properties) {
        super(EstrogenBlocks.DREAM_BLOCK.get(), properties);
    }

    @Override
    public SoundEvent getPlaceSound(BlockState state) {
        return EstrogenSounds.DREAM_BLOCK_PLACE.get();
    }

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        InteractionResult result = super.place(context);
        if (result == InteractionResult.SUCCESS) {
            context.getPlayer().getInventory().placeItemBackInInventory(Items.GLASS_BOTTLE.getDefaultInstance());
        }
        return result;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        InteractionResult actionResult = super.useOn(context);
        if (!level.isClientSide && actionResult.consumesAction() && context.getPlayer() != null && !context.getPlayer().isCreative()) {
            context.getPlayer().getInventory().placeItemBackInInventory(Items.GLASS_BOTTLE.getDefaultInstance());
        }
        return actionResult;
    }
}