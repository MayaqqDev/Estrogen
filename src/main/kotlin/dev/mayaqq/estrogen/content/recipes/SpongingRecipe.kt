@file:EventSubscriber
package dev.mayaqq.estrogen.content.recipes

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.teamresourceful.bytecodecs.base.ByteCodec
import com.teamresourceful.bytecodecs.base.`object`.ObjectByteCodec
import dev.mayaqq.cynosure.core.bytecodecs.ByteCodecs
import dev.mayaqq.cynosure.core.bytecodecs.item.ItemStackByteCodec
import dev.mayaqq.cynosure.core.bytecodecs.toByteCodec
import dev.mayaqq.cynosure.core.codecs.IngredientCodec
import dev.mayaqq.cynosure.core.codecs.advancements.PredicateCodecs
import dev.mayaqq.cynosure.core.codecs.fieldOf
import dev.mayaqq.cynosure.core.codecs.item.ItemStackCodec
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.events.entity.player.interaction.InteractionEvent
import dev.mayaqq.cynosure.utils.Either
import dev.mayaqq.cynosure.utils.contains
import dev.mayaqq.cynosure.utils.isLeft
import dev.mayaqq.cynosure.utils.isRight
import dev.mayaqq.estrogen.content.EstrogenRecipes
import dev.mayaqq.estrogen.content.recipes.data.EntityTypeRecipeCodec
import dev.mayaqq.estrogen.content.recipes.data.FluidRecipeCodec
import dev.mayaqq.estrogen.content.recipes.inventory.FluidData
import dev.mayaqq.estrogen.content.recipes.inventory.InteractionData
import dev.mayaqq.estrogen.content.recipes.viewers.RecipeViewerInfo
import dev.mayaqq.estrogen.id
import net.minecraft.advancements.critereon.EntityPredicate
import net.minecraft.core.NonNullList
import net.minecraft.core.RegistryAccess
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.tags.FluidTags
import net.minecraft.tags.TagKey
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.SpawnEggItem
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level
import net.minecraft.world.level.material.Fluid
import java.util.*
import kotlin.jvm.optionals.getOrNull

class SpongingRecipe(val recipeId: ResourceLocation, val input: Either<Fluid, TagKey<Fluid>>, val output: ResourceLocation) : Recipe<FluidData> {
    override fun matches(data: FluidData, level: Level): Boolean {
        if (input.isLeft) {
            val fluid = input.left!!
            val fluidKey = BuiltInRegistries.FLUID.getKey(fluid)
            val flowing = BuiltInRegistries.FLUID.get(ResourceLocation(fluidKey.path, "${fluidKey.namespace}_flowing"))
            if (data.fluid.`is`(fluid) || data.fluid.`is`(flowing)) return true
        } else {
            val tag = input.right!!
            if (data.fluid.`is`(tag)) return true
        }
        return false
    }

    override fun assemble(data: FluidData, registryAccess: RegistryAccess): ItemStack = throw UnsupportedOperationException()
    override fun getResultItem(access: RegistryAccess): ItemStack = throw UnsupportedOperationException()
    override fun getId(): ResourceLocation = recipeId
    override fun getSerializer(): RecipeSerializer<*> = EstrogenRecipes.Serializers.SPONGING
    override fun getType(): RecipeType<*> = EstrogenRecipes.SPONGING
    override fun canCraftInDimensions(width: Int, height: Int): Boolean = true
    override fun getIngredients(): NonNullList<Ingredient> = NonNullList.of(Ingredient.EMPTY)

    companion object: RecipeViewerInfo {
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
        override val width: Int = 177
    }
}