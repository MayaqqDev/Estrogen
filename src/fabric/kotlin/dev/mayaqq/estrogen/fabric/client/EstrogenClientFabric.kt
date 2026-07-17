@file:JvmName("EstrogenClientFabric")
package dev.mayaqq.estrogen.fabric.client

import dev.mayaqq.estrogen.client.content.EstrogenKeybinds
import dev.mayaqq.estrogen.client.estrogenClient
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper

fun init() {
    estrogenClient()
    KeyBindingHelper.registerKeyBinding(EstrogenKeybinds.DASH_KEY)
//    ColorProviderRegistry.ITEM.register(ThighHighsItem::getItemColor, EstrogenItems.ThighHighs.get())
//    ColorProviderRegistry.ITEM.register(DreamCatcherItem::getItemColor, EstrogenBlocks.DreamCatcher.get().asItem())
}

fun initFabric() {
}