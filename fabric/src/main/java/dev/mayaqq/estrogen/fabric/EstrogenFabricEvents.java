package dev.mayaqq.estrogen.fabric;

import dev.mayaqq.estrogen.networking.EstrogenStatusEffectSender;
import dev.mayaqq.estrogen.registry.EstrogenCreativeTab;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import dev.mayaqq.estrogen.registry.items.EstrogenPatchesItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class EstrogenFabricEvents {
    // Could replace with mixin in common? Event is called at head and tail, pretty easy impl
    public static void register() {
        EntityTrackingEvents.START_TRACKING.register(((trackedEntity, player) -> {
            if (trackedEntity instanceof ServerPlayer trackedPlayer) {
                new EstrogenStatusEffectSender().sendPlayerStatusEffect(trackedPlayer, EstrogenEffects.ESTROGEN_EFFECT, player);
            }
        }));

        EntityTrackingEvents.STOP_TRACKING.register(((trackedEntity, player) -> {
            if (trackedEntity instanceof ServerPlayer trackedPlayer) {
                new EstrogenStatusEffectSender().sendRemovePlayerStatusEffect(trackedPlayer, EstrogenEffects.ESTROGEN_EFFECT, player);
            }
        }));

        ItemGroupEvents.modifyEntriesEvent(EstrogenCreativeTab.ESTROGEN_TAB.getKey()).register(content -> {
            for (ItemStack stack : EstrogenItems.creativeTabItems())
                content.accept(stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        });
    }
}
