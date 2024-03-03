package dev.mayaqq.estrogen.registry.recipes;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.registry.EstrogenProcessingRecipes;
import dev.mayaqq.estrogen.registry.blockEntities.CentrifugeBlockEntity;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

public class CentrifugingRecipe extends ProcessingRecipe<Inventory> {
    CentrifugeBlockEntity blockEntity;

    public CentrifugingRecipe(IRecipeTypeInfo typeInfo, ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(typeInfo, params);
    }

    public CentrifugingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        this(EstrogenProcessingRecipes.CENTRIFUGING, params);
    }

    public void setBlockEntity(CentrifugeBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    public boolean matches(@NotNull Inventory inventory, @NotNull Level level) {
        FluidContainer fluidContainerUp = FluidContainer.of(level, blockEntity.getBlockPos().above(), Direction.DOWN);
        FluidContainer fluidContainerDown = FluidContainer.of(level, blockEntity.getBlockPos().below(), Direction.UP);

        if (fluidContainerUp == null || fluidContainerDown == null) return false;

        FluidHolder fluidUp = fluidContainerUp.getFirstFluid();
        FluidHolder fluidDown = fluidContainerDown.getFirstFluid();

        if (fluidUp == null || fluidDown == null) return false;

        float speed = blockEntity.getSpeed();

        if (speed != 256 && speed != -256) return false;

        FluidIngredient fluidIngredient = this.getFluidIngredients().get(0);
        long amountRequired = fluidIngredient.getRequiredAmount();

        int amountOut = (int) this.getFluidResults().get(0).getAmount();

        AtomicBoolean matches = new AtomicBoolean(false);

        FluidHolder fluidIngredientHolder = FluidHolder.of(fluidIngredient.getMatchingFluidStacks().get(0).getFluid(), amountRequired);
        FluidHolder fluidResultHolder = FluidHolder.of(this.getFluidResults().get(0).getFluid(), amountOut);

        fluidContainerDown.getFluids().forEach(fluidBottom -> {
            if (fluidBottom.matches(fluidIngredientHolder)) {
                if (fluidBottom.getFluidAmount() >= amountRequired) {
                    if (fluidContainerUp.insertFluid(fluidResultHolder, true) >= amountOut) {
                        fluidContainerUp.getFluids().forEach(fluidTop -> {
                            if (fluidTop.matches(fluidResultHolder)) {
                                fluidContainerDown.extractFluid(fluidIngredientHolder, false);
                                fluidContainerUp.insertFluid(fluidResultHolder, false);
                                matches.set(true);
                            }
                        });
                    }
                }
            }
        });
        return matches.get();
    }

    public ItemStack assemble(Inventory container, RegistryAccess registryAccess) {
        return getResultItem(registryAccess);
    }

    public boolean canCraftInDimensions(int i, int j) {
        return true;
    }

    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return getRollableResults().isEmpty() ? ItemStack.EMPTY
                : getRollableResults().get(0)
                .getStack();
    }

    public ResourceLocation getId() {
        return id;
    }

    public RecipeSerializer<?> getSerializer() {
        return getTypeInfo().getSerializer();
    }

    public RecipeType<?> getType() {
        return getTypeInfo().getType();
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
