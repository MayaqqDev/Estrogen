package dev.mayaqq.estrogen.platformSpecific.fabric;

import com.simibubi.create.foundation.fluid.FluidIngredient;
import dev.mayaqq.estrogen.registry.blockEntities.CentrifugeBlockEntity;
import dev.mayaqq.estrogen.registry.recipes.CentrifugingRecipe;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("UnstableApiUsage")
public class CentrifugingRecipeMatchesImpl {
    @org.jetbrains.annotations.Contract
    public static boolean matches(CentrifugeBlockEntity be, Level world, CentrifugingRecipe recipe) {
        Storage<FluidVariant> storageUp = FluidStorage.SIDED.find(world, be.getBlockPos().above(), Direction.DOWN);
        Storage<FluidVariant> storageDown = FluidStorage.SIDED.find(world, be.getBlockPos().below(), Direction.UP);

        if (storageUp == null || storageDown == null) return false;

        float speed = be.getSpeed();

        if (speed != 256 && speed != -256) return false;

        FluidIngredient fluidIngredient = recipe.getFluidIngredients().get(0);
        long amountRequired = fluidIngredient.getRequiredAmount();

        AtomicBoolean matches = new AtomicBoolean(false);
        TransferUtil.getAllFluids(storageDown).forEach(fluidStack -> {
            if (!fluidIngredient.test(fluidStack)) return;
            try (Transaction tx = Transaction.openOuter()) {
                long amount = storageDown.extract(FluidVariant.of(fluidStack.getFluid()), amountRequired, tx);
                if (amount == amountRequired) {
                    long amount2 = storageUp.insert(FluidVariant.of(recipe.getFluidResults().get(0).getFluid()), recipe.getFluidResults().get(0).getAmount(), tx);
                    if (amount2 == amountRequired) {
                        tx.commit();
                        matches.set(true);
                    }
                }
            }
        });
        return matches.get();
    }
}
