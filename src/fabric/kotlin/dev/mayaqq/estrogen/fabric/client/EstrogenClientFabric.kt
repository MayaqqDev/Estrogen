@file:JvmName("EstrogenClientFabric")
package dev.mayaqq.estrogen.fabric.client

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.client.content.EstrogenKeybinds
import dev.mayaqq.estrogen.client.estrogenClient
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.EstrogenItems
import dev.mayaqq.estrogen.content.items.DreamCatcherItem
import dev.mayaqq.estrogen.content.items.ThighHighsItem
import invoke.kitty.kritter.client.events.ClientLifecycleEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.fabricmc.fabric.impl.client.keybinding.KeyBindingRegistryImpl
import net.fabricmc.fabric.mixin.client.keybinding.KeyBindingAccessor
import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft
import net.minecraft.client.Options
import net.minecraft.client.gui.screens.options.OptionsScreen
import net.minecraft.client.particle.SingleQuadParticle

fun init() {
    estrogenClient()

    KeyBindingHelper.registerKeyBinding(EstrogenKeybinds.DASH_KEY)
    ColorProviderRegistry.ITEM.register(ThighHighsItem::getItemColor, EstrogenItems.ThighHighs.get())
    ColorProviderRegistry.ITEM.register(DreamCatcherItem::getItemColor, EstrogenBlocks.DreamCatcher.get().asItem())
}

fun initFabric() {
}