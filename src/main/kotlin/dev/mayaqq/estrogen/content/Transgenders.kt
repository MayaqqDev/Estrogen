package dev.mayaqq.estrogen.content

import dev.engine_room.flywheel.api.visual.BlockEntityVisual
import dev.engine_room.flywheel.api.visualization.BlockEntityVisualizer
import dev.engine_room.flywheel.api.visualization.VisualizationContext
import dev.engine_room.flywheel.api.visualization.VisualizerRegistry
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer
import dev.mayaqq.cynosure.items.extensions.CustomTooltip
import dev.mayaqq.cynosure.items.extensions.registerExtension
import dev.mayaqq.cynosure.tooltips.DescriptionTooltip
import dev.mayaqq.estrogen.mixin.client.accessor.ItemPropertiesAccessor
import earth.terrarium.baubly.Baubly
import earth.terrarium.baubly.client.BaubleRenderer
import earth.terrarium.baubly.client.BaublyClient
import earth.terrarium.baubly.common.Bauble
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import uwu.serenity.kritter.client.stdlib.clientOnly
import uwu.serenity.kritter.stdlib.BlockEntityBuilder
import uwu.serenity.kritter.stdlib.ItemBuilder


// Transgenders is back :>
// Items

inline fun ItemBuilder<*>.tooltip(crossinline tooltip: (Item) -> CustomTooltip) {
    onRegister {
        it.registerExtension(tooltip(it))
    }
}
fun ItemBuilder<*>.standardTooltip() {
    onRegister {
        it.registerExtension(DescriptionTooltip(DescriptionTooltip.Themes.Default))
    }
}

fun <I> ItemBuilder<I>.bauble() where I : Item, I : Bauble {
    onRegister { Baubly.registerBauble(it) }
}

inline fun <I> ItemBuilder<I>.baubleWithRenderer(crossinline renderer: () -> BaubleRenderer) where I : Item, I : Bauble {
    onRegister {
        Baubly.registerBauble(it)
        clientOnly { BaublyClient.registerBaubleRenderer(it, renderer()) }
    }
}

fun <I> ItemBuilder<I>.textureProperty(id: ResourceLocation, consumer: ClampedItemPropertyFunction) where I : Item {
    onRegister {
        clientOnly {
            ItemPropertiesAccessor.register(it, id, consumer)
        }
    }
}

// Block entities
// these need to be inline/crossinline for server-side safety
inline fun <BE : BlockEntity> BlockEntityBuilder<BE>.visual(crossinline factory: (VisualizationContext, BE, Float) -> BlockEntityVisual<in BE>, noinline predicate: (BE) -> Boolean = { true }) {
    clientOnly {
        onSetup {
            val builder = SimpleBlockEntityVisualizer.builder(it)
                .factory { ctx, be, f -> factory(ctx, be, f) }
            predicate?.let { builder.skipVanillaRender(it) } ?: builder.neverSkipVanillaRender()
            builder.apply()
        }
    }
}

inline fun <BE : BlockEntity> BlockEntityBuilder<BE>.visualizaer(crossinline factory: (BlockEntityType<BE>) -> BlockEntityVisualizer<in BE>) {
    clientOnly {
        onSetup {
            VisualizerRegistry.setVisualizer(it, factory(it))
        }
    }
}

// Pois

//TODO: Cynosure fluid api