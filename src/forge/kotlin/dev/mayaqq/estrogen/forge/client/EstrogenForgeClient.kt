@file:EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
package dev.mayaqq.estrogen.forge.client

import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.client.estrogenClient
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

@SubscribeEvent
fun onClientInit(event: FMLClientSetupEvent) {
    event.enqueueWork(::estrogenClient)

}