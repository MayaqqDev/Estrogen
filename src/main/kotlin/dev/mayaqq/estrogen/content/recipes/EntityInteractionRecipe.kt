@file:EventSubscriber
package dev.mayaqq.estrogen.content.recipes

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.teamresourceful.bytecodecs.base.ByteCodec
import com.teamresourceful.bytecodecs.base.`object`.ObjectByteCodec
import dev.mayaqq.cynosure.core.Loader
import dev.mayaqq.cynosure.core.bytecodecs.ByteCodecs
import dev.mayaqq.cynosure.core.bytecodecs.item.ItemStackByteCodec
import dev.mayaqq.cynosure.core.codecs.IngredientCodec
import dev.mayaqq.cynosure.core.codecs.advancements.PredicateCodecs
import dev.mayaqq.cynosure.core.codecs.fieldOf
import dev.mayaqq.cynosure.core.codecs.item.ItemStackCodec
import dev.mayaqq.cynosure.core.currentLoader
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.events.entity.player.interaction.InteractionEvent
import dev.mayaqq.cynosure.utils.contains
import dev.mayaqq.estrogen.content.EstrogenRecipes
import dev.mayaqq.estrogen.content.recipes.data.EntityTypeRecipeCodec
import dev.mayaqq.estrogen.content.recipes.inventory.InteractionData
import dev.mayaqq.estrogen.content.recipes.viewers.RecipeViewerInfo
import dev.mayaqq.estrogen.id
import invoke.kitty.kritter.utils.Either
import invoke.kitty.kritter.utils.dfu.toKritter
import invoke.kitty.kritter.utils.fold
import invoke.kitty.kritter.utils.foldToRight
import invoke.kitty.kritter.utils.isRight
import net.minecraft.advancements.critereon.EntityPredicate
import net.minecraft.core.HolderLookup
import net.minecraft.core.NonNullList
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

class EntityInteractionRecipe(val ingredient: Ingredient, val result: ItemStack, val entity: Either<EntityType<*>, TagKey<EntityType<*>>>, val sound: Optional<ResourceLocation>, val predicate: Optional<EntityPredicate>) : Recipe<InteractionData> {
    override fun matches(data: InteractionData, level: Level): Boolean {
        if (!ingredient.test(data.item)) return false
        return if (entity.isRight && entity.right != null) {
            data.entity.type in entity.right!!
        } else {
            data.entity.type == entity.left
        }
    }

    override fun assemble(data: InteractionData, lookup: HolderLookup.Provider): ItemStack = result.copy()
    override fun getSerializer(): RecipeSerializer<*> = EstrogenRecipes.Serializers.ENTITY_INTERACTION_SERIALIZER.value!!
    override fun getType(): RecipeType<*> = EstrogenRecipes.ENTITY_INTERACTION.value!!
    override fun canCraftInDimensions(width: Int, height: Int): Boolean = true
    override fun getResultItem(lookup: HolderLookup.Provider): ItemStack = result.copy()

    override fun getIngredients(): NonNullList<Ingredient> = NonNullList.of(Ingredient.EMPTY, ingredient)

    companion object : RecipeViewerInfo {

        val CODEC: Codec<EntityInteractionRecipe> = RecordCodecBuilder.create { instance ->
            instance.group(
                IngredientCodec.fieldOf("ingredient").forGetter(EntityInteractionRecipe::ingredient),
                ItemStackCodec.fieldOf("result").forGetter(EntityInteractionRecipe::result),
                EntityTypeRecipeCodec.fieldOf("entity").forGetter(EntityInteractionRecipe::entity),
                ResourceLocation.CODEC.optionalFieldOf("sound").forGetter(EntityInteractionRecipe::sound),
                PredicateCodecs.ENTITY_CODEC.optionalFieldOf("predicate").forGetter(EntityInteractionRecipe::predicate),
            ).apply(instance, ::EntityInteractionRecipe)
        }

        val NET_CODEC: ByteCodec<EntityInteractionRecipe> = ObjectByteCodec.create(
            IngredientCodec.NETWORK fieldOf EntityInteractionRecipe::ingredient,
            ItemStackByteCodec fieldOf EntityInteractionRecipe::result,
            EntityTypeRecipeCodec.NETWORK fieldOf EntityInteractionRecipe::entity,
            ByteCodecs.RESOURCE_LOCATION.optionalFieldOf(EntityInteractionRecipe::sound),
            ByteCodecs.constantFieldOf(Optional.empty()),
            ::EntityInteractionRecipe
        )

        override val display: ItemStack = Items.COW_SPAWN_EGG.defaultInstance
        override val catalyst: ItemStack = Items.GLASS_BOTTLE.defaultInstance
        override val id: ResourceLocation = id("entity_interaction")
        override val height: Int = 70
        override val width: Int = 177
        override val type: RecipeType<*> get() = EstrogenRecipes.ENTITY_INTERACTION.value!!
    }
}

fun Either<EntityType<*>, TagKey<EntityType<*>>>.getSpawnEggs(): Array<ItemStack> = fold(
    { arrayOf(entityToEgg(it) ?: return emptyArray()) },
    {
        BuiltInRegistries.ENTITY_TYPE.getTagOrEmpty(it).mapNotNull { holder ->
            entityToEgg(
                holder.unwrap()
                    .toKritter()
                    .foldToRight { key -> BuiltInRegistries.ENTITY_TYPE[key] ?: return@mapNotNull null }
            )
        }.toTypedArray()
    }
)

private fun entityToEgg(entity: EntityType<*>): ItemStack? = SpawnEggItem.byId(entity)?.defaultInstance

@Subscription
fun onEntityInteraction(event: InteractionEvent.UseEntity) {
    if (event.level is ServerLevel) {
        // Crazy fix, please I have to unify the handling lol, might be Create fucking up though
        if ((currentLoader == Loader.FABRIC && event.phase == InteractionEvent.UseEntity.Phase.SPECIFIC) || (currentLoader == Loader.FORGE && event.phase == InteractionEvent.UseEntity.Phase.GENERAL)) {
            event.level.recipeManager.getAllRecipesFor(EstrogenRecipes.ENTITY_INTERACTION.value!!).forEach { recipe ->
                val data = InteractionData(event.getUsedStack(),  event.entity, event.player as ServerPlayer)
                if (recipe.value().matches(data, event.level)) {
                    val sound: ResourceLocation? = recipe.value().sound.getOrNull()
                    if (sound != null) BuiltInRegistries.SOUND_EVENT.get(sound)?.let { event.entity.playSound(it) }

                    if (!event.player.isCreative) event.getUsedStack().shrink(1)
                    event.player.inventory.placeItemBackInInventory(recipe.value().assemble(data, event.level.registryAccess()))
                    event.result = InteractionResult.SUCCESS
                }
            }
        }
    }
}