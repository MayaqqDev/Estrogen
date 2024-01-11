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
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EstrogenForgeEvents {

    @SubscribeEvent
    public void modifyDamageSource(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.hasEffect(EstrogenEffects.ESTROGEN_EFFECT)) {
                event.setDamageMultiplier(event.getDamageMultiplier() / 1.5f);
            }
        }
    }
}
