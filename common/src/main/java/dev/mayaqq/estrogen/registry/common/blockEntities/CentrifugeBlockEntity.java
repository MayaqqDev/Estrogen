package dev.mayaqq.estrogen.registry.common.blockEntities;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import dev.mayaqq.estrogen.registry.common.EstrogenRecipes;
import dev.mayaqq.estrogen.registry.common.recipes.common.DataInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class CentrifugeBlockEntity extends KineticBlockEntity {

    public CentrifugeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void tick() {
        super.tick();
        if (world.isClient) return;
        world.getServer().getRecipeManager().listAllOfType(EstrogenRecipes.CENTRIFUGING.getType()).forEach(recipe -> {
            recipe.matches(new DataInventory(1, this), world);
        });
    }
}