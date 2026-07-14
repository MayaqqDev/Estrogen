package dev.mayaqq.estrogen.compat.recipeviewers.recipes

import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVIngredient
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVRecipe
import dev.mayaqq.estrogen.compat.recipeviewers.api.Role
import dev.mayaqq.estrogen.compat.recipeviewers.api.ViewerInfo
import dev.mayaqq.estrogen.content.recipes.EntityInteractionRecipe
import dev.mayaqq.estrogen.content.recipes.getSpawnEggs
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.inventory.InventoryScreen
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.SpawnEggItem
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeHolder
import org.joml.Quaternionf
import org.joml.Vector3f


class EntityInteractionCRVRecipe(recipe: RecipeHolder<EntityInteractionRecipe>) : CRVRecipe<EntityInteractionRecipe>(recipe) {

    var entities = mutableMapOf<SpawnEggItem, LivingEntity>()

    override fun init() {
        addTexture(RecipeTextures.JEI_SHADOW, 62, 47)
        addTexture(RecipeTextures.JEI_DOWN_ARROW, 74, 10)

        val matchingStacks: Array<ItemStack> = recipe.value().entity.getSpawnEggs()
        val eggs = addSlot(CRVIngredient.of(Ingredient.of(*matchingStacks)), 27, 38, Role.RENDER_ONLY)

        addSlot(inputs[0], 51, 5, Role.INPUT)
        addSlot(outputs[0], 132, 38, Role.OUTPUT)

        addDrawable(88, 55) { graphics, offsetX, offsetY, mouseX, mouseY, delta ->
            val item = (System.currentTimeMillis() / 1000 % eggs.ingredient.size).toInt()
            val current = eggs.ingredient
            val stack = current.items[item]
            val entity = entities.getOrPut(stack.item as SpawnEggItem) {
                Minecraft.getInstance().level?.let {
                    (stack.item as SpawnEggItem).getType(stack).create(
                        it
                    )
                } as LivingEntity
            }
            val rad = 0.017453292f
            val angle = Quaternionf().rotateYXZ(50 * rad, 180 * rad, 0f)

            InventoryScreen.renderEntityInInventory(
                graphics,
                0.5F,
                0F,
                20F,
                Vector3f(),
                angle,
                null,
                entity
            )
        }
    }

    override val inputs: List<CRVIngredient> = listOf(CRVIngredient.of(recipe.value().ingredient))
    override val outputs: List<CRVIngredient> = listOf(CRVIngredient.of(recipe.value().result))
    override val catalysts: List<CRVIngredient> = listOf()

    companion object : ViewerInfo<EntityInteractionRecipe, EntityInteractionCRVRecipe>(
        EntityInteractionRecipe,
        { recipe -> EntityInteractionCRVRecipe(recipe as RecipeHolder<EntityInteractionRecipe>) },
        EntityInteractionRecipe::class
    )
}