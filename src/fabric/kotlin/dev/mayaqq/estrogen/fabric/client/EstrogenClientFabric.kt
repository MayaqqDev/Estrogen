@file:JvmName("EstrogenClientFabric")
package dev.mayaqq.estrogen.fabric.client

import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.EstrogenItems
import dev.mayaqq.estrogen.content.blocks.DreamCatcherBlock
import dev.mayaqq.estrogen.content.items.ThighHighsItem
import dev.mayaqq.estrogen.utils.EstrogenColors
import invoke.kitty.kritter.utils.color.White
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry

fun init() {
    ColorProviderRegistry.BLOCK.register(
        { state, view, pos, tintIndex ->
            val block = state.block
            if (block is DreamCatcherBlock && view != null && pos != null) {
                block.getColor(view, pos, tintIndex).toInt()
            } else {
                White.toInt()
            }
        },
        EstrogenBlocks.DreamCatcher.get()
    )
    ColorProviderRegistry.BLOCK.register(
        { _, _, _, tintIndex -> if (tintIndex == 0) EstrogenColors.HORSE_URINE.toInt() else White.toInt() },
        EstrogenBlocks.HorseUrineCauldron.get()
    )
    ColorProviderRegistry.BLOCK.register(
        { _, _, _, tintIndex -> if (tintIndex == 0) EstrogenColors.FILTRATED_HORSE_URINE.toInt() else White.toInt() },
        EstrogenBlocks.FiltratedHorseUrineCauldron.get()
    )
    ColorProviderRegistry.ITEM.register(ThighHighsItem::getItemColor, EstrogenItems.ThighHighs.get())
    //KeyBindingHelper.registerKeyBinding(EstrogenKeybinds.DASH_KEY)
}

fun initFabric() {
}
