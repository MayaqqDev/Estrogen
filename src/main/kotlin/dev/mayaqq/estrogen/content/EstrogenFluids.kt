@file:Suppress("UNCHECKED_CAST")

package dev.mayaqq.estrogen.content

import com.teamresourceful.resourcefullib.client.fluid.registry.ResourcefulClientFluidRegistry
import com.teamresourceful.resourcefullib.common.fluid.ResourcefulBucketItem
import com.teamresourceful.resourcefullib.common.fluid.ResourcefulFlowingFluid
import com.teamresourceful.resourcefullib.common.fluid.registry.ResourcefulFluidRegistry
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistryType
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.content.blocks.fluid.BaseEstrogenLiquidBlock
import dev.mayaqq.estrogen.content.blocks.fluid.EstrogenLiquidBlock
import dev.mayaqq.estrogen.content.fluids.registry.FluidRegistryProvider
import dev.mayaqq.estrogen.content.fluids.registry.fluid
import dev.mayaqq.estrogen.id
import dev.mayaqq.estrogen.mcid
import dev.mayaqq.estrogen.utils.EstrogenColors
import invoke.kitty.kritter.registry.api.Registrar
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.registries.Registries
import net.minecraft.tags.FluidTags
import net.minecraft.world.item.Items
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.level.material.MapColor

@Suppress("UnstableApiUsage")
object EstrogenFluids : Registrar<Fluid> by Registrar(MOD_ID, Registries.FLUID), FluidRegistryProvider {

    override val fluidRegistry: ResourcefulFluidRegistry = ResourcefulRegistries.create(ResourcefulRegistryType.FLUID, MOD_ID)
    override val clientFluidRegistry: ResourcefulClientFluidRegistry = ResourcefulClientFluidRegistry(MOD_ID)

    val LiquidEstrogen = fluid("liquid_estrogen", ResourcefulFlowingFluid::Still, ResourcefulFlowingFluid::Flowing) {
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
            renderType { RenderType.translucent() }
            block({ data, properties -> EstrogenLiquidBlock(data, properties,
                arrayOf(BaseEstrogenLiquidBlock.FluidInteraction { pos, state, fluidState ->
                    if (fluidState.`is`(Fluids.WATER)) {
                        return@FluidInteraction Blocks.CALCITE.defaultBlockState()
                    }
                    return@FluidInteraction null
                })
            )}) {
                initialPropertiesFrom(Blocks::WATER)
                properties {
                    mapColor(MapColor.COLOR_CYAN)
                }
            }
            bucket(::ResourcefulBucketItem) {
                properties {
                    craftRemainder(Items.BUCKET)
                    stacksTo(1)
                    rarity(Rarity.RARE)
                }
            }
        }
    val MoltenSlime = fluid("molten_slime", ResourcefulFlowingFluid::Still, ResourcefulFlowingFluid::Flowing) {
        lavaLike(MapColor.COLOR_LIGHT_GREEN, EstrogenColors.MOLTEN_SLIME.toInt())
        simpleBucket()
    }
    val MoltenAmethyst = fluid("molten_amethyst", ResourcefulFlowingFluid::Still, ResourcefulFlowingFluid::Flowing) {
        lavaLike(MapColor.COLOR_PURPLE, EstrogenColors.MOLTEN_AMETHYST.toInt())
        simpleBucket()
    }
    val TestosteroneMixture = fluid("testosterone_mixture", ResourcefulFlowingFluid::Still, ResourcefulFlowingFluid::Flowing) {
        waterLike(MapColor.TERRACOTTA_YELLOW, EstrogenColors.TESTOSTERONE_MIXTURE.toInt())
        simpleBucket()
    }
    val FiltratedHorseUrine = fluid("filtrated_horse_urine", ResourcefulFlowingFluid::Still, ResourcefulFlowingFluid::Flowing) {
        waterLike(MapColor.TERRACOTTA_YELLOW, EstrogenColors.FILTRATED_HORSE_URINE.toInt())
        simpleBucket()
    }
    val HorseUrine = fluid("horse_urine", ResourcefulFlowingFluid::Still, ResourcefulFlowingFluid::Flowing) {
        waterLike(MapColor.COLOR_YELLOW, EstrogenColors.HORSE_URINE.toInt())
        simpleBucket()
    }
    val GenderFluid = fluid("gender_fluid", ResourcefulFlowingFluid::Still, ResourcefulFlowingFluid::Flowing) {
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
        renderType { RenderType.translucent() }
        block({ data, properties -> EstrogenLiquidBlock(data, properties,
            arrayOf(BaseEstrogenLiquidBlock.FluidInteraction { pos, state, fluidState ->
                if (fluidState.`is`(FluidTags.LAVA)) {
                    return@FluidInteraction Blocks.PRISMARINE.defaultBlockState()
                }
                return@FluidInteraction null
            })
        )}) {
            initialPropertiesFrom(Blocks::WATER)
            properties {
                mapColor(MapColor.COLOR_PURPLE)
                emissiveRendering(Always)
            }
        }
        bucket(::ResourcefulBucketItem) {
            properties {
                craftRemainder(Items.BUCKET)
                stacksTo(1)
                rarity(Rarity.EPIC)
            }
        }
    }
}