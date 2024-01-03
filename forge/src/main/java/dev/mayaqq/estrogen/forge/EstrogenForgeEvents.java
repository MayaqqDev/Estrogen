package dev.mayaqq.estrogen.forge;

import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import dev.mayaqq.estrogen.registry.common.EstrogenItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EstrogenForgeEvents {

    @SubscribeEvent
    private void modifyDamageSource(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.hasEffect(EstrogenEffects.ESTROGEN_EFFECT)) {
                event.setDamageMultiplier(event.getDamageMultiplier() / 1.5f);
            }
        }
    }

    @SubscribeEvent
    private void interactHorse(PlayerInteractEvent.EntityInteractSpecific event) {
        Entity target = event.getTarget();
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        if (target instanceof Horse horse) {
            if (!horse.isBaby()) {
                if (stack.getItem() == Items.GLASS_BOTTLE) {
                    stack.shrink(1);
                    player.playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH, 1.0f, 1.0f);
                    player.addItem(new ItemStack(EstrogenItems.HORSE_URINE_BOTTLE.get()));
                    event.setCancellationResult(InteractionResult.SUCCESS);
                }
            }
        }
    }
}
