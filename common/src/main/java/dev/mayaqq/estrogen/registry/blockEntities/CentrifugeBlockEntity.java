package dev.mayaqq.estrogen.registry.blockEntities;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import dev.mayaqq.estrogen.registry.EstrogenBlockEntities;
import dev.mayaqq.estrogen.registry.EstrogenRecipes;
import dev.mayaqq.estrogen.registry.recipes.CentrifugingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CentrifugeBlockEntity extends KineticBlockEntity {

    public CentrifugeBlockEntity(BlockPos pos, BlockState state) {
        super(EstrogenBlockEntities.CENTRIFUGE.get(), pos, state);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide) return;
        level.getServer().getRecipeManager().getAllRecipesFor(EstrogenRecipes.CENTRIFUGING.getType()).forEach(recipe -> {
            ((CentrifugingRecipe) (Object) recipe).setBlockEntity(this);
            recipe.matches(null, level);
        });
    }
}