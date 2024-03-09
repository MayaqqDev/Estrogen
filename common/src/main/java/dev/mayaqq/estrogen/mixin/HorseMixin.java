package dev.mayaqq.estrogen.mixin;

import dev.mayaqq.estrogen.registry.common.EstrogenItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Horse.class)
public class HorseMixin {
    // Makes it so that you can milk horses with bottles to get their urine.
    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void estrogen$mobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack stack = player.getItemInHand(hand);
        Horse horse = (Horse) (Object) this;
        if (stack.getItem() instanceof BottleItem && !horse.isBaby()) {
            stack.shrink(1);
            player.playSound(SoundEvents.BOTTLE_FILL, 1, 1);
            player.getInventory().placeItemBackInInventory(new ItemStack(EstrogenItems.HORSE_URINE_BOTTLE.get()));
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
