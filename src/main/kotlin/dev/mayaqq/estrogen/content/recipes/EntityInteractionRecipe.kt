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
import dev.mayaqq.cynosure.utils.isRight
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.content.EstrogenRecipes
import dev.mayaqq.estrogen.content.recipes.data.EntityTypeRecipeCodec
import dev.mayaqq.estrogen.content.recipes.inventory.InteractionData
import dev.mayaqq.estrogen.content.recipes.viewers.RecipeViewerInfo
import dev.mayaqq.estrogen.id
import net.minecraft.advancements.critereon.EntityPredicate
import net.minecraft.core.RegistryAccess
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
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
import java.util.*
import kotlin.jvm.optionals.getOrNull

class EntityInteractionRecipe(val recipeId: ResourceLocation, val ingredient: Ingredient, val result: ItemStack, val entity: Either<EntityType<*>, TagKey<EntityType<*>>>, val sound: Optional<ResourceLocation>, val predicate: Optional<EntityPredicate>) : Recipe<InteractionData> {
    override fun matches(data: InteractionData, level: Level): Boolean {
        if (!ingredient.test(data.item)) return false
        return if (entity.isRight && entity.right != null) {
            data.entity.type in entity.right!!
        } else {
            data.entity.type == entity.left
        }
    }

    override fun assemble(data: InteractionData, registryAccess: RegistryAccess): ItemStack = result.copy()

    override fun getResultItem(access: RegistryAccess): ItemStack = result.copy()
    override fun getId(): ResourceLocation = recipeId
    override fun getSerializer(): RecipeSerializer<*> = EstrogenRecipes.Serializers.ENTITY_INTERACTION_SERIALIZER
    override fun getType(): RecipeType<*> = EstrogenRecipes.ENTITY_INTERACTION
    override fun canCraftInDimensions(width: Int, height: Int): Boolean = true

    companion object: RecipeViewerInfo {
        fun codec(id: ResourceLocation): Codec<EntityInteractionRecipe> = RecordCodecBuilder.create { instance ->
            instance.group(
                RecordCodecBuilder.point(id),
                IngredientCodec.fieldOf("ingredient").forGetter(EntityInteractionRecipe::ingredient),
                ItemStackCodec.fieldOf("result").forGetter(EntityInteractionRecipe::result),
                EntityTypeRecipeCodec.fieldOf("entity").forGetter(EntityInteractionRecipe::entity),
                ResourceLocation.CODEC.optionalFieldOf("sound").forGetter(EntityInteractionRecipe::sound),
                PredicateCodecs.ENTITY.optionalFieldOf("predicate").forGetter(EntityInteractionRecipe::predicate),
            ).apply(instance, ::EntityInteractionRecipe)
        }

        fun netcodec(id: ResourceLocation): ByteCodec<EntityInteractionRecipe> = ObjectByteCodec.create(
            ByteCodecs.constantFieldOf(id),
            IngredientCodec.NETWORK fieldOf EntityInteractionRecipe::ingredient,
            ItemStackByteCodec fieldOf EntityInteractionRecipe::result,
            EntityTypeRecipeCodec.NETWORK fieldOf EntityInteractionRecipe::entity,
            ByteCodecs.RESOURCE_LOCATION.optionalFieldOf(EntityInteractionRecipe::sound),
            PredicateCodecs.ENTITY.toByteCodec().optionalFieldOf(EntityInteractionRecipe::predicate),
            ::EntityInteractionRecipe
        )

        override val display: ItemStack = Items.COW_SPAWN_EGG.defaultInstance
        override val catalyst: ItemStack = Items.GLASS_BOTTLE.defaultInstance
        override val id: ResourceLocation = id("entity_interaction")
    }
}

fun Either<EntityType<*>, TagKey<EntityType<*>>>.getSpawnEggs(): ArrayList<ItemStack> {
    val array = arrayListOf<ItemStack>()
    if (this.isRight && this.right != null) {
        BuiltInRegistries.ENTITY_TYPE.getTagOrEmpty(this.right!!).forEach {
            val either = it.unwrap()
            if (either.left().isPresent) {
                val entity = BuiltInRegistries.ENTITY_TYPE.get(either.left().get()) as EntityType<*>
                array.add(entityToEgg(entity)?: return@forEach)
            }
        }
    } else {
        array.add(entityToEgg(this.left!!)?: return array)
    }
    return array
}

private fun entityToEgg(entity: EntityType<*>): ItemStack? {
    return SpawnEggItem.byId(entity)?.defaultInstance
}

@Subscription
fun onEntityInteraction(event: InteractionEvent.UseEntity) {
    if (event.level is ServerLevel) {
        event.level.recipeManager.getAllRecipesFor(EstrogenRecipes.ENTITY_INTERACTION).forEach { recipe ->
            val data = InteractionData(event.getUsedStack(),  event.entity, event.player as ServerPlayer)
            if (recipe.matches(data, event.level)) {
                val sound: ResourceLocation? = recipe.sound.getOrNull()
                if (sound != null) BuiltInRegistries.SOUND_EVENT.get(sound)?.let { event.entity.playSound(it) }

                if (!event.player.isCreative) event.getUsedStack().shrink(1)
                event.player.inventory.placeItemBackInInventory(recipe.assemble(data, event.level.registryAccess()))
                event.result = InteractionResult.SUCCESS
            }
        }
    }
}