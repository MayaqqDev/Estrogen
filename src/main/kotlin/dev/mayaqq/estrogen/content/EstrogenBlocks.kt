package dev.mayaqq.estrogen.content

import dev.mayaqq.cynosure.blocks.model.ModelBedBlock
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.content.blocks.*
import dev.mayaqq.estrogen.content.items.DreamBottleItem
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.registries.Registries
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Items
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.CarpetBlock
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument
import net.minecraft.world.level.material.MapColor
import uwu.serenity.kritter.api.Registrar
import uwu.serenity.kritter.api.creative.TabPlacement
import uwu.serenity.kritter.client.stdlib.renderType
import uwu.serenity.kritter.stdlib.Never
import uwu.serenity.kritter.stdlib.asStack
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
    val DormantDreamBlock: DormantDreamBlock by block("dormant_dream_block", ::DormantDreamBlock)

    val DreamBlock: DreamBlock by block("dream_block", ::DreamBlock) {
        properties {
            mapColor(MapColor.DIAMOND)
            instrument(NoteBlockInstrument.HAT)
            strength(3.0f)
            noOcclusion()
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
            creativeTab(CreativeModeTabs.BUILDING_BLOCKS)
        }
    }

    val QuiltedMothWool: Block by block("quilted_moth_wool", ::Block) {
        copyProperties(Blocks::ORANGE_WOOL)
        item(::BlockItem) {
            creativeTab(CreativeModeTabs.BUILDING_BLOCKS)
        }
    }

    val MothCarpet: CarpetBlock by block("moth_carpet", ::CarpetBlock) {
        copyProperties(Blocks::ORANGE_CARPET)
        item(::BlockItem) {
            creativeTab(CreativeModeTabs.BUILDING_BLOCKS)
        }
    }

    val QuiltedMothCarpet: CarpetBlock by block("quilted_moth_carpet", ::CarpetBlock) {
        copyProperties(Blocks::ORANGE_CARPET)
        item(::BlockItem) {
            creativeTab(CreativeModeTabs.BUILDING_BLOCKS)
        }
    }

    val EstrogenPillBlock: EstrogenPillBlock by block("estrogen_pill_block", ::EstrogenPillBlock) {
        copyProperties(Blocks::OAK_PLANKS)
        properties {
            strength(1.0f, 1.0f)
            sound(EstrogenSoundTypes.PILL_BOX)
        }
        item(::BlockItem) {
            creativeTab(CreativeModeTabs.BUILDING_BLOCKS)
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
    }

    val QuiltedMothBed: ModelBedBlock by block("quilted_moth_bed", ::ModelBedBlock) {
        copyProperties(Blocks::ORANGE_BED)
        item(::BlockItem, "quilted_moth_bed") {
            properties {
                stacksTo(1)
                creativeTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, TabPlacement.AFTER(::MothBed::asStack))
            }
        }
    }
}