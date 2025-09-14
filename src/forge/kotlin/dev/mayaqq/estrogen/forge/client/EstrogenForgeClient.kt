@file:EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
package dev.mayaqq.estrogen.forge.client

import dev.mayaqq.cynosure.core.isModLoaded
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.client.content.screen.EstrogenMenuScreen
import dev.mayaqq.estrogen.client.estrogenClient
import dev.mayaqq.estrogen.forge.compat.registerPlugin
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.core.BlockPos
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.client.ConfigScreenHandler
import net.minecraftforge.client.event.RegisterColorHandlersEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import java.util.function.BiFunction
import java.util.function.Supplier


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