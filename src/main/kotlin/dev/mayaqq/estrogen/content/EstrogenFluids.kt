@file:Suppress("UNCHECKED_CAST")

package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.content.blocks.EstrogenLiquidBlock
import dev.mayaqq.estrogen.content.fluids.registry.EstrogenFluidEntry
import dev.mayaqq.estrogen.content.fluids.registry.fluid
import dev.mayaqq.estrogen.id
import earth.terrarium.botarium.common.registry.fluid.BotariumFlowingFluid
import earth.terrarium.botarium.common.registry.fluid.BotariumSourceFluid
import earth.terrarium.botarium.common.registry.fluid.FluidBucketItem
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Items
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.MapColor
import uwu.serenity.kritter.api.Registrar

object EstrogenFluids : Registrar<Fluid> by Estrogen..Registries.FLUID {
    val LiquidEstrogen = fluid("liquid_estrogen", ::BotariumSourceFluid, ::BotariumFlowingFluid) {
            properties {
                still(id("block/liquid_estrogen/liquid_estrogen_still"))
                flowing(id("block/liquid_estrogen/liquid_estrogen_flow"))
                screenOverlay(ResourceLocation("textures/misc/underwater.png"))
                canConvertToSource(false)
                canDrown(true)
                canExtinguish(true)
                canHydrate(true)
                canPushEntity(true)
                canSwim(true)
                viscosity(1500)
                density(1500)
            }
            renderType(RenderType::translucent)
            block(::EstrogenLiquidBlock) {
                copyProperties(Blocks::WATER)
                properties {
                    mapColor(MapColor.COLOR_CYAN)
                }
            }
            bucket(::FluidBucketItem) {
                properties {
                    craftRemainder(Items.BUCKET)
                    stacksTo(1)
                    rarity(Rarity.RARE)
                }
            }
        } as EstrogenFluidEntry<BotariumSourceFluid, BotariumFlowingFluid>
}