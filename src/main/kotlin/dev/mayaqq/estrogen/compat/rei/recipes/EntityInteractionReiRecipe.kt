package dev.mayaqq.estrogen.compat.rei.recipes

import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.cynosure.utils.Either
import dev.mayaqq.cynosure.utils.fold
import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.compat.rei.StackWithCatalystReiRenderable
import dev.mayaqq.estrogen.content.recipes.EntityInteractionRecipe
import dev.mayaqq.estrogen.content.recipes.getSpawnEggs
import dev.mayaqq.estrogen.content.recipes.viewers.RecipeViewerInfo
import dev.mayaqq.estrogen.id
import me.shedaniel.math.Point
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.client.gui.Renderer
import me.shedaniel.rei.api.client.gui.widgets.Slot
import me.shedaniel.rei.api.client.gui.widgets.Widget
import me.shedaniel.rei.api.client.gui.widgets.Widgets
import me.shedaniel.rei.api.client.registry.display.DisplayCategory
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.display.basic.BasicDisplay
import me.shedaniel.rei.api.common.util.EntryIngredients
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.inventory.InventoryScreen
import net.minecraft.network.chat.Component
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import java.util.*
import kotlin.jvm.optionals.getOrNull


class EntityInteractionReiRecipe(val recipe: EntityInteractionRecipe, val entity: Either<EntityType<*>, TagKey<EntityType<*>>>) : BasicDisplay(
    EntryIngredients.ofIngredients(recipe.ingredients),
    listOf(EntryIngredients.ofItemStacks(listOf(recipe.result))),
    Optional.of(recipe.id)
) {
    companion object {
        val category: CategoryIdentifier<EntityInteractionReiRecipe> = CategoryIdentifier.of(id("entity_interaction"))
    }

    override fun getCategoryIdentifier(): CategoryIdentifier<*> = category

    class Category(private val viewerInfo: RecipeViewerInfo) : DisplayCategory<EntityInteractionReiRecipe> {
        override fun getCategoryIdentifier(): CategoryIdentifier<out EntityInteractionReiRecipe> = category

        override fun getTitle(): Component = Component.translatable("estrogen.recipe.entity_interaction")

        override fun getIcon(): Renderer = StackWithCatalystReiRenderable(viewerInfo)

        override fun getDisplayHeight(): Int = EntityInteractionRecipe.height
        override fun getDisplayWidth(recipe: EntityInteractionReiRecipe): Int = EntityInteractionRecipe.width

        var slot: Slot? = null

        override fun setupDisplay(recipe: EntityInteractionReiRecipe, bounds: Rectangle): MutableList<Widget> {
            val widgets = mutableListOf<Widget>()

            widgets.add(Widgets.createRecipeBase(bounds))

            val eggSlot: Slot = Widgets.createSlot(Point(bounds.x + 27, bounds.y + 38 + 4))
                .markInput()
                .entries(EntryIngredients.ofItemStacks(recipe.entity.getSpawnEggs()))
            widgets.add(eggSlot)
            slot = eggSlot

            val slot: Slot = Widgets.createSlot(Point(bounds.x + 51, bounds.y + 5 + 4))
                .markInput()
                .entries(recipe.inputs[0])
            widgets.add(slot)

            val outputSlot: Slot = Widgets.createSlot(Point(bounds.x + 132, bounds.y + 38 + 4))
                .markOutput()
                .entries(recipe.outputs[0])

            widgets.add(outputSlot)

            @Suppress("UnstableApiUsage")
            val entities = recipe.entity
                .fold(
                    { listOf(it) },
                    { key ->
                        val registry = registryAccess().registry(key.registry()).getOrNull() ?: return@fold listOf()
                        val tag = registry.getTagOrEmpty(key)
                        return@fold tag.mapNotNull { holder -> holder.unwrap().map({ registry.get(it) }, { it }) }
                    }
                )
                .map { type -> lazy { Minecraft.getInstance().level?.let(type::create) as? LivingEntity } }

            widgets.add(Widgets.createDrawableWidget { graphics, mouseX, mouseY, delta ->
                val index = (System.currentTimeMillis() / 2000) % entities.size
                val entity by entities.getOrNull(index.toInt()) ?: return@createDrawableWidget

                graphics.pushPop {
                    translate(bounds.getX().toDouble(), bounds.getY().toDouble() + 4, 0.0)
                    RecipeTextures.JEI_SLOT.render(graphics, 50, 4)
                    RecipeTextures.JEI_SLOT.render(graphics, 26, 37)
                    RecipeTextures.JEI_SLOT.render(graphics, 131, 37)
                    RecipeTextures.JEI_SHADOW.render(graphics, 62, 47)
                    RecipeTextures.JEI_DOWN_ARROW.render(graphics, 74, 10)
                    if (entity != null) {
                        InventoryScreen.renderEntityInInventoryFollowsMouse(
                            graphics,
                            88,
                            55,
                            20,
                            (-mouseX).toFloat() + 87,
                            (-mouseY).toFloat() + 20,
                            entity!!
                        )
                    }
                }
            })

            return widgets
        }
    }
}