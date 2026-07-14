package dev.mayaqq.estrogen.datagen.impl.tags

import dev.mayaqq.estrogen.content.EstrogenEntities
import dev.mayaqq.estrogen.content.EstrogenTags
import dev.mayaqq.estrogen.datagen.api.platform.PlatformHelper
import dev.mayaqq.estrogen.datagen.api.tags.BaseTagProvider
import invoke.kitty.kritter.registry.api.entry.holder
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.minecraft.core.HolderLookup
import net.minecraft.tags.EntityTypeTags
import net.minecraft.world.entity.EntityType
import java.util.concurrent.CompletableFuture

class EstrogenEntityTags(
    data: FabricDataOutput,
    completableFeature: CompletableFuture<HolderLookup.Provider>,
    helper: PlatformHelper
) : BaseTagProvider.EntityProvider(data, completableFeature, helper) {
    override fun addTags(provider: HolderLookup.Provider) {
        getOrCreateTagBuilder(EstrogenTags.Entities.URINE_GIVING)
            .add(EntityType.HORSE)
            .add(EntityType.ZOMBIE_HORSE)
            .add(EntityType.DONKEY)
            .add(EntityType.MULE)
        getOrCreateTagBuilder(EntityTypeTags.ARTHROPOD)
            .add(EstrogenEntities.Moth.get())
    }
}