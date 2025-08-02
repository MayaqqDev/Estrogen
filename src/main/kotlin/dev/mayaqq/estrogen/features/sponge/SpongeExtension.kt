package dev.mayaqq.estrogen.features.sponge

import dev.mayaqq.estrogen.content.EstrogenRecipes
import dev.mayaqq.estrogen.content.recipes.inventory.FluidData
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.FluidState
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

object SpongeExtension {
    fun onSuckUp(
        blockState: BlockState,
        fluidState: FluidState,
        center: BlockPos,
        level: Level,
        pos: BlockPos,
        cir: CallbackInfoReturnable<Boolean>
    ) {
        level.recipeManager.getAllRecipesFor(EstrogenRecipes.SPONGING).forEach { recipe ->
            if (recipe.matches(FluidData(fluidState), level)) {
                level.setBlockAndUpdate(pos,
                    BuiltInRegistries.BLOCK.get(recipe.output).withPropertiesOf(blockState)
                )
                cir.returnValue = true
            }
        }
    }
}