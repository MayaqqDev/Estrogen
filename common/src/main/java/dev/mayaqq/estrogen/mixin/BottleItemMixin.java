package dev.mayaqq.estrogen.mixin;

import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import dev.mayaqq.estrogen.registry.EstrogenSounds;
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
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(BottleItem.class)
public class BottleItemMixin {
    @Shadow
    protected ItemStack turnBottleIntoItem(ItemStack bottleStack, Player player, ItemStack filledBottleStack) {
        return null;
    }

    @Inject(method = "use",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD)
    public void onUse(Level level, Player player, InteractionHand usedHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir, List list, ItemStack itemStack, BlockHitResult blockHitResult, BlockPos blockPos) {
        BlockState blockState = level.getBlockState(blockPos);

        if (blockState.is(EstrogenBlocks.DREAM_BLOCK.get())) {
            level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
            level.playSound(null, blockPos, EstrogenSounds.DREAM_BLOCK_PLACE.get(), SoundSource.BLOCKS, 1.0F, 0.5F);
            cir.setReturnValue(InteractionResultHolder.sidedSuccess(this.turnBottleIntoItem(itemStack, player, EstrogenItems.DREAM_BOTTLE.get().getDefaultInstance()), level.isClientSide()));
        }
    }
}
