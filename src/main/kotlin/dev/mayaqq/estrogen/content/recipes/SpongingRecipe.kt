@file:EventSubscriber
package dev.mayaqq.estrogen.content.recipes

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.teamresourceful.bytecodecs.base.ByteCodec
import com.teamresourceful.bytecodecs.base.`object`.ObjectByteCodec
import dev.mayaqq.cynosure.core.bytecodecs.ByteCodecs
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.estrogen.content.EstrogenRecipes
import dev.mayaqq.estrogen.content.recipes.data.FluidRecipeCodec
import dev.mayaqq.estrogen.content.recipes.inventory.FluidData
import dev.mayaqq.estrogen.content.recipes.viewers.RecipeViewerInfo
import dev.mayaqq.estrogen.id
import invoke.kitty.kritter.utils.Either
import invoke.kitty.kritter.utils.isLeft
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.core.NonNullList
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.level.material.Fluids
import kotlin.jvm.optionals.getOrNull

class SpongingRecipe(val input: Either<Fluid, TagKey<Fluid>>, val output: ResourceLocation) : Recipe<FluidData> {
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

    override fun assemble(data: FluidData, access: HolderLookup.Provider): ItemStack = getBucket(access)
    override fun getResultItem(access: HolderLookup.Provider): ItemStack = getBucket(access)
    override fun getSerializer(): RecipeSerializer<*> = EstrogenRecipes.Serializers.SPONGING.value!!
    override fun getType(): RecipeType<*> = EstrogenRecipes.SPONGING.value!!
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
            level.recipeManager.getAllRecipesFor(EstrogenRecipes.SPONGING.value).forEach { recipe ->
                if (recipe.value().matches(FluidData(fluidState, blockState), level) && !level.isClientSide) {
                    return BuiltInRegistries.BLOCK.get(recipe.value().output).withPropertiesOf(blockState)
                }
            }
            return null
        }

        val CODEC: Codec<SpongingRecipe> = RecordCodecBuilder.create { instance ->
            instance.group(
                FluidRecipeCodec.fieldOf("input").forGetter(SpongingRecipe::input),
                ResourceLocation.CODEC.fieldOf("output").forGetter(SpongingRecipe::output)
            ).apply(instance, ::SpongingRecipe)
        }

        val NETCODEC: ByteCodec<SpongingRecipe> = ObjectByteCodec.create(
            FluidRecipeCodec.NETWORK.fieldOf( SpongingRecipe::input),
            ByteCodecs.RESOURCE_LOCATION.fieldOf(SpongingRecipe::output),
            ::SpongingRecipe
        )

        override val display: ItemStack = Items.SPONGE.defaultInstance
        override val catalyst: ItemStack = Items.BUCKET.defaultInstance
        override val id: ResourceLocation = id("sponging")
        override val height: Int = 70
        override val type: RecipeType<*> get() = EstrogenRecipes.SPONGING.value!!
        override val width: Int = 177
        override val workstation: ItemStack? get() = Items.SPONGE.defaultInstance
    }

    private fun getBucket(access: HolderLookup.Provider) = (access.lookup(Registries.FLUID).get().get(ResourceKey.create(Registries.FLUID, output)).getOrNull()?.value())?.bucket?.defaultInstance ?: throw UnsupportedOperationException()
}