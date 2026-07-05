@file:JvmName("EstrogenClientFabric")
package dev.mayaqq.estrogen.fabric.client

import dev.mayaqq.estrogen.client.estrogenClient
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.EstrogenItems
import dev.mayaqq.estrogen.content.items.DreamCatcherItem
import dev.mayaqq.estrogen.content.items.ThighHighsItem
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry

fun init() {
    estrogenClient()

    ColorProviderRegistry.ITEM.register(ThighHighsItem::getItemColor, EstrogenItems.ThighHighs.getOrThrow())
    ColorProviderRegistry.ITEM.register(DreamCatcherItem::getItemColor, EstrogenBlocks.DreamCatcher.getOrThrow().asItem())
}