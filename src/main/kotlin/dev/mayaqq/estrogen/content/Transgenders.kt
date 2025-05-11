package dev.mayaqq.estrogen.content

import dev.engine_room.flywheel.api.visual.BlockEntityVisual
import dev.engine_room.flywheel.api.visualization.BlockEntityVisualizer
import dev.engine_room.flywheel.api.visualization.VisualizationContext
import dev.engine_room.flywheel.api.visualization.VisualizationManager
import dev.engine_room.flywheel.api.visualization.VisualizerRegistry
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer
import dev.engine_room.flywheel.lib.visualization.VisualizationHelper
import dev.mayaqq.cynosure.items.extensions.CustomTooltip
import dev.mayaqq.cynosure.items.extensions.registerExtension
import dev.mayaqq.cynosure.tooltips.DescriptionTooltip
import earth.terrarium.baubly.Baubly
import earth.terrarium.baubly.client.BaubleRenderer
import earth.terrarium.baubly.client.BaublyClient
import earth.terrarium.baubly.common.Bauble
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.damagesource.DamageType
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import org.spongepowered.asm.mixin.injection.Desc
import uwu.serenity.kritter.client.stdlib.clientOnly
import uwu.serenity.kritter.stdlib.BlockBuilder
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

//TODO: Cynosure fluid api