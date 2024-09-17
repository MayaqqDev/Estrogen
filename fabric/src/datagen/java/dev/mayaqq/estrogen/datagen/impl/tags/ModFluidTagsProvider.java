package dev.mayaqq.estrogen.datagen.impl.tags;

import com.simibubi.create.AllTags;
import dev.mayaqq.estrogen.datagen.base.platform.ModPlatform;
import dev.mayaqq.estrogen.datagen.base.platform.PlatformHelper;
import dev.mayaqq.estrogen.datagen.base.tags.BaseTagProvider;
import dev.mayaqq.estrogen.registry.EstrogenFluids;
import dev.mayaqq.estrogen.registry.EstrogenTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ModFluidTagsProvider extends BaseTagProvider.FluidProvider {

    public ModFluidTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, PlatformHelper helper) {
        super(output, completableFuture, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        getOrCreateTagBuilder(EstrogenTags.Fluids.WATER)
                .add(EstrogenFluids.HORSE_URINE.get())
                .add(EstrogenFluids.FILTRATED_HORSE_URINE.get());
        getOrCreateTagBuilder(EstrogenTags.Fluids.URINE)
                .add(EstrogenFluids.HORSE_URINE.get())
                .add(EstrogenFluids.FILTRATED_HORSE_URINE.get());
        getOrCreateTagBuilder(AllTags.AllFluidTags.FAN_PROCESSING_CATALYSTS_BLASTING.tag)
                .add(EstrogenFluids.MOLTEN_SLIME.get());

        if (getPlatform() == ModPlatform.FABRIC) {
            getOrCreateTagBuilder(EstrogenTags.Fluids.WATER)
                    .add(EstrogenFluids.LIQUID_ESTROGEN.get())
                    .add(EstrogenFluids.LIQUID_ESTROGEN.getFlowing())
                    .add(EstrogenFluids.HORSE_URINE.getFlowing())
                    .add(EstrogenFluids.FILTRATED_HORSE_URINE.getFlowing())
                    .add(EstrogenFluids.TESTOSTERONE_MIXTURE.get())
                    .add(EstrogenFluids.TESTOSTERONE_MIXTURE.getFlowing());
            getOrCreateTagBuilder(EstrogenTags.Fluids.LAVA)
                    .add(EstrogenFluids.MOLTEN_SLIME.get())
                    .add(EstrogenFluids.MOLTEN_SLIME.getFlowing())
                    .add(EstrogenFluids.MOLTEN_AMETHYST.get())
                    .add(EstrogenFluids.MOLTEN_AMETHYST.getFlowing());
        }
    }
}
