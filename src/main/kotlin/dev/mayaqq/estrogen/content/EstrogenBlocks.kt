package dev.mayaqq.estrogen.content

import dev.mayaqq.cynosure.blocks.model.ModelBedBlock
import dev.mayaqq.cynosure.blocks.poi.add
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.events.registry.VanillaBlockEntityRegistrationEvent
import dev.mayaqq.cynosure.items.extensions.CustomArmPose
import dev.mayaqq.cynosure.items.extensions.CustomTooltip
import dev.mayaqq.cynosure.items.extensions.registerExtension
import dev.mayaqq.cynosure.text.CynosureFonts
import dev.mayaqq.cynosure.text.Text
import dev.mayaqq.cynosure.text.TextStyle.color
import dev.mayaqq.cynosure.text.TextStyle.font
import dev.mayaqq.cynosure.text.TextStyle.underlined
import dev.mayaqq.cynosure.tooltips.CompositeTooltip
import dev.mayaqq.cynosure.tooltips.DescriptionTooltip
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.client.content.blockRenderers.cookieJar.CookieJarArmPose
import dev.mayaqq.estrogen.content.blocks.*
import dev.mayaqq.estrogen.content.items.DreamBottleItem
import dev.mayaqq.estrogen.content.items.DreamCatcherItem
import dev.mayaqq.estrogen.content.items.ThighHighsItem
import dev.mayaqq.estrogen.id
import dev.mayaqq.estrogen.utils.EstrogenColors
import invoke.kitty.kritter.creativeTabs.TabPlacement
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.api.entry.RegistryEntry
import invoke.kitty.kritter.registry.block.BlockRenderType
import invoke.kitty.kritter.registry.block.block
import invoke.kitty.kritter.registry.block.colorProvider
import invoke.kitty.kritter.registry.block.renderType
import invoke.kitty.kritter.registry.item.creativeTab
import invoke.kitty.kritter.registry.item.item
import invoke.kitty.kritter.utils.color.DeepSkyblue
import invoke.kitty.kritter.utils.color.White
import net.minecraft.core.cauldron.CauldronInteraction
import net.minecraft.core.registries.Registries
import net.minecraft.tags.ItemTags
import net.minecraft.world.entity.ai.village.poi.PoiTypes
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Items
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BedBlockEntity
import net.minecraft.world.level.block.state.properties.BedPart
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument
import net.minecraft.world.level.material.MapColor

@Suppress("unused")
object EstrogenBlocks : Registrar<Block> by Registrar(MOD_ID, Registries.BLOCK) {

    val CookieJar: RegistryEntry<CookieJarBlock> = block("cookie_jar", ::CookieJarBlock) {
        initialPropertiesFrom(Blocks::GLASS)
        properties {
            sound(EstrogenSoundTypes.COOKIE_JAR)
        }
        renderType = BlockRenderType.CUTOUT
        item("cookie_jar", ::BlockItem) {
            standardTooltip()
            creativeTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, TabPlacement.AFTER(Items.BARREL))
            onRegister {
                it.registerExtension(CustomArmPose(::CookieJarArmPose))
            }
        }
    }

    val DreamBlock: RegistryEntry<DreamBlock> = block("dream_block", ::DreamBlock) {
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

    val MothWool: RegistryEntry<Block> = block("moth_wool", ::Block) {
        initialPropertiesFrom(Blocks::ORANGE_WOOL)
        item("moth_wool", ::BlockItem)
    }

    val QuiltedMothWool: RegistryEntry<Block> = block("quilted_moth_wool", ::Block) {
        initialPropertiesFrom(Blocks::ORANGE_WOOL)
        item("quilted_moth_wool", ::BlockItem)
    }

    val MothCarpet: RegistryEntry<CarpetBlock> = block("moth_wool_carpet", ::CarpetBlock) {
        initialPropertiesFrom(Blocks::ORANGE_CARPET)
        item("moth_wool_carpet", ::BlockItem)
    }

    val QuiltedMothCarpet: RegistryEntry<CarpetBlock> = block("quilted_moth_wool_carpet", ::CarpetBlock) {
        initialPropertiesFrom(Blocks::ORANGE_CARPET)
        item("quilted_moth_wool_carpet", ::BlockItem)
    }

    val EstrogenPillBlock: RegistryEntry<EstrogenPillBlock> = block("estrogen_pill_block", ::EstrogenPillBlock) {
        initialPropertiesFrom(Blocks::OAK_PLANKS)
        properties {
            strength(1.0f, 1.0f)
            sound(EstrogenSoundTypes.PILL_BOX)
        }
        item("estrogen_pill_block", ::BlockItem)
    }

    val MothBed: RegistryEntry<EstrogenBedBlock> = block("moth_bed", ::EstrogenBedBlock) {
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

    val QuiltedMothBed: RegistryEntry<EstrogenBedBlock> = block("quilted_moth_bed", ::EstrogenBedBlock) {
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

    val ColonThreeBlock: RegistryEntry<ColonThreeBlock> = block("colon_three", ::ColonThreeBlock) {
        initialPropertiesFrom(Blocks::NETHERITE_BLOCK)
        properties {
            randomTicks()
        }
        item("colon_three", ::BlockItem)
    }

    val DreamCatcher: RegistryEntry<DreamCatcherBlock> = block("dreamcatcher", ::DreamCatcherBlock) {
        properties {
            mapColor(Blocks.OAK_PLANKS.defaultMapColor())
            forceSolidOn()
            instrument(NoteBlockInstrument.BASS)
            noCollission()
            strength(1.0F)
            ignitedByLava()
        }
        renderType = BlockRenderType.CUTOUT
        colorProvider(DreamCatcherBlock)
        item("dreamcatcher", ::DreamCatcherItem) {
            tooltip {
                CompositeTooltip(
                    DescriptionTooltip(DescriptionTooltip.Theme.Default),
                    CustomTooltip { stack, player, flag ->
                        add(Text.of("TheIndigenousFoundation.org/articles/dreamcatchers") {
                            color = DeepSkyblue
                            underlined = true
                            font = CynosureFonts.TinyFont
                        })
                    }
                )
            }
            creativeTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, TabPlacement.AFTER(Items.BARREL))
            onSetup { CauldronInteraction.WATER.map()[it] = ThighHighsItem.CAULDRON_INTERACTION }
            textureProperty(id("colored")) { itemStack, clientLevel, livingEntity, i ->
                return@textureProperty if ((itemStack.item as? DreamCatcherItem)?.isBlank(itemStack) == true) 0.0F else 1.0F
            }
        }
    }

    val HorseUrineCauldron: RegistryEntry<LayeredCauldronBlock> = block(
        "horse_urine_cauldron", { properties -> LayeredCauldronBlock(
            Biome.Precipitation.NONE,
            CauldronInteractions.HORSE_URINE,
            properties
        ) }) {
        initialPropertiesFrom(Blocks::CAULDRON)
        renderType = BlockRenderType.CUTOUT
        colorProvider { _, _, _, _, tint -> if (tint == 0) EstrogenColors.HORSE_URINE else White }
    }

    val FiltratedHorseUrineCauldron: RegistryEntry<FiltratedHorseUrineCauldron> = block(
        "filtrated_horse_urine_cauldron", { properties -> FiltratedHorseUrineCauldron(
            properties,
            CauldronInteractions.FILTRATED_HORSE_URINE
        ) }) {
        initialPropertiesFrom(Blocks::CAULDRON)
        renderType = BlockRenderType.CUTOUT
        colorProvider { _, _, _, _, tint -> if (tint == 0) EstrogenColors.FILTRATED_HORSE_URINE else White }
    }

    val LiquidEstrogenCauldron: RegistryEntry<LayeredCauldronBlock> = block(
        "liquid_estrogen_cauldron", { properties -> LayeredCauldronBlock(
            Biome.Precipitation.NONE,
            CauldronInteractions.ESTROGEN,
            properties
        ) }) {
        initialPropertiesFrom(Blocks::CAULDRON)
        renderType = BlockRenderType.CUTOUT
    }

    val Memorial: RegistryEntry<MemorialBlock> = block("memorial", ::MemorialBlock) {
        initialPropertiesFrom(Blocks::REINFORCED_DEEPSLATE)
        properties {}
    }
}