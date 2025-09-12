package dev.mayaqq.estrogen.content

import dev.mayaqq.cynosure.blocks.model.ModelBedBlock
import dev.mayaqq.cynosure.blocks.poi.add
import dev.mayaqq.cynosure.core.Loader
import dev.mayaqq.cynosure.core.currentLoader
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.content.blocks.*
import dev.mayaqq.estrogen.content.items.DreamBottleItem
import dev.mayaqq.estrogen.content.items.DreamCatcherItem
import dev.mayaqq.estrogen.content.items.ThighHighsItem
import dev.mayaqq.estrogen.utils.EstrogenColors
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.cauldron.CauldronInteraction
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.ItemTags
import net.minecraft.world.entity.ai.village.poi.PoiTypes
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Items
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.CarpetBlock
import net.minecraft.world.level.block.LayeredCauldronBlock
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument
import net.minecraft.world.level.material.MapColor
import uwu.serenity.kritter.api.Registrar
import uwu.serenity.kritter.api.creative.TabPlacement
import uwu.serenity.kritter.client.stdlib.renderType
import uwu.serenity.kritter.stdlib.Never
import uwu.serenity.kritter.stdlib.block

@Suppress("unused")
object EstrogenBlocks : Registrar<Block> by Estrogen..Registries.BLOCK {

    val CookieJar: CookieJarBlock by block("cookie_jar", ::CookieJarBlock) {
        copyProperties(Blocks::GLASS)
        properties {
            sound(EstrogenSoundTypes.COOKIE_JAR)
        }
        renderType = RenderType::cutout
        item(::BlockItem) {
            standardTooltip()
            creativeTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, TabPlacement.AFTER(Items.BARREL))
        }
    }

    @Deprecated("Becoming part of DreamBlock")
    val DormantDreamBlock: DormantDreamBlock by block("dormant_dream_block", ::DormantDreamBlock) {
        properties {
            randomTicks()
        }
    }

    val DreamBlock: DreamBlock by block("dream_block", ::DreamBlock) {
        properties {
            mapColor(MapColor.DIAMOND)
            instrument(NoteBlockInstrument.HAT)
            strength(3.0f)
            noOcclusion()
            dynamicShape()
            requiresCorrectToolForDrops()
            isRedstoneConductor(Never)
            sound(EstrogenSoundTypes.DREAM_BLOCK_DORMANT)
            isValidSpawn(Never.withArgument())
            isSuffocating(Never)
            isViewBlocking(Never)
        }
        renderType = RenderType::translucent
        item(::DreamBottleItem, "dream_bottle") {
            properties {
                rarity(Rarity.EPIC)
            }
            onRegister { EstrogenItems.DreamBottle = it }
        }
        item(::BlockItem, "dormant_dream_block")
    }

    val MothWool: Block by block("moth_wool", ::Block) {
        copyProperties(Blocks::ORANGE_WOOL)
        item(::BlockItem) {
            creativeTab(CreativeModeTabs.BUILDING_BLOCKS, TabPlacement.END)
        }
    }

    val QuiltedMothWool: Block by block("quilted_moth_wool", ::Block) {
        copyProperties(Blocks::ORANGE_WOOL)
        item(::BlockItem) {
            creativeTab(CreativeModeTabs.BUILDING_BLOCKS, TabPlacement.END)
        }
    }

    val MothCarpet: CarpetBlock by block("moth_wool_carpet", ::CarpetBlock) {
        copyProperties(Blocks::ORANGE_CARPET)
        item(::BlockItem) {
            creativeTab(CreativeModeTabs.BUILDING_BLOCKS)
        }
    }

    val QuiltedMothCarpet: CarpetBlock by block("quilted_moth_wool_carpet", ::CarpetBlock) {
        copyProperties(Blocks::ORANGE_CARPET)
        item(::BlockItem) {
            creativeTab(CreativeModeTabs.BUILDING_BLOCKS, TabPlacement.END)
        }
    }

    val EstrogenPillBlock: EstrogenPillBlock by block("estrogen_pill_block", ::EstrogenPillBlock) {
        copyProperties(Blocks::OAK_PLANKS)
        properties {
            strength(1.0f, 1.0f)
            sound(EstrogenSoundTypes.PILL_BOX)
        }
        item(::BlockItem) {
            creativeTab(CreativeModeTabs.BUILDING_BLOCKS, TabPlacement.END)
        }
    }

    val MothBed: ModelBedBlock by block("moth_bed", ::ModelBedBlock) {
        copyProperties(Blocks::ORANGE_BED)
        item(::BlockItem, "moth_bed") {
            properties {
                stacksTo(1)
                creativeTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, TabPlacement.AFTER { stack -> stack.`is`(ItemTags.BEDS) })
            }
        }
        onRegister {
            //TODO: make this work on forge
            if (currentLoader == Loader.FABRIC) PoiTypes.HOME.add(it)
        }
    }

    val QuiltedMothBed: ModelBedBlock by block("quilted_moth_bed", ::ModelBedBlock) {
        copyProperties(Blocks::ORANGE_BED)
        item(::BlockItem, "quilted_moth_bed") {
            properties {
                stacksTo(1)
                creativeTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, TabPlacement.AFTER { stack -> stack.`is`(ItemTags.BEDS) })
            }
        }
        onRegister {
            //TODO: make this work on forge
            if (currentLoader == Loader.FABRIC) PoiTypes.HOME.add(it)
        }
    }

    val ColonThreeBlock: ColonThreeBlock by block("colon_three", ::ColonThreeBlock) {
        copyProperties(Blocks::NETHERITE_BLOCK)
        properties {
            randomTicks()
        }
        item(::BlockItem, "colon_three")
    }

    val DreamCatcher: DreamCatcherBlock by block("dreamcatcher", ::DreamCatcherBlock) {
        properties {
            mapColor(Blocks.OAK_PLANKS.defaultMapColor())
            forceSolidOn()
            instrument(NoteBlockInstrument.BASS)
            noCollission()
            strength(1.0F)
            ignitedByLava()
        }
        renderType = RenderType::cutout
        color(DreamCatcherBlock::getBlockColor)
        item(::DreamCatcherItem) {
            standardTooltip()
            creativeTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, TabPlacement.AFTER(Items.BARREL))
            color(DreamCatcherItem::getItemColor)
            onSetup { CauldronInteraction.WATER[it] = ThighHighsItem.CAULDRON_INTERACTION }
        }
    }

    val HorseUrineCauldron: LayeredCauldronBlock by block(
        "horse_urine_cauldron", { properties -> LayeredCauldronBlock(
            properties,
            {false},
            CauldronInteractions.HORSE_URINE
        ) }) {
        copyProperties(Blocks::CAULDRON)
        renderType = RenderType::cutout
        color { _, _, _, tint -> return@color if (tint == 0) EstrogenColors.HORSE_URINE.toInt() else -1 }
    }

    val FiltratedHorseUrineCauldron: FiltratedHorseUrineCauldron by block(
        "filtrated_horse_urine_cauldron", { properties -> FiltratedHorseUrineCauldron(
            properties,
            CauldronInteractions.FILTRATED_HORSE_URINE
        ) }) {
        copyProperties(Blocks::CAULDRON)
        renderType = RenderType::cutout
        color { _, _, _, tint -> return@color if (tint == 0) EstrogenColors.FILTRATED_HORSE_URINE.toInt() else -1 }
    }

    val LiquidEstrogenCauldron: LayeredCauldronBlock by block(
        "liquid_estrogen_cauldron", { properties -> LayeredCauldronBlock(
            properties,
            {false},
            CauldronInteractions.ESTROGEN
        ) }) {
        copyProperties(Blocks::CAULDRON)
        renderType = RenderType::cutout
    }

    val Memorial: MemorialBlock by block("memorial", ::MemorialBlock) {
        copyProperties(Blocks::REINFORCED_DEEPSLATE)
        properties {}
    }

    // Convert to Create: Estrogen
    val Centrifuge: TransferBlock by block("centrifuge", {p -> TransferBlock(
        p,
        ResourceLocation("createestrogen", "centrifuge")
    )})
}