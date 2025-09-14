package dev.mayaqq.estrogen.content

import dev.engine_room.flywheel.api.visual.BlockEntityVisual
import dev.engine_room.flywheel.api.visualization.BlockEntityVisualizer
import dev.engine_room.flywheel.api.visualization.VisualizationContext
import dev.engine_room.flywheel.api.visualization.VisualizerRegistry
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer
import dev.mayaqq.cynosure.items.extensions.CustomTooltip
import dev.mayaqq.cynosure.items.extensions.registerExtension
import dev.mayaqq.cynosure.tooltips.DescriptionTooltip
import dev.mayaqq.estrogen.content.blocks.fluid.LavaLikeLiquidBlock
import dev.mayaqq.estrogen.content.fluids.registry.FluidBuilder
import dev.mayaqq.estrogen.id
import dev.mayaqq.estrogen.mcid
import dev.mayaqq.estrogen.mixin.client.accessor.ItemPropertiesAccessor
import earth.terrarium.baubly.Baubly
import earth.terrarium.baubly.client.BaubleRenderer
import earth.terrarium.baubly.client.BaublyClient
import earth.terrarium.baubly.common.Bauble
import earth.terrarium.botarium.common.registry.fluid.BotariumFlowingFluid
import earth.terrarium.botarium.common.registry.fluid.BotariumLiquidBlock
import earth.terrarium.botarium.common.registry.fluid.BotariumSourceFluid
import earth.terrarium.botarium.common.registry.fluid.FluidBucketItem
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.level.pathfinder.BlockPathTypes
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
        it.registerExtension(DescriptionTooltip(DescriptionTooltip.Theme.Default))
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

// Fluids

fun <S : BotariumSourceFluid, F : BotariumFlowingFluid> FluidBuilder<S, F>.lavaLike(mapColor: MapColor, tint: Int) {
    properties {
        still(id("block/blank_lava/blank_lava_still"))
        flowing(id("block/blank_lava/blank_lava_flow"))
        overlay(id("block/blank_lava/blank_lava_flow"))
        screenOverlay(id("textures/misc/underwater.png"))
        tintColor(tint)
        temperature(10000)
        canConvertToSource(false)
        canDrown(false)
        canExtinguish(false)
        canHydrate(false)
        canPushEntity(true)
        canSwim(false)
        lightLevel(15)
        motionScale(0.004)
        pathType(BlockPathTypes.LAVA)
        tickRate(10)
        viscosity(1500)
        density(1500)
    }
    block(::LavaLikeLiquidBlock) {
        copyProperties(Blocks::LAVA)
        properties {
            mapColor(mapColor)
        }
    }
}

fun <S : BotariumSourceFluid, F : BotariumFlowingFluid> FluidBuilder<S, F>.waterLike(mapColor: MapColor, tint: Int) {
    properties {
        still(mcid("block/water_still"))
        flowing(mcid("block/water_flow"))
        overlay(mcid("block/water_flow"))
        screenOverlay(mcid("textures/misc/underwater.png"))
        tintColor(tint)
        canConvertToSource(false)
        canDrown(true)
        canExtinguish(true)
        canHydrate(false)
        canPushEntity(true)
        canSwim(true)
        viscosity(1500)
        density(1500)
    }
    renderType(RenderType::translucent)
    block(::BotariumLiquidBlock) {
        copyProperties(Blocks::WATER)
        properties {
            mapColor(mapColor)
        }
    }
}

fun <S : BotariumSourceFluid, F : BotariumFlowingFluid> FluidBuilder<S, F>.simpleBucket() {
    bucket(::FluidBucketItem) {
        properties {
            craftRemainder(Items.BUCKET)
            stacksTo(1)
        }
    }
}