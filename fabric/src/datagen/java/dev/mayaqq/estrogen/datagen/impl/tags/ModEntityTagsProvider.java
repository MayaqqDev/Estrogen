package dev.mayaqq.estrogen.datagen.impl.tags;

import dev.mayaqq.estrogen.datagen.base.platform.PlatformHelper;
import dev.mayaqq.estrogen.datagen.base.tags.BaseTagProvider;
import dev.mayaqq.estrogen.registry.EstrogenTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public class ModEntityTagsProvider extends BaseTagProvider.EntityProvider {

    public ModEntityTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, PlatformHelper helper) {
        super(output, registriesFuture, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        getOrCreateTagBuilder(EstrogenTags.Entities.URINE_GIVING)
                .add(EntityType.HORSE)
                .add(EntityType.ZOMBIE_HORSE)
                .add(EntityType.DONKEY)
                .add(EntityType.MULE);
    }
}
