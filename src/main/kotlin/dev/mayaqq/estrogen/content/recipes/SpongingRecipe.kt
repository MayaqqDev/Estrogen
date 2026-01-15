@file:EventSubscriber
package dev.mayaqq.estrogen.content.recipes

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.teamresourceful.bytecodecs.base.ByteCodec
import com.teamresourceful.bytecodecs.base.`object`.ObjectByteCodec
import dev.mayaqq.cynosure.core.bytecodecs.ByteCodecs
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.utils.Either
import dev.mayaqq.cynosure.utils.isLeft
import dev.mayaqq.estrogen.content.EstrogenRecipes
import dev.mayaqq.estrogen.content.recipes.data.FluidRecipeCodec
import dev.mayaqq.estrogen.content.recipes.inventory.FluidData
import dev.mayaqq.estrogen.content.recipes.viewers.RecipeViewerInfo
import dev.mayaqq.estrogen.id
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.core.RegistryAccess
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.level.material.Fluids

class SpongingRecipe(val recipeId: ResourceLocation, val input: Either<Fluid, TagKey<Fluid>>, val output: ResourceLocation) : Recipe<FluidData> {
    override fun matches(data: FluidData, level: Level): Boolean {
        if (data.fluid.`is`(Fluids.EMPTY)) return false
        if (input.isLeft) {
            val fluid = input.left!!
            if (data.fluid.type.isSame(fluid)) return true
        } else {
            val tag = input.right!!
            if (data.fluid.`is`(tag)) return true
        }
        return false
    }

    override fun assemble(data: FluidData, access: RegistryAccess): ItemStack = access.registry(Registries.FLUID).get().get(output)?.bucket?.defaultInstance ?: throw UnsupportedOperationException()
    override fun getResultItem(access: RegistryAccess): ItemStack = access.registry(Registries.FLUID).get().get(output)?.bucket?.defaultInstance ?: throw UnsupportedOperationException()
    override fun getId(): ResourceLocation = recipeId
    override fun getSerializer(): RecipeSerializer<*> = EstrogenRecipes.Serializers.SPONGING
    override fun getType(): RecipeType<*> = EstrogenRecipes.SPONGING
    override fun canCraftInDimensions(width: Int, height: Int): Boolean = true
    override fun getIngredients(): NonNullList<Ingredient> = NonNullList.of(Ingredient.EMPTY)

    companion object: RecipeViewerInfo {
        @JvmStatic
        fun onSuckUp(
            blockState: BlockState,
            fluidState: FluidState,
            center: BlockPos,
            level: Level,
            pos: BlockPos,
        ): BlockState? {
            level.recipeManager.getAllRecipesFor(EstrogenRecipes.SPONGING).forEach { recipe ->
                if (recipe.matches(FluidData(fluidState, blockState), level) && !level.isClientSide) {
                    return BuiltInRegistries.BLOCK.get(recipe.output).withPropertiesOf(blockState)
                }
            }
            return null
        }

        fun codec(id: ResourceLocation): Codec<SpongingRecipe> = RecordCodecBuilder.create { instance ->
            instance.group(
                RecordCodecBuilder.point(id),
                FluidRecipeCodec.fieldOf("input").forGetter(SpongingRecipe::input),
                ResourceLocation.CODEC.fieldOf("output").forGetter(SpongingRecipe::output)
            ).apply(instance, ::SpongingRecipe)
        }

        fun netcodec(id: ResourceLocation): ByteCodec<SpongingRecipe> = ObjectByteCodec.create(
            ByteCodecs.constantFieldOf(id),
            FluidRecipeCodec.NETWORK.fieldOf( SpongingRecipe::input),
            ByteCodecs.RESOURCE_LOCATION.fieldOf(SpongingRecipe::output),
            ::SpongingRecipe
        )

        override val display: ItemStack = Items.SPONGE.defaultInstance
        override val catalyst: ItemStack = Items.BUCKET.defaultInstance
        override val id: ResourceLocation = id("sponging")
        override val height: Int = 70
        override val type: RecipeType<*> get() = EstrogenRecipes.SPONGING
        override val width: Int = 177
        override val workstation: ItemStack? get() = Items.SPONGE.defaultInstance
    }
}