package dev.mayaqq.estrogen.content

import dev.mayaqq.cynosure.blocks.model.ModelBedBlock
import dev.mayaqq.cynosure.blocks.poi.add
import dev.mayaqq.cynosure.core.identifier
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.content.blocks.*
import dev.mayaqq.estrogen.content.items.DreamBottleItem
import dev.mayaqq.estrogen.content.items.DreamCatcherItem
import dev.mayaqq.estrogen.content.items.ThighHighsItem
import dev.mayaqq.estrogen.id
import dev.mayaqq.estrogen.utils.EstrogenColors
import invoke.kitty.kritter.creativeTabs.TabPlacement
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.block.BlockRenderType
import invoke.kitty.kritter.registry.block.block
import invoke.kitty.kritter.registry.block.colorProvider
import invoke.kitty.kritter.registry.block.renderType
import invoke.kitty.kritter.registry.creativeTab.creativeTab
import invoke.kitty.kritter.registry.item.creativeTab
import invoke.kitty.kritter.registry.item.item
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
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.properties.BedPart
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument
import net.minecraft.world.level.material.MapColor

@Suppress("unused")
object EstrogenBlocks : Registrar<Block> by Registrar(MOD_ID, Registries.BLOCK) {

    val CookieJar: CookieJarBlock by block("cookie_jar", ::CookieJarBlock) {
        initialPropertiesFrom(Blocks::GLASS)
        properties {
            sound(EstrogenSoundTypes.COOKIE_JAR)
        }
        renderType = BlockRenderType.CUTOUT
        item("cookie_jar", ::BlockItem) {
            standardTooltip()
            creativeTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, TabPlacement.AFTER(Items.BARREL))
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
            //isViewBlocking { state, block, pos -> state.getValue(PERSISTENT) }
        }
        renderType = BlockRenderType.TRANSLUCENT
        item("dream_bottle", ::DreamBottleItem) {
            properties {
                rarity(Rarity.EPIC)
            }
            onRegister { EstrogenItems.DreamBottle = it }
        }
        item("dormant_dream_block", ::BlockItem)
    }

    val MothWool: Block by block("moth_wool", ::Block) {
        initialPropertiesFrom(Blocks::ORANGE_WOOL)
        item("moth_wool", ::BlockItem)
    }

    val QuiltedMothWool: Block by block("quilted_moth_wool", ::Block) {
        initialPropertiesFrom(Blocks::ORANGE_WOOL)
        item("quilted_moth_wool", ::BlockItem)
    }

    val MothCarpet: CarpetBlock by block("moth_wool_carpet", ::CarpetBlock) {
        initialPropertiesFrom(Blocks::ORANGE_CARPET)
        item("moth_wool_carpet", ::BlockItem)
    }

    val QuiltedMothCarpet: CarpetBlock by block("quilted_moth_wool_carpet", ::CarpetBlock) {
        initialPropertiesFrom(Blocks::ORANGE_CARPET)
        item("quilted_moth_wool_carpet", ::BlockItem)
    }

    val EstrogenPillBlock: EstrogenPillBlock by block("estrogen_pill_block", ::EstrogenPillBlock) {
        initialPropertiesFrom(Blocks::OAK_PLANKS)
        properties {
            strength(1.0f, 1.0f)
            sound(EstrogenSoundTypes.PILL_BOX)
        }
        item("estrogen_pill_block", ::BlockItem)
    }

    val MothBed: ModelBedBlock by block("moth_bed", ::ModelBedBlock) {
        initialPropertiesFrom(Blocks::ORANGE_BED)
        item("moth_bed", ::BlockItem) {
            properties {
                stacksTo(1)
                creativeTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, TabPlacement.AFTER { stack -> stack.`is`(ItemTags.BEDS) })
            }
        }
        onRegister { bed ->
            PoiTypes.HOME.add(bed.stateDefinition.possibleStates.filter { it.getValue(BedBlock.PART) == BedPart.HEAD }.toMutableSet())
        }
    }

    val QuiltedMothBed: ModelBedBlock by block("quilted_moth_bed", ::ModelBedBlock) {
        initialPropertiesFrom(Blocks::ORANGE_BED)
        item("quilted_moth_bed", ::BlockItem) {
            properties {
                stacksTo(1)
                creativeTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, TabPlacement.AFTER { stack -> stack.`is`(ItemTags.BEDS) })
            }
        }
        onRegister { bed ->
            PoiTypes.HOME.add(bed.stateDefinition.possibleStates.filter { it.getValue(BedBlock.PART) == BedPart.HEAD }.toMutableSet())
        }
    }

    val ColonThreeBlock: ColonThreeBlock by block("colon_three", ::ColonThreeBlock) {
        initialPropertiesFrom(Blocks::NETHERITE_BLOCK)
        properties {
            randomTicks()
        }
        item("colon_three", ::BlockItem)
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
        renderType = BlockRenderType.CUTOUT
        colorProvider = DreamCatcherBlock
        item("dreamcatcher", ::DreamCatcherItem) {
            standardTooltip()
            creativeTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, TabPlacement.AFTER(Items.BARREL))
            onSetup { CauldronInteraction.WATER.map()[it] = ThighHighsItem.CAULDRON_INTERACTION }
            textureProperty(id("colored")) { itemStack, clientLevel, livingEntity, i ->
                return@textureProperty if ((itemStack.item as? DreamCatcherItem)?.isBlank(itemStack) == true) 0.0F else 1.0F
            }
        }
    }

    val HorseUrineCauldron: LayeredCauldronBlock by block(
        "horse_urine_cauldron", { properties -> LayeredCauldronBlock(
            Biome.Precipitation.NONE,
            CauldronInteractions.HORSE_URINE,
            properties
        ) }) {
        initialPropertiesFrom(Blocks::CAULDRON)
        renderType = BlockRenderType.CUTOUT
        colorProvider { _, _, _, tint -> return@colorProvider if (tint == 0) EstrogenColors.HORSE_URINE.toInt() else -1 }
    }

    val FiltratedHorseUrineCauldron: FiltratedHorseUrineCauldron by block(
        "filtrated_horse_urine_cauldron", { properties -> FiltratedHorseUrineCauldron(
            properties,
            CauldronInteractions.FILTRATED_HORSE_URINE
        ) }) {
        initialPropertiesFrom(Blocks::CAULDRON)
        renderType = BlockRenderType.CUTOUT
        colorProvider { _, _, _, tint -> return@color if (tint == 0) EstrogenColors.FILTRATED_HORSE_URINE.toInt() else -1 }
    }

    val LiquidEstrogenCauldron: LayeredCauldronBlock by block(
        "liquid_estrogen_cauldron", { properties -> LayeredCauldronBlock(
            properties,
            {false},
            CauldronInteractions.ESTROGEN
        ) }) {
        initialPropertiesFrom(Blocks::CAULDRON)
        renderType = BlockRenderType.CUTOUT
    }

    val Memorial: MemorialBlock by block("memorial", ::MemorialBlock) {
        initialPropertiesFrom(Blocks::REINFORCED_DEEPSLATE)
        properties {}
    }
}