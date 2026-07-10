package dev.mayaqq.estrogen.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.mayaqq.estrogen.content.EstrogenBlocks;
import dev.mayaqq.estrogen.content.EstrogenSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownEnderpearl.class)
public class ThrownEnderpearlMixin {
    @Inject(
            method = "onHit",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/projectile/ThrownEnderpearl;isRemoved()Z"
            )
    )
    private void onEnderpearlHit(HitResult result, CallbackInfo ci, @Local ServerLevel level) {
        if (result.getType() == HitResult.Type.BLOCK) {
            var blockHit = (BlockHitResult) result;
            BlockState state = level.getBlockState(blockHit.getBlockPos());
            if (state.is(Blocks.TINTED_GLASS)) {
                level.setBlockAndUpdate(blockHit.getBlockPos(), EstrogenBlocks.INSTANCE.getDreamBlock().get().defaultBlockState());
                level.playSound(null, blockHit.getBlockPos(), EstrogenSounds.INSTANCE.getDREAM_BLOCK_DORMANT_PLACE().get(), SoundSource.BLOCKS);
            }
        }
    }
}
