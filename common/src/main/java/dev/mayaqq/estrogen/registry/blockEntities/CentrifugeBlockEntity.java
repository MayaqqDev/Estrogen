package dev.mayaqq.estrogen.registry.blockEntities;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import dev.mayaqq.estrogen.registry.EstrogenRecipes;
import dev.mayaqq.estrogen.registry.recipes.CentrifugingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CentrifugeBlockEntity extends KineticBlockEntity {

    public CentrifugeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide) return;
        level.getServer().getRecipeManager().getAllRecipesFor(EstrogenRecipes.CENTRIFUGING.get()).forEach(recipe -> {
            ((CentrifugingRecipe) (Object) recipe).setBlockEntity(this);
            recipe.matches(null, level);
        });
    }
}