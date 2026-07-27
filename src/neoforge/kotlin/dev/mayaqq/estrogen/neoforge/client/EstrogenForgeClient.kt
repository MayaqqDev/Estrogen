package dev.mayaqq.estrogen.neoforge.client

import dev.mayaqq.estrogen.client.content.EstrogenRenderTypes
import dev.mayaqq.estrogen.client.content.entityRenderers.moth.MothModel
import dev.mayaqq.estrogen.client.content.entityRenderers.mothElytra.MothElytraModel
import dev.mayaqq.estrogen.client.content.screen.EstrogenMenuScreen
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.EstrogenItems
import dev.mayaqq.estrogen.content.blocks.DreamCatcherBlock
import dev.mayaqq.estrogen.content.items.ThighHighsItem
import dev.mayaqq.estrogen.utils.EstrogenColors
import invoke.kitty.kritter.platform.Mod
import invoke.kitty.kritter.platform.forge.EntrypointHandler
import invoke.kitty.kritter.platform.forge.eventBus
import invoke.kitty.kritter.platform.forge.modContainer
import invoke.kitty.kritter.utils.color.White
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent
import net.neoforged.neoforge.client.event.EntityRenderersEvent
import net.neoforged.neoforge.client.gui.IConfigScreenFactory

object EstrogenForgeClient {
    @EntrypointHandler("client")
    fun onClientInit(mod: Mod) {
        mod.eventBus!!.addListener(RegisterColorHandlersEvent.Block::class.java) { event ->
            event.register(
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
            event.register(
                { _, _, _, tintIndex -> if (tintIndex == 0) EstrogenColors.HORSE_URINE.toInt() else White.toInt() },
                EstrogenBlocks.HorseUrineCauldron.get()
            )
            event.register(
                { _, _, _, tintIndex -> if (tintIndex == 0) EstrogenColors.FILTRATED_HORSE_URINE.toInt() else White.toInt() },
                EstrogenBlocks.FiltratedHorseUrineCauldron.get()
            )
        }
        mod.eventBus!!.addListener(RegisterColorHandlersEvent.Item::class.java) { event ->
            event.register(ThighHighsItem::getItemColor, EstrogenItems.ThighHighs.get())
        }
        mod.eventBus!!.addListener(EntityRenderersEvent.RegisterLayerDefinitions::class.java) { event ->
            event.registerLayerDefinition(MothModel.LAYER_LOCATION, MothModel::createBodyLayer)
            event.registerLayerDefinition(MothElytraModel.LAYER_LOCATION, MothElytraModel.Companion::createBodyLayer)
        }
        mod.modContainer!!.registerExtensionPoint(
            IConfigScreenFactory::class.java,
            IConfigScreenFactory { _, screen -> EstrogenMenuScreen(screen) }
        )
        EstrogenRenderTypes
    }
}
