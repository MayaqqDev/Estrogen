package dev.mayaqq.estrogen.registry.blockEntities;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import dev.architectury.fluid.FluidStack;
import dev.mayaqq.estrogen.registry.EstrogenRecipes;
import dev.mayaqq.estrogen.threeamedition.DataInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class CentrifugeBlockEntity extends KineticBlockEntity implements IHaveGoggleInformation {

    FluidStack processingFrom;
    FluidStack processingTo;

    public CentrifugeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        if (world.isClient) return;
        world.getServer().getRecipeManager().listAllOfType(EstrogenRecipes.CENTRIFUGING.getType()).forEach(recipe -> {
            recipe.matches(new DataInventory(1, this), world);
        });
    }

    public FluidStack getProcessingFrom() {
        return processingFrom;
    }

    public FluidStack getProcessingTo() {
        return processingTo;
    }

    public void setProcessingFrom(FluidStack processingFrom) {
        this.processingFrom = processingFrom;
    }

    public void setProcessingTo(FluidStack processingTo) {
        this.processingTo = processingTo;
    }
}