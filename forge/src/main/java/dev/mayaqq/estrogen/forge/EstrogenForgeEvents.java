package dev.mayaqq.estrogen.forge;

import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingFallEvent;
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
}
