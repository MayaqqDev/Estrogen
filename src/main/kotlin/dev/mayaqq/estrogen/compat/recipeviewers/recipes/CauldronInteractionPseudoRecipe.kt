package dev.mayaqq.estrogen.compat.recipeviewers.recipes

import dev.mayaqq.cynosure.helpers.McFont
import dev.mayaqq.cynosure.text.Text
import dev.mayaqq.cynosure.text.TextProperties.width
import dev.mayaqq.cynosure.text.TextStyle.color
import dev.mayaqq.cynosure.utils.colors.McGray
import dev.mayaqq.cynosure.utils.colors.McGreen
import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVIngredient
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVPseudoRecipe
import dev.mayaqq.estrogen.compat.recipeviewers.api.RecipeData
import dev.mayaqq.estrogen.compat.recipeviewers.api.Role
import dev.mayaqq.estrogen.compat.recipeviewers.api.elements.GuiBlockRenderer
import dev.mayaqq.estrogen.content.blocks.RichCauldronInteraction
import dev.mayaqq.estrogen.id
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.block.AbstractCauldronBlock
import net.minecraft.world.level.block.LayeredCauldronBlock
import net.minecraft.world.phys.Vec3

class CauldronInteractionPseudoRecipe(data: CIPRData) : CRVPseudoRecipe<CIPRData>(data) {
    override fun init() {

        addTexture(RecipeTextures.JEI_SHADOW, 62, 37)
        addTexture(RecipeTextures.JEI_ARROW, 7 + 18 + 4, 32)
        addTexture(RecipeTextures.JEI_ARROW, 152 - 42 - 4, 32)
        addSlot(inputs[0], 7, 28, Role.INPUT)
        addSlot(outputs[0], 152, 28, Role.OUTPUT)
        var block = data.cauldron.defaultBlockState()
        if (data.cauldron is LayeredCauldronBlock) {
            block = block.setValue(LayeredCauldronBlock.LEVEL, 3)
        }
        addDrawable(78, 42, GuiBlockRenderer(
            block,
            null,
            0, 0, 0,
            Vec3(22.5, 45.0, 0.0),
            20.0
        )
        )
        addDrawable(0, 0) { graphics, offsetX, offsetY, mouseX, mouseY, delta ->
            val text = Text.translatable(data.cauldron.descriptionId) {
                color = McGray
            }
            graphics.renderTooltip(McFont, text, 10, 10)
        }

    }

    override val inputs: List<CRVIngredient>
        get() = listOf(CRVIngredient.of(Ingredient.of(data.item)))
    override val outputs: List<CRVIngredient>
        get() = listOf(CRVIngredient.of(data.interaction.expectedOutput))
    override val catalysts: List<CRVIngredient> get() = listOf(CRVIngredient.of(Ingredient.of(Items.CAULDRON)))

    override fun getId(): ResourceLocation = id("${data.item.descriptionId.split(".").last()}_to_${data.cauldron.descriptionId.split(".").last()}_cauldron_interaction")
}

class CIPRData(val item: Item, val interaction: RichCauldronInteraction, val cauldron: AbstractCauldronBlock) : RecipeData