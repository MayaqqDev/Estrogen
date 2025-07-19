@file:EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
package dev.mayaqq.estrogen.forge.client

import dev.mayaqq.cynosure.core.isModLoaded
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.client.estrogenClient
import dev.mayaqq.estrogen.forge.compat.registerPlugin
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraftforge.client.event.RegisterColorHandlersEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

val itemsForColors = hashMapOf<Item, (stack: ItemStack, tint: Int) -> Int>()

@SubscribeEvent
fun onClientInit(event: FMLClientSetupEvent) {
    event.enqueueWork(::estrogenClient)
    if (isModLoaded("roughlyenoughitems")) event.enqueueWork(::registerPlugin)
}

@SubscribeEvent
fun onRegisterItemColors(event: RegisterColorHandlersEvent.Item) {
    itemsForColors.forEach { item, provider ->
        event.itemColors.register(provider, item)
    }
}