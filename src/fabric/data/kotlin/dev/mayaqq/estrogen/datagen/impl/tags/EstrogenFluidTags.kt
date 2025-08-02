package dev.mayaqq.estrogen.datagen.impl.tags

import dev.mayaqq.estrogen.content.EstrogenFluids
import dev.mayaqq.estrogen.content.EstrogenTags
import dev.mayaqq.estrogen.datagen.platform.Platform
import dev.mayaqq.estrogen.datagen.platform.PlatformHelper
import dev.mayaqq.estrogen.datagen.tags.BaseTagProvider
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture

class EstrogenFluidTags(
    data: FabricDataOutput,
    completableFeature: CompletableFuture<HolderLookup.Provider>,
    helper: PlatformHelper
) : BaseTagProvider.FluidProvider(data, completableFeature, helper) {
    override fun addTags(provider: HolderLookup.Provider) {
        getOrCreateTagBuilder(EstrogenTags.Fluids.WATER)
            .add(EstrogenFluids.HorseUrine.value)
            .add(EstrogenFluids.HorseUrine.flowing)
            .add(EstrogenFluids.FiltratedHorseUrine.value)
            .add(EstrogenFluids.FiltratedHorseUrine.flowing)
        getOrCreateTagBuilder(EstrogenTags.Fluids.URINE)
            .add(EstrogenFluids.HorseUrine.value)
            .add(EstrogenFluids.HorseUrine.flowing)
            .add(EstrogenFluids.FiltratedHorseUrine.value)
            .add(EstrogenFluids.FiltratedHorseUrine.flowing)
        getOrCreateTagBuilder(EstrogenTags.Fluids.PROCESSING_LAVA)
            .add(EstrogenFluids.MoltenSlime.value)
            .add(EstrogenFluids.MoltenSlime.flowing)
        getOrCreateTagBuilder(EstrogenTags.Fluids.SPONGE_IGNORING)
            .add(EstrogenFluids.FiltratedHorseUrine.value)
            .add(EstrogenFluids.FiltratedHorseUrine.flowing)

        if (platform == Platform.FABRIC) {
            getOrCreateTagBuilder(EstrogenTags.Fluids.WATER)
                .add(EstrogenFluids.LiquidEstrogen.value)
                .add(EstrogenFluids.LiquidEstrogen.flowing)
                .add(EstrogenFluids.HorseUrine.flowing)
                .add(EstrogenFluids.FiltratedHorseUrine.flowing)
                .add(EstrogenFluids.TestosteroneMixture.value)
                .add(EstrogenFluids.TestosteroneMixture.flowing)
                .add(EstrogenFluids.GenderFluid.value)
                .add(EstrogenFluids.GenderFluid.flowing);
            getOrCreateTagBuilder(EstrogenTags.Fluids.LAVA)
                .add(EstrogenFluids.MoltenSlime.value)
                .add(EstrogenFluids.MoltenSlime.flowing)
                .add(EstrogenFluids.MoltenAmethyst.value)
                .add(EstrogenFluids.MoltenAmethyst.flowing);
        }
    }
}