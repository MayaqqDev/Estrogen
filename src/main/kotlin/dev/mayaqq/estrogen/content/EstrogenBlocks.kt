package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.content.blocks.CookieJarBlock
import dev.mayaqq.estrogen.content.blocks.DormantDreamBlock
import dev.mayaqq.estrogen.content.blocks.DreamBlock
import dev.mayaqq.estrogen.content.items.DreamBottleItem
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.CarpetBlock
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.level.material.PushReaction
import uwu.serenity.kritter.api.Registrar
import uwu.serenity.kritter.client.stdlib.renderType
import uwu.serenity.kritter.stdlib.Never
import uwu.serenity.kritter.stdlib.block

@Suppress("unused")
object EstrogenBlocks : Registrar<Block> by Estrogen..Registries.BLOCK {

    // TODO: Creative tabs whenever ashley re-fixes access wideners
    val COOKIE_JAR: CookieJarBlock by block("cookie_jar", ::CookieJarBlock) {
        copyProperties(Blocks::GLASS)
        properties {
            sound(EstrogenSoundTypes.COOKIE_JAR)
        }
        renderType = RenderType::cutout
        item(::BlockItem) {
            standardTooltip()
            //creativeTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, TabPlacement.AFTER(Items.BARREL))
        }
    }

    val DORMANT_DREAM_BLOCK: DormantDreamBlock by block("dormant_dream_block", ::DormantDreamBlock) {
        properties {
            mapColor(MapColor.DIAMOND)
            instrument(NoteBlockInstrument.HAT)
            strength(3.0f)
            noOcclusion()
            requiresCorrectToolForDrops()
            isRedstoneConductor(Never)
            sound(EstrogenSoundTypes.DORMANT_DREAM_BLOCK)
            isValidSpawn(Never.withArgument())
            isSuffocating(Never)
            isViewBlocking(Never)
        }
        renderType = RenderType::translucent
        onRegister {  }
        item(::BlockItem) {
            standardTooltip()
        }
    }

    val DREAM_BLOCK: DreamBlock by block("dream_block", ::DreamBlock) {
        copyProperties(Blocks::END_GATEWAY)
        properties {
            pushReaction(PushReaction.NORMAL)
            isSuffocating(Never)
            sound(EstrogenSoundTypes.DREAM_BLOCK)
            dynamicShape()
        }
        item(::DreamBottleItem, "dream_bottle") {
            properties {
                rarity(Rarity.EPIC)
            }
        }
    }

    val MOTH_WOOL: Block by block("moth_wool", ::Block) {
        copyProperties(Blocks::ORANGE_WOOL)
        item(::BlockItem) {
            //creativeTab(CreativeModeTabs.BUILDING_BLOCKS)
        }
    }

    val QUILTED_MOTH_WOOL: Block by block("quilted_moth_wool", ::Block) {
        copyProperties(Blocks::ORANGE_WOOL)
        item(::BlockItem) {
            //creativeTab(CreativeModeTabs.BUILDING_BLOCKS)
        }
    }

    val MOTH_CARPET: CarpetBlock by block("moth_carpet", ::CarpetBlock) {
        copyProperties(Blocks::ORANGE_CARPET)
        item(::BlockItem) {
            //creativeTab(CreativeModeTabs.BUILDING_BLOCKS)
        }
    }

    val QUILTED_MOTH_CARPET: CarpetBlock by block("quilted_moth_carpet", ::CarpetBlock) {
        copyProperties(Blocks::ORANGE_CARPET)
        item(::BlockItem) {
            //creativeTab(CreativeModeTabs.BUILDING_BLOCKS)
        }
    }

}