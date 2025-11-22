package dev.mayaqq.estrogen.datagen

import dev.mayaqq.estrogen.datagen.api.EstrogenDatagenEntrypoint
import dev.mayaqq.estrogen.datagen.api.EstrogenPack
import dev.mayaqq.estrogen.datagen.impl.advancements.EstrogenAdvancements
import dev.mayaqq.estrogen.datagen.impl.loottables.EstrogenLoottables
import dev.mayaqq.estrogen.datagen.impl.recipes.minecraft.EstrogenCraftingRecipes
import dev.mayaqq.estrogen.datagen.impl.tags.EstrogenBlockTags
import dev.mayaqq.estrogen.datagen.impl.tags.EstrogenEntityTags
import dev.mayaqq.estrogen.datagen.impl.tags.EstrogenFluidTags
import dev.mayaqq.estrogen.datagen.impl.tags.EstrogenItemTags
import dev.mayaqq.estrogen.datagen.impl.translations.EstrogenTranslations

object EstrogenDatagen : EstrogenDatagenEntrypoint("estrogen") {
    override fun setupCommon(pack: EstrogenPack) {
        //TODO: pack.addProvider(::EstrogenEntityInteractionRecipes);
        pack.addProvider(::EstrogenBlockTags)
        pack.addProvider(::EstrogenEntityTags)
        pack.addProvider(::EstrogenItemTags)
        pack.addProvider(::EstrogenAdvancements)
        pack.addProvider(::EstrogenTranslations)
        pack.addProvider(::EstrogenLoottables)
    }

    override fun setupFabric(pack: EstrogenPack) {
        pack.addProvider(::EstrogenFluidTags)
        pack.addProvider(::EstrogenCraftingRecipes)
    }

    override fun setupForge(pack: EstrogenPack) {
        pack.addProvider(::EstrogenFluidTags)
        pack.addProvider(::EstrogenCraftingRecipes)
    }
}