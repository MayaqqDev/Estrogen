@file:EventSubscriber
package dev.mayaqq.estrogen.content.recipes

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.teamresourceful.bytecodecs.base.ByteCodec
import com.teamresourceful.bytecodecs.base.`object`.ObjectByteCodec
import dev.mayaqq.cynosure.core.bytecodecs.ExtraByteCodecs
import dev.mayaqq.cynosure.core.bytecodecs.item.ItemStackByteCodec
import dev.mayaqq.cynosure.core.bytecodecs.toByteCodec
import dev.mayaqq.cynosure.core.codecs.IngredientCodec
import dev.mayaqq.cynosure.core.codecs.advancements.PredicateCodecs
import dev.mayaqq.cynosure.core.codecs.fieldOf
import dev.mayaqq.cynosure.core.codecs.item.ItemStackCodec
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.events.entity.player.interaction.InteractionEvent
import dev.mayaqq.estrogen.content.EstrogenRecipes
import dev.mayaqq.estrogen.content.recipes.inventory.InteractionData
import net.minecraft.advancements.critereon.EntityPredicate
import net.minecraft.core.RegistryAccess
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level

class EntityInteractionRecipe(val id: ResourceLocation, val ingredient: Ingredient, val result: ItemStack, val predicate: EntityPredicate, val sound: ResourceLocation?) : Recipe<InteractionData> {
    override fun matches(data: InteractionData, level: Level): Boolean = ingredient.test(data.item) && predicate.matches(data.player, data.entity)

    override fun assemble(data: InteractionData, registryAccess: RegistryAccess): ItemStack = result.copy()

    override fun getResultItem(access: RegistryAccess): ItemStack = result.copy()
    override fun getId(): ResourceLocation = id
    override fun getSerializer(): RecipeSerializer<*> = EstrogenRecipes.Serializers.ENTITY_INTERACTION_SERIALIZER
    override fun getType(): RecipeType<*> = EstrogenRecipes.ENTITY_INTERACTION
    override fun canCraftInDimensions(width: Int, height: Int): Boolean = true

    companion object {
        fun codec(id: ResourceLocation): Codec<EntityInteractionRecipe> = RecordCodecBuilder.create { instance ->
            instance.group(
                RecordCodecBuilder.point(id),
                IngredientCodec.fieldOf("ingredient").forGetter(EntityInteractionRecipe::ingredient),
                ItemStackCodec.fieldOf("result").forGetter(EntityInteractionRecipe::result),
                PredicateCodecs.ENTITY.fieldOf("entity").forGetter(EntityInteractionRecipe::predicate),
                ResourceLocation.CODEC.fieldOf("sound").forGetter(EntityInteractionRecipe::sound)
            ).apply(instance, ::EntityInteractionRecipe)
        }

        fun netcodec(id: ResourceLocation): ByteCodec<EntityInteractionRecipe> = ObjectByteCodec.create(
            ByteCodec.unit(id) fieldOf { _ -> id }, // TODO: Prob make a helper function in cynosure
            IngredientCodec.NETWORK fieldOf EntityInteractionRecipe::ingredient,
            ItemStackByteCodec fieldOf EntityInteractionRecipe::result,
            PredicateCodecs.ENTITY.toByteCodec() fieldOf EntityInteractionRecipe::predicate,
            ExtraByteCodecs.RESOURCE_LOCATION.fieldOf(EntityInteractionRecipe::sound),
            ::EntityInteractionRecipe
        )
    }
}

@Subscription
fun onEntityInteraction(event: InteractionEvent.UseEntity) {
    if (event.level is ServerLevel) {
        event.level.recipeManager.getAllRecipesFor(EstrogenRecipes.ENTITY_INTERACTION).forEach { recipe ->
            val data = InteractionData(event.getUsedStack(),  event.entity, event.player as ServerPlayer)
            if (recipe.matches(data, event.level)) {
                if (recipe.sound != null) BuiltInRegistries.SOUND_EVENT.get(recipe.sound)?.let { event.entity.playSound(it) }

                if (!event.player.isCreative) event.getUsedStack().shrink(1)
                event.player.inventory.placeItemBackInInventory(recipe.assemble(data, event.level.registryAccess()))
                event.result = InteractionResult.SUCCESS
            }
        }
    }
}