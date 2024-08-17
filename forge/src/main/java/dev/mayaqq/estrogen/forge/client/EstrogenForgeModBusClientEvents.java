package dev.mayaqq.estrogen.forge.client;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.config.ConfigSync;
import dev.mayaqq.estrogen.client.registry.EstrogenClientEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = Estrogen.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class EstrogenForgeModBusClientEvents {
    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        ConfigSync.onLoad(event.getConfig());
    }

    @SubscribeEvent
    public static void onReload(ModConfigEvent.Reloading event) {
        ConfigSync.onReload(event.getConfig());
    }

    @SubscribeEvent
    public static void registerModelLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        EstrogenClientEvents.registerModelLayer(event::registerLayerDefinition);
    }

    @SubscribeEvent
    public static void registerColorHandlersItem(RegisterColorHandlersEvent.Item event) {
        EstrogenClientEvents.registerItemColorProviders(event::register);
    }
}
