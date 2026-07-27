package dev.mayaqq.estrogen.neoforge.mixins;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Optional;

@Mixin(LootTable.class)
public interface LootTableRandomSequenceAccessor {
    @Accessor("randomSequence")
    Optional<ResourceLocation> estrogen$getRandomSequence();
}
