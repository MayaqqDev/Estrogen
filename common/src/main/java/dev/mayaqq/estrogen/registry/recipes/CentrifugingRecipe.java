package dev.mayaqq.estrogen.registry.recipes;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.platform.IngredientUtils;
import dev.mayaqq.estrogen.registry.EstrogenRecipes;
import dev.mayaqq.estrogen.registry.blockEntities.CentrifugeBlockEntity;
import dev.mayaqq.estrogen.utils.recipe.RecipeTypeInfo;
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
        this(getRecipeTypeInfo(), params);
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
        double speedRequired = EstrogenConfig.server().centrifugeSpeedRequired.get();

        if (!(speed >= speedRequired || speed <= -speedRequired)) return false;


        FluidHolder input = IngredientUtils.getFluidIngredients(this).get(0);

        FluidHolder output = IngredientUtils.getFluidResults(this).get(0);
        long amountOut = output.getFluidAmount();

        AtomicBoolean matches = new AtomicBoolean(false);


        fluidContainerDown.getFluids().forEach(fluidBottom -> {
            if (fluidBottom.matches(input)) {
                if (fluidBottom.getFluidAmount() >= input.getFluidAmount()) {
                    if (fluidContainerUp.insertFluid(output, true) >= amountOut) {
                        fluidContainerUp.getFluids().forEach(fluidTop -> {
                            if (fluidTop.matches(output) || fluidTop.isEmpty()) {
                                fluidContainerDown.extractFluid(input, false);
                                fluidContainerUp.insertFluid(output, false);
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

    public static RecipeTypeInfo getRecipeTypeInfo() {
        return new RecipeTypeInfo(new ResourceLocation("estrogen", "centrifuging"), EstrogenRecipes.CENTRIFUGING.get(), EstrogenRecipes.CENTRIFUGING_SERIALIZER.get());
    }
}
