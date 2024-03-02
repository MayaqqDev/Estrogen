package dev.mayaqq.estrogen.fabric;

import dev.mayaqq.estrogen.registry.EstrogenEffects;
import dev.mayaqq.estrogen.registry.EstrogenEvents;
import dev.mayaqq.estrogen.registry.effects.EstrogenEffect;
import io.github.fabricators_of_create.porting_lib.entity.events.player.PlayerTickEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;

public class EstrogenFabricEvents {
    public static void register() {
        // Fabric Specific Events
        EntityTrackingEvents.START_TRACKING.register(((trackedEntity, player) -> {
            if (trackedEntity instanceof ServerPlayer trackedPlayer) {
                EstrogenEffect.sendPlayerStatusEffect(trackedPlayer, EstrogenEffects.ESTROGEN_EFFECT.get(), player);
            }
        }));
        EntityTrackingEvents.STOP_TRACKING.register(((trackedEntity, player) -> {
            if (trackedEntity instanceof ServerPlayer trackedPlayer) {
                EstrogenEffect.sendPlayerStatusEffect(trackedPlayer, EstrogenEffects.ESTROGEN_EFFECT.get(), player);
            }
        }));

        // Minigame ticking
        PlayerTickEvents.END.register(EstrogenEvents::playerTickEnd);

        // Boob Growing
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            EstrogenEvents.onPlayerJoin(handler.player);
        });
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            EstrogenEvents.onPlayerQuit(handler.player);
        });

        // Urine Collection
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            InteractionResult result = EstrogenEvents.entityInteract(player, entity, player.getItemInHand(hand), world);
            if (result != null) return result;
            return InteractionResult.PASS;
        });
    }
}
