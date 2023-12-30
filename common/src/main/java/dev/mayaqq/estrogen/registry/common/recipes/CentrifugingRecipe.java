package dev.mayaqq.estrogen.registry.common.recipes;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.item.SmartInventory;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.registry.common.EstrogenRecipes;
import dev.mayaqq.estrogen.registry.common.blockEntities.CentrifugeBlockEntity;
import dev.mayaqq.estrogen.registry.common.recipes.common.DataInventory;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("UnstableApiUsage")
public class CentrifugingRecipe extends ProcessingRecipe<SmartInventory> {
    public CentrifugingRecipe(IRecipeTypeInfo typeInfo, ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(typeInfo, params);
    }


    public CentrifugingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        this(EstrogenRecipes.CENTRIFUGING, params);
    }

    @Override
    public boolean matches(SmartInventory inventory, World world) {
        CentrifugeBlockEntity be = ((DataInventory) inventory).getCentrifuge();
        Storage<FluidVariant> storageUp = FluidStorage.SIDED.find(world, be.getPos().up(), Direction.DOWN);
        Storage<FluidVariant> storageDown = FluidStorage.SIDED.find(world, be.getPos().down(), Direction.UP);

        if (storageUp == null || storageDown == null) return false;

        float speed = be.getSpeed();

        if (speed != 256 && speed != -256) return false;

        FluidIngredient fluidIngredient = fluidIngredients.get(0);
        long amountRequired = fluidIngredient.getRequiredAmount();

        AtomicBoolean matches = new AtomicBoolean(false);
        TransferUtil.getAllFluids(storageDown).forEach(fluidStack -> {
            if (!fluidIngredient.test(fluidStack)) return;
            try(Transaction tx = Transaction.openOuter()) {
                long amount = storageDown.extract(FluidVariant.of(fluidStack.getFluid()), amountRequired, tx);
                if (amount == amountRequired) {
                    long amount2 = storageUp.insert(FluidVariant.of(fluidResults.get(0).getFluid()), fluidResults.get(0).getAmount(), tx);
                    if (amount2 == amountRequired) {
                        tx.commit();
                        matches.set(true);
                    }
                }
            }
        });
        return matches.get();
    }



    @Override
    protected int getMaxInputCount() {
        return 0;
    }

    @Override
    protected int getMaxOutputCount() {
        return 0;
    }

    @Override
    protected int getMaxFluidInputCount() {
        return 1;
    }

    @Override
    protected int getMaxFluidOutputCount() {
        return 1;
    }
}
