@file:EventSubscriber
package dev.mayaqq.estrogen.content.recipes

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.teamresourceful.bytecodecs.base.ByteCodec
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.events.entity.player.interaction.InteractionEvent
import dev.mayaqq.cynosure.utils.bytecodecs.ExtraByteCodecs
import dev.mayaqq.cynosure.utils.codecs.Codecs
import dev.mayaqq.cynosure.utils.codecs.IngredientCodec
import dev.mayaqq.cynosure.utils.codecs.ItemStackCodec
import dev.mayaqq.cynosure.utils.codecs.advancements.EntityPredicateCodec
import dev.mayaqq.cynosure.utils.codecs.fieldOf
import dev.mayaqq.estrogen.content.EstrogenRecipeSerializers
import dev.mayaqq.estrogen.content.EstrogenRecipes
import dev.mayaqq.estrogen.content.recipes.inventory.InteractionData
import net.minecraft.advancements.critereon.EntityPredicate
import net.minecraft.core.RegistryAccess
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.util.ExtraCodecs
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
    override fun getSerializer(): RecipeSerializer<*> = EstrogenRecipeSerializers.ENTITY_INTERACTION_SERIALIZER
    override fun getType(): RecipeType<*> = EstrogenRecipes.ENTITY_INTERACTION
    override fun canCraftInDimensions(width: Int, height: Int): Boolean = true

    companion object {
        fun codec(id: ResourceLocation): Codec<EntityInteractionRecipe> = RecordCodecBuilder.create { instance ->
            instance.group(
                RecordCodecBuilder.point(id),
                IngredientCodec.CODEC.fieldOf("ingredient").forGetter(EntityInteractionRecipe::ingredient),
                ItemStackCodec.CODEC.fieldOf("result").forGetter(EntityInteractionRecipe::result),
                EntityPredicateCodec.CODEC.fieldOf("entity").forGetter(EntityInteractionRecipe::predicate),
                ResourceLocation.CODEC.fieldOf("sound").forGetter(EntityInteractionRecipe::sound)
            ).apply(instance, ::EntityInteractionRecipe)
        }

        fun netcodec(id: ResourceLocation): ByteCodec<EntityInteractionRecipe> = RecordCodecBuilder.create { instance ->
            instance.group(
                RecordCodecBuilder.point(id),
                IngredientCodec.BYTE_CODEC.fieldOf("ingredient").forGetter(EntityInteractionRecipe::ingredient),
                ItemStackCodec.NETWORK_CODEC.fieldOf("result").forGetter(EntityInteractionRecipe::result),
                EntityPredicateCodec.BYTE_CODEC.fieldOf("entity").forGetter(EntityInteractionRecipe::predicate),
                ResourceLocation.CODEC.fieldOf("sound").forGetter(EntityInteractionRecipe::sound)
            ).apply(instance, ::EntityInteractionRecipe)
        }
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