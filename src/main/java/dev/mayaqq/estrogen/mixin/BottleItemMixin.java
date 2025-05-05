package dev.mayaqq.estrogen.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.mayaqq.estrogen.content.EstrogenBlocks;
import dev.mayaqq.estrogen.content.EstrogenItems;
import dev.mayaqq.estrogen.content.EstrogenSounds;
import dev.mayaqq.estrogen.content.blocks.DreamBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BottleItem.class)
public class BottleItemMixin {
    @Shadow
    protected ItemStack turnBottleIntoItem(ItemStack bottleStack, Player player, ItemStack filledBottleStack) {
        return null;
    }

    @Inject(method = "use",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"),
        cancellable = true
    )
    public void onUse(Level level, Player player, InteractionHand usedHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir, @Local ItemStack itemStack, @Local BlockPos blockPos) {
        BlockState blockState = level.getBlockState(blockPos);

        if (blockState.is(EstrogenBlocks.INSTANCE.getDREAM_BLOCK()) && DreamBlock.canEntityUse(blockState, player)) {
            level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
            level.playSound(null, blockPos, EstrogenSounds.INSTANCE.getDREAM_BLOCK_BREAK(), SoundSource.BLOCKS, 1.0F, 0.5F);
            cir.setReturnValue(InteractionResultHolder.sidedSuccess(this.turnBottleIntoItem(itemStack, player, EstrogenItems.INSTANCE.getDREAM_BOTTLE().getDefaultInstance()), level.isClientSide()));
        }
    }
}
