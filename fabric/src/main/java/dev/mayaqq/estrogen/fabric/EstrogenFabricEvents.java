package dev.mayaqq.estrogen.fabric;

import dev.mayaqq.estrogen.registry.EstrogenEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.PlayerTickEvents;
import io.github.fabricators_of_create.porting_lib.event.common.AdvancementCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.world.InteractionResult;

import java.util.Objects;

public class EstrogenFabricEvents {
    public static void register() {
        // Player Tracking
        EntityTrackingEvents.START_TRACKING.register((EstrogenEvents::playerTracking));
        EntityTrackingEvents.STOP_TRACKING.register((EstrogenEvents::playerTracking));

        // Minigame ticking
        PlayerTickEvents.END.register(EstrogenEvents::playerTickEnd);

        // Boob Growing
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
                EstrogenEvents.onPlayerJoin(handler.player));
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            EstrogenEvents.onPlayerQuit(handler.player);
        });

        // Entity Interaction Recipe
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (hitResult == null) return InteractionResult.PASS;
            return Objects.requireNonNullElse(EstrogenEvents.entityInteract(player, entity, player.getItemInHand(hand), world), InteractionResult.PASS);
        });

        // Entity Death
        ServerLivingEntityEvents.AFTER_DEATH.register(EstrogenEvents::onEntityDeath);

        AdvancementCallback.EVENT.register(EstrogenEvents::onAdvancement);
    }
}
