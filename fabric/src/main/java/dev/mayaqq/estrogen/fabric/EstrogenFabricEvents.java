package dev.mayaqq.estrogen.fabric;

import dev.mayaqq.estrogen.client.Dash;
import dev.mayaqq.estrogen.registry.EstrogenEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.PlayerTickEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.Minecraft;
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

            // Might be the wrong place for these, I haven't messed with fabric events ever.
            Dash.uwufy = false;
            Minecraft.getInstance().updateTitle();
        });

        // Entity Interaction Recipe
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (hitResult == null) return InteractionResult.PASS;
            return Objects.requireNonNullElse(EstrogenEvents.entityInteract(player, entity, player.getItemInHand(hand), world), InteractionResult.PASS);
        });
    }
}
