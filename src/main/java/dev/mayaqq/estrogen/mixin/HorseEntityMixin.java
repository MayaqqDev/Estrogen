package dev.mayaqq.estrogen.mixin;

import dev.mayaqq.estrogen.registry.common.EstrogenItems;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HorseEntity.class)
public class HorseEntityMixin {
    @Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
    public void estrogen$interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack stack = player.getStackInHand(hand);
        boolean isntBaby = !((HorseEntity) (Object) this).isBaby();
        if (stack.getItem() instanceof GlassBottleItem && isntBaby) {
            stack.decrement(1);
            player.playSound(SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            player.giveItemStack(new ItemStack(EstrogenItems.HORSE_URINE_BOTTLE));
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }
}
