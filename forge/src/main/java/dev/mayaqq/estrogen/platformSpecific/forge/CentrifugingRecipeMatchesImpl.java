package dev.mayaqq.estrogen.platformSpecific.forge;

import com.simibubi.create.foundation.fluid.FluidIngredient;
import dev.mayaqq.estrogen.registry.common.blockEntities.CentrifugeBlockEntity;
import dev.mayaqq.estrogen.registry.common.recipes.CentrifugingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.concurrent.atomic.AtomicBoolean;

public class CentrifugingRecipeMatchesImpl {
    @org.jetbrains.annotations.Contract
    public static boolean matches(CentrifugeBlockEntity be, Level world, CentrifugingRecipe recipe) {
        Level level = be.getLevel();
        BlockEntity up = level.getBlockEntity(be.getBlockPos().above());
        BlockEntity down = level.getBlockEntity(be.getBlockPos().below());

        if (!up.getCapability(ForgeCapabilities.FLUID_HANDLER).isPresent() || !down.getCapability(ForgeCapabilities.FLUID_HANDLER).isPresent()) return false;

        FluidStack fluidUp = up.getCapability(ForgeCapabilities.FLUID_HANDLER).map(handler -> handler.getFluidInTank(0)).orElse(null);
        FluidStack fluidDown = down.getCapability(ForgeCapabilities.FLUID_HANDLER).map(handler -> handler.getFluidInTank(0)).orElse(null);

        if (fluidUp == null || fluidDown == null) return false;

        float speed = be.getSpeed();

        if (speed != 256 && speed != -256) return false;

        FluidIngredient fluidIngredient = recipe.getFluidIngredients().get(0);
        long amountRequired = fluidIngredient.getRequiredAmount();

        int amountOut = recipe.getFluidResults().get(0).getAmount();

        AtomicBoolean matches = new AtomicBoolean(false);

        down.getCapability(ForgeCapabilities.FLUID_HANDLER).map(handler -> handler.getTanks()).ifPresent(tanks -> {
            for (int i = 0; i < tanks; i++) {
                int finalI = i;
                down.getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(handler -> {
                        FluidStack stack = handler.drain((int) amountRequired, IFluidHandler.FluidAction.SIMULATE);
                        if (fluidIngredient.test(stack)) {
                            up.getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(handler1 -> {
                                int amount = handler.drain((int) amountRequired, IFluidHandler.FluidAction.SIMULATE).getAmount();
                                if (amount == amountRequired) {
                                    if (handler1.isFluidValid(amountOut, recipe.getFluidResults().get(0))) {
                                        handler.drain((int) amountRequired, IFluidHandler.FluidAction.EXECUTE);
                                        handler1.fill(new FluidStack(recipe.getFluidResults().get(0).getFluid(), amountOut), IFluidHandler.FluidAction.EXECUTE);
                                        matches.set(true);
                                    }
                                }
                            });
                        }
                });
            }
        });
        return matches.get();
    }
}
