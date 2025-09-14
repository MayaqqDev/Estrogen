@file:EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
package dev.mayaqq.estrogen.forge.client

import dev.mayaqq.cynosure.core.isModLoaded
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.client.content.screen.EstrogenMenuScreen
import dev.mayaqq.estrogen.client.estrogenClient
import dev.mayaqq.estrogen.forge.compat.registerPlugin
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraftforge.client.ConfigScreenHandler
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent


@SubscribeEvent
fun onClientInit(event: FMLClientSetupEvent) {
    event.enqueueWork(::estrogenClient)
    if (isModLoaded("roughlyenoughitems")) event.enqueueWork(::registerPlugin)

    @Suppress("Deprecation", "Removal")
    ModLoadingContext.get().activeContainer.registerExtensionPoint(
        ConfigScreenHandler.ConfigScreenFactory::class.java
    ) {
        ConfigScreenHandler.ConfigScreenFactory { minecraft: Minecraft, screen: Screen ->
            EstrogenMenuScreen(screen)
        }
    }
}