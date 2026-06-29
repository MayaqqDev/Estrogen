package dev.mayaqq.estrogen.compat.recipeviewers.recipes

import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.helpers.McFont
import dev.mayaqq.cynosure.text.Text
import dev.mayaqq.cynosure.text.TextStyle.color
import dev.mayaqq.estrogen.api.EstrogenFlag
import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVIngredient
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVRecipe
import dev.mayaqq.estrogen.compat.recipeviewers.api.Role
import dev.mayaqq.estrogen.compat.recipeviewers.api.ViewerInfo
import dev.mayaqq.estrogen.compat.recipeviewers.api.elements.GuiBlockRenderer
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.EstrogenFluids
import dev.mayaqq.estrogen.content.EstrogenRecipes
import dev.mayaqq.estrogen.content.recipes.LiquidEstrogenCauldronRecipe
import dev.mayaqq.estrogen.modules.anyModuleHasFlag
import invoke.kitty.kritter.utils.color.MinecraftColors
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.RecipeHolder
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.LayeredCauldronBlock
import net.minecraft.world.level.block.piston.PistonBaseBlock
import net.minecraft.world.level.block.piston.PistonHeadBlock
import net.minecraft.world.level.block.state.properties.PistonType
import net.minecraft.world.phys.Vec3

class LiquidEstrogenCauldronCRVRecipe(recipe: RecipeHolder<LiquidEstrogenCauldronRecipe>) : CRVRecipe<LiquidEstrogenCauldronRecipe>(recipe) {

    var extended = false
    var extendCounter = System.currentTimeMillis()

    fun recipeDisabled(): Boolean {
        return McClient.level?.recipeManager?.getAllRecipesFor(EstrogenRecipes.LIQUID_ESTROGEN_CAULDRON.value)?.any { !it.value().enabled } == true || anyModuleHasFlag(EstrogenFlag.DISABLES_CAULDRON_ESTROGEN)
    }

    override fun init() {
        if (recipeDisabled()) {
            addDrawable(0, 0) { graphics, offsetX, offsetY, mouseX, mouseY, delta ->
                graphics.fill(0, 0, info.width, info.height, -0x2FEFEFF0)
                val text = Text.translatable("estrogen.recipe.common.disabled") {
                    color = MinecraftColors.Red
                }
                graphics.drawString(
                    McFont,
                    text,
                    (info.width / 2) - (McFont.width(text) / 2),
                    (info.height / 2) - McFont.lineHeight / 2,
                    0xFFFFFF,
                    false
                )
            }
        } else {

            addSlot(CRVIngredient.of(EstrogenFluids.FiltratedHorseUrine.bucket.defaultInstance), 10, 14, Role.INPUT)
            addSlot(CRVIngredient.of(EstrogenFluids.LiquidEstrogen.source), 148, 35, Role.OUTPUT)
            addTexture(RecipeTextures.JEI_ARROW, 72, 40)

            addTexture(RecipeTextures.JEI_SHADOW, 104, 49)

            addDrawable(120, 54, GuiBlockRenderer(EstrogenBlocks.LiquidEstrogenCauldron.value!!.defaultBlockState()
                .setValue(LayeredCauldronBlock.LEVEL, 3)))

            addDrawable(0, 0) { graphics, offsetX, offsetY, mouseX, mouseY, delta ->
                graphics.renderTooltip(McFont, Text.of("\uD83D\uDD04 ") {
                    color = MinecraftColors.Green
                    append(Text.translatable("estrogen.recipe.liquid_estrogen_cauldron.tooltip") {
                        color = MinecraftColors.Gray
                    })
                }, 97, 20)
                if (System.currentTimeMillis() - extendCounter > 700) {
                    extended = !extended
                    extendCounter = System.currentTimeMillis()
                }
                RecipeTextures.JEI_DOWN_ARROW.render(graphics, 27 + if (extended) 20 else 0, 18)
                GuiBlockRenderer(
                    EstrogenBlocks.FiltratedHorseUrineCauldron.value!!.defaultBlockState(),
                    x = 30 + if (extended) 20 else 0, y = 55, rotation = Vec3.ZERO
                ).draw(graphics, offsetX, offsetY, mouseX, mouseY, delta)
                if (extended) {
                    GuiBlockRenderer(
                        Blocks.PISTON_HEAD.defaultBlockState().setValue(PistonHeadBlock.TYPE, PistonType.STICKY),
                        x = 30, y = 55, rotation = Vec3(0.0, 270.0, 0.0)
                    ).draw(graphics, offsetX, offsetY, mouseX, mouseY, delta)
                }
                GuiBlockRenderer(Blocks.STICKY_PISTON.defaultBlockState().setValue(PistonBaseBlock.EXTENDED, extended),
                    x = 10, y = 55, rotation = Vec3(0.0, 270.0, 0.0)
                ).draw(graphics, offsetX, offsetY, mouseX, mouseY, delta)
            }
        }
    }

    override val inputs = listOf(CRVIngredient.of((EstrogenFluids.FiltratedHorseUrine.flowing)))
    override val outputs = listOf(CRVIngredient.of((EstrogenFluids.LiquidEstrogen.flowing)))
    override val catalysts = listOf(CRVIngredient.of(Items.CAULDRON.defaultInstance))


    companion object : ViewerInfo<LiquidEstrogenCauldronRecipe, LiquidEstrogenCauldronCRVRecipe>(
        LiquidEstrogenCauldronRecipe,
        { LiquidEstrogenCauldronCRVRecipe(it as RecipeHolder<LiquidEstrogenCauldronRecipe>) },
        LiquidEstrogenCauldronRecipe::class
        )
}