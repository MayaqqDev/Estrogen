@file:Suppress("UNCHECKED_CAST")

package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.content.blocks.fluid.BaseEstrogenLiquidBlock
import dev.mayaqq.estrogen.content.blocks.fluid.EstrogenLiquidBlock
import dev.mayaqq.estrogen.content.fluids.registry.FluidRegistryProvider
import dev.mayaqq.estrogen.content.fluids.registry.fluid
import dev.mayaqq.estrogen.id
import dev.mayaqq.estrogen.mcid
import dev.mayaqq.estrogen.utils.EstrogenColors
import earth.terrarium.botarium.common.registry.fluid.BotariumFlowingFluid
import earth.terrarium.botarium.common.registry.fluid.BotariumSourceFluid
import earth.terrarium.botarium.common.registry.fluid.FluidBucketItem
import earth.terrarium.botarium.common.registry.fluid.FluidRegistry
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.registries.Registries
import net.minecraft.tags.FluidTags
import net.minecraft.world.item.Items
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.level.material.MapColor
import uwu.serenity.kritter.api.Registrar
import uwu.serenity.kritter.stdlib.Always

object EstrogenFluids : Registrar<Fluid> by Estrogen..Registries.FLUID, FluidRegistryProvider {

    override val fluidRegistry = FluidRegistry(MOD_ID)

    val LiquidEstrogen = fluid("liquid_estrogen", ::BotariumSourceFluid, ::BotariumFlowingFluid) {
            properties {
                still(id("block/liquid_estrogen/liquid_estrogen_still"))
                flowing(id("block/liquid_estrogen/liquid_estrogen_flow"))
                screenOverlay(mcid("textures/misc/underwater.png"))
                canConvertToSource(false)
                canDrown(true)
                canExtinguish(true)
                canPushEntity(true)
                canSwim(true)
                viscosity(1500)
                density(1500)
                canHydrate(false)
            }
            renderType(RenderType::translucent)
            block({ data, properties -> EstrogenLiquidBlock(data, properties,
                arrayOf(BaseEstrogenLiquidBlock.FluidInteraction { pos, state, fluidState ->
                    if (fluidState.`is`(Fluids.WATER)) {
                        return@FluidInteraction Blocks.CALCITE.defaultBlockState()
                    }
                    return@FluidInteraction null
                })
            )}) {
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
        }
    val MoltenSlime = fluid("molten_slime", ::BotariumSourceFluid, ::BotariumFlowingFluid) {
        lavaLike(MapColor.COLOR_LIGHT_GREEN, EstrogenColors.MOLTEN_SLIME.toInt())
        simpleBucket()
    }
    val MoltenAmethyst = fluid("molten_amethyst", ::BotariumSourceFluid, ::BotariumFlowingFluid) {
        lavaLike(MapColor.COLOR_PURPLE, EstrogenColors.MOLTEN_AMETHYST.toInt())
        simpleBucket()
    }
    val TestosteroneMixture = fluid("testosterone_mixture", ::BotariumSourceFluid, ::BotariumFlowingFluid) {
        waterLike(MapColor.TERRACOTTA_YELLOW, EstrogenColors.TESTOSTERONE_MIXTURE.toInt())
        simpleBucket()
    }
    val FiltratedHorseUrine = fluid("filtrated_horse_urine", ::BotariumSourceFluid, ::BotariumFlowingFluid) {
        waterLike(MapColor.TERRACOTTA_YELLOW, EstrogenColors.FILTRATED_HORSE_URINE.toInt())
        simpleBucket()
    }
    val HorseUrine = fluid("horse_urine", ::BotariumSourceFluid, ::BotariumFlowingFluid) {
        waterLike(MapColor.COLOR_YELLOW, EstrogenColors.HORSE_URINE.toInt())
        simpleBucket()
    }
    val GenderFluid = fluid("gender_fluid", ::BotariumSourceFluid, ::BotariumFlowingFluid) {
        properties {
            still(id("block/gender_fluid/gender_fluid_still"))
            flowing(id("block/gender_fluid/gender_fluid_flow"))
            overlay(id("block/gender_fluid/gender_fluid_flow"))
            screenOverlay(mcid("textures/misc/underwater.png"))
            canConvertToSource(false)
            canDrown(true)
            canExtinguish(true)
            canHydrate(false)
            canSwim(true)
            viscosity(1500)
            density(1500)
        }
        renderType(RenderType::translucent)
        block({ data, properties -> EstrogenLiquidBlock(data, properties,
            arrayOf(BaseEstrogenLiquidBlock.FluidInteraction { pos, state, fluidState ->
                if (fluidState.`is`(FluidTags.LAVA)) {
                    return@FluidInteraction Blocks.PRISMARINE.defaultBlockState()
                }
                return@FluidInteraction null
            })
        )}) {
            copyProperties(Blocks::WATER)
            properties {
                mapColor(MapColor.COLOR_PURPLE)
                emissiveRendering(Always)
            }
        }
        bucket(::FluidBucketItem) {
            properties {
                craftRemainder(Items.BUCKET)
                stacksTo(1)
                rarity(Rarity.EPIC)
            }
        }
    }
}