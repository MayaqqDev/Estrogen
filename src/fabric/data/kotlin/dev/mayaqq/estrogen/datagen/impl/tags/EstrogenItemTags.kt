package dev.mayaqq.estrogen.datagen.impl.tags

import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.EstrogenFluids
import dev.mayaqq.estrogen.content.EstrogenItems
import dev.mayaqq.estrogen.content.EstrogenTags
import dev.mayaqq.estrogen.datagen.api.platform.PlatformHelper
import dev.mayaqq.estrogen.datagen.api.tags.BaseTagProvider
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.minecraft.core.HolderLookup
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Items
import java.util.concurrent.CompletableFuture

class EstrogenItemTags(
    data: FabricDataOutput,
    completableFeature: CompletableFuture<HolderLookup.Provider>,
    helper: PlatformHelper
) : BaseTagProvider.ItemProvider(data, completableFeature, helper) {
    override fun addTags(provider: HolderLookup.Provider) {
        getOrCreateTagBuilder(EstrogenTags.Items.THIGHS)
            .add(EstrogenItems.EstrogenPatches)
            .add(EstrogenItems.ThighHighs)
        getOrCreateTagBuilder(EstrogenTags.Items.UWUFYING)
            .add(EstrogenItems.ColonThree)
        getOrCreateTagBuilder(EstrogenTags.Items.CURIOS_THIGHS)
            .add(EstrogenItems.EstrogenPatches)
            .add(EstrogenItems.ThighHighs)
        getOrCreateTagBuilder(EstrogenTags.Items.MUSIC_DISCS)
            .add(EstrogenItems.EstrogenChipCookie)
        getOrCreateTagBuilder(EstrogenTags.Items.LAVA_BUCKETS)
            .add(EstrogenFluids.MoltenSlime.bucket)
            .add(EstrogenFluids.MoltenAmethyst.bucket)
        getOrCreateTagBuilder(EstrogenTags.Items.COOKIES)
            .add(EstrogenItems.EstrogenChipCookie)
            .add(Items.COOKIE)
        getOrCreateTagBuilder(EstrogenTags.Items.LEATHER_ITEMS)
            .add(Items.LEATHER)
            .add(Items.LEATHER_BOOTS)
            .add(Items.LEATHER_CHESTPLATE)
            .add(Items.LEATHER_HELMET)
            .add(Items.LEATHER_LEGGINGS)
            .add(Items.LEATHER_HORSE_ARMOR)
        getOrCreateTagBuilder(EstrogenTags.Items.LIGHT_EMITTERS)
            .add(Items.TORCH)
            .add(Items.TORCHFLOWER)
            .add(Items.LANTERN)
            .add(Items.SOUL_LANTERN)
            .add(Items.CANDLE)
        getOrCreateTagBuilder(net.minecraft.tags.ItemTags.WOOL)
            .add(EstrogenBlocks.MothWool.asItem())
            .add(EstrogenBlocks.QuiltedMothWool.asItem())
        getOrCreateTagBuilder(net.minecraft.tags.ItemTags.WOOL_CARPETS)
            .add(EstrogenBlocks.MothCarpet.asItem())
            .add(EstrogenBlocks.QuiltedMothCarpet.asItem())
        getOrCreateTagBuilder(net.minecraft.tags.ItemTags.BEDS)
            .add(EstrogenBlocks.MothBed.asItem())
            .add(EstrogenBlocks.QuiltedMothBed.asItem())
        getOrCreateTagBuilder(EstrogenTags.Items.UPRIGHT_ON_BELT)
            .add(EstrogenItems.GenderChangePotion)
            .add(EstrogenItems.HorseUrineBottle)
        getOrCreateTagBuilder(EstrogenTags.Items.MALUM_GROSS_FOODS)
            .add(EstrogenItems.HorseUrineBottle)
        getOrCreateTagBuilder(EstrogenTags.Items.CHEST_ARMOR_IGNORE)
            .add(Items.ELYTRA)
            .add(EstrogenItems.MothElytra)
            .addOptional(ResourceLocation("mekanism", "hdpe_elytra"))
            .addOptional(ResourceLocation("deeperdarker", "soul_elytra"))
            .addOptional(ResourceLocation("mekanism", "jetpack"))
            .addOptional(ResourceLocation("silentgear", "elytra"))
            .addOptional(ResourceLocation("gtceu", "liquid_fuel_jetpack"))
            .addOptional(ResourceLocation("gtceu", "electric_jetpack"))
            .addOptional(ResourceLocation("gtceu", "advanced_electric_jetpack"))
            .addOptional(ResourceLocation("endermanoverhaul", "savanna_hood"))
            .addOptional(ResourceLocation("endermanoverhaul", "snowy_hood"))
            .addOptional(ResourceLocation("endermanoverhaul", "badlands_hood"))
            .addOptional(ResourceLocation("ironjetpacks", "jetpack"))
        getOrCreateTagBuilder(EstrogenTags.Items.NON_RECOLORABLE)
            .add(EstrogenBlocks.MothWool.asItem())
            .add(EstrogenBlocks.MothCarpet.asItem())
            .add(EstrogenBlocks.QuiltedMothWool.asItem())
            .add(EstrogenBlocks.QuiltedMothCarpet.asItem())
            .add(EstrogenBlocks.MothBed.asItem())
            .add(EstrogenBlocks.QuiltedMothBed.asItem())
        getOrCreateTagBuilder(EstrogenTags.Items.MAGNET)
            .add(EstrogenBlocks.DreamBlock.asItem())
        getOrCreateTagBuilder(EstrogenTags.Items.DISABLES_CAPE)
            .add(EstrogenItems.MothElytra)
        getOrCreateTagBuilder(EstrogenTags.Items.CHEST_FEATURE_DISABLED)
            .addOptional(ResourceLocation("botania", "manasteel_chestplate"))
            .addOptional(ResourceLocation("botania", "elementium_chestplate"))
            .addOptional(ResourceLocation("botania", "terrasteel_chestplate"))
            .addOptional(ResourceLocation("mythicbotany", "alfsteel_chestplate"))
            .addOptional(ResourceLocation("eidolon", "bonelord_chestplate"))
            .addOptional(ResourceLocation("silentgear", "chestplate"))
            .addOptional(ResourceLocation("psi", "psimetal_exosuit_chestplate"))
            .addOptional(ResourceLocation("tconstruct", "plate_chestplate"))
            .addOptional(ResourceLocation("mekanism", "jetpack_armored"))
            .addOptional(ResourceLocation("mekanism", "mekasuit_bodyarmor"))
            .addOptional(ResourceLocation("advanced_ae", "quantum_chestplate"))
            .addOptional(ResourceLocation("botania", "manaweave_chestplate"))
            .addOptional(ResourceLocation("eidolon", "warlock_cloak"))
            .addOptional(ResourceLocation("tconstruct", "travelers_chestplate"))
            .addOptional(ResourceLocation("everythingcopper", "copper_chestplate"))
            //TODO: someone do these so I don't have to disable em pls
            .addOptional(ResourceLocation("ars_nouveau", "battlemage_robes"))
            .addOptional(ResourceLocation("ars_nouveau", "arcanist_robes"))
            .addOptional(ResourceLocation("ars_nouveau", "sorcerer_robes"))
            .addOptional(ResourceLocation("tconstruct", "slime_chestplate"))
            .addOptional(ResourceLocation("irons_spellbooks", "pumpkin_chestplate"))
            .addOptional(ResourceLocation("irons_spellbooks", "electromancer_chestplate"))
            .addOptional(ResourceLocation("irons_spellbooks", "cultist_chestplate"))
            .addOptional(ResourceLocation("irons_spellbooks", "cryomancer_chestplate"))
            .addOptional(ResourceLocation("irons_spellbooks", "shadowwalker_chestplate"))
            .addOptional(ResourceLocation("irons_spellbooks", "plagued_chestplate"))
            .addOptional(ResourceLocation("irons_spellbooks", "priest_chestplate"))
            .addOptional(ResourceLocation("irons_spellbooks", "pyromancer_chestplate"))
            .addOptional(ResourceLocation("irons_spellbooks", "archevoker_chestplate"))

    }
}