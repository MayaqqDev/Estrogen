package dev.mayaqq.estrogen.fabric;

import dev.mayaqq.estrogen.networking.EstrogenStatusEffectSender;
import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingEntityDamageEvents;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;

import static dev.mayaqq.estrogen.registry.common.EstrogenEffects.ESTROGEN_EFFECT;

public class EstrogenFabricEvents {
    // Could replace with mixin in common? Event is called at head and tail, pretty easy impl
    public static void register() {
        EntityTrackingEvents.START_TRACKING.register(((trackedEntity, player) -> {
            if (trackedEntity instanceof ServerPlayer trackedPlayer) {
                new EstrogenStatusEffectSender().sendPlayerStatusEffect(trackedPlayer, ESTROGEN_EFFECT, player);
            }
        }));

        EntityTrackingEvents.STOP_TRACKING.register(((trackedEntity, player) -> {
            if (trackedEntity instanceof ServerPlayer trackedPlayer) {
                new EstrogenStatusEffectSender().sendRemovePlayerStatusEffect(trackedPlayer, ESTROGEN_EFFECT, player);
            }
        }));
        LivingEntityDamageEvents.HURT.register((event) -> {
            if (event.damaged instanceof Player player) {
                if (event.damageSource.is(DamageTypes.FALL) && player.hasEffect(EstrogenEffects.ESTROGEN_EFFECT)) {
                    event.damageAmount /= 1.5f;
                }
            }
        });
    }
}
