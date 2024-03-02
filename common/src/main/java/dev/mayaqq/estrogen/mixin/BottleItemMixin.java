package dev.mayaqq.estrogen.mixin;

import dev.mayaqq.estrogen.platform.CommonHooks;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import dev.mayaqq.estrogen.registry.EstrogenTags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BottleItem.class)
public abstract class BottleItemMixin {

    @Shadow
    protected ItemStack turnBottleIntoItem(ItemStack bottleStack, Player player, ItemStack filledBottleStack) {
        return null;
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void estrogen$horseUrine(Level level, Player player, InteractionHand usedHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        HitResult hit = player.pick(CommonHooks.getReach(player), 1, false);
        if (hit instanceof EntityHitResult hitResult) {
            Entity entity = hitResult.getEntity();
            if (hitResult.getEntity().getType().is(EstrogenTags.Entities.PISS_GIVING)) {
                if (!(entity instanceof Animal animal) || animal.isBaby()) {
                    ItemStack itemStack = player.getItemInHand(usedHand);
                    level.gameEvent(player, GameEvent.FLUID_PICKUP, player.position());
                    if (player instanceof ServerPlayer serverPlayer) {
                        CriteriaTriggers.PLAYER_INTERACTED_WITH_ENTITY.trigger(serverPlayer, itemStack, entity);
                    }
                    cir.setReturnValue(InteractionResultHolder.sidedSuccess(turnBottleIntoItem(itemStack, player, new ItemStack(EstrogenItems.HORSE_URINE_BOTTLE.get())), level.isClientSide()));
                }
            }
        }
    }
}
