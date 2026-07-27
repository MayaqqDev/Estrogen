package dev.mayaqq.estrogen.neoforge.mixins;

import dev.mayaqq.cynosure.injection.ILootTableBuilderKt;
import dev.mayaqq.cynosure.injection.ILootTableKt;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "dev.mayaqq.cynosure.loot.LootilsKt", remap = false)
public abstract class CynosureLootCopyMixin {
    @Inject(method = "copy", at = @At("HEAD"), cancellable = true)
    private static void estrogen$copyWithoutRequiredRandomSequence(
            LootTable table,
            CallbackInfoReturnable<LootTable.Builder> callback
    ) {
        LootTable.Builder builder = LootTable.lootTable().setParamSet(table.getParamSet());
        ILootTableBuilderKt.pools(builder, ILootTableKt.getLootPools(table));
        ILootTableBuilderKt.apply(builder, ILootTableKt.getLootFunctions(table));
        ((LootTableRandomSequenceAccessor) table)
                .estrogen$getRandomSequence()
                .ifPresent(builder::setRandomSequence);
        callback.setReturnValue(builder);
    }
}
