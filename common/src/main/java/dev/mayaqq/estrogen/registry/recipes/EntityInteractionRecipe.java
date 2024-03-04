package dev.mayaqq.estrogen.registry.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.IngredientCodec;
import com.teamresourceful.resourcefullib.common.codecs.recipes.ItemStackCodec;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import dev.mayaqq.estrogen.registry.EstrogenRecipes;
import dev.mayaqq.estrogen.registry.recipes.codecs.EntityObjectCodec;
import dev.mayaqq.estrogen.registry.recipes.inventory.EntityInteractionInventory;
import dev.mayaqq.estrogen.registry.recipes.objects.EntityObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record EntityInteractionRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result, EntityObject entity, ResourceLocation sound, Boolean cantBeBaby) implements CodecRecipe<EntityInteractionInventory> {

    /* TODO: Recipe
    SO, I need to make a custom Entity codec, so please do that in the future, another thing is use ad astra as reference, it should basically work.
    https://github.com/terrarium-earth/Ad-Astra/blob/1.20.1/common/src/main/java/earth/terrarium/adastra/common/recipes/machines/CompressingRecipe.java

    Do not forget we are not using a container.
     */

    public static Codec<EntityInteractionRecipe> codec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
                RecordCodecBuilder.point(id),
                IngredientCodec.CODEC.fieldOf("ingredient").forGetter(EntityInteractionRecipe::ingredient),
                ItemStackCodec.CODEC.fieldOf("result").forGetter(EntityInteractionRecipe::result),
                EntityObjectCodec.CODEC.fieldOf("entity").forGetter(EntityInteractionRecipe::entity),
                ResourceLocation.CODEC.fieldOf("sound").forGetter(EntityInteractionRecipe::sound),
                Codec.BOOL.fieldOf("cantBeBaby").forGetter(EntityInteractionRecipe::cantBeBaby)

        ).apply(instance, EntityInteractionRecipe::new));
    }

    public static Codec<EntityInteractionRecipe> netCodec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
                RecordCodecBuilder.point(id),
                IngredientCodec.NETWORK_CODEC.fieldOf("ingredient").forGetter(EntityInteractionRecipe::ingredient),
                ItemStackCodec.NETWORK_CODEC.fieldOf("result").forGetter(EntityInteractionRecipe::result),
                EntityObjectCodec.NETWORK_CODEC.fieldOf("entity").forGetter(EntityInteractionRecipe::entity),
                ResourceLocation.CODEC.fieldOf("sound").forGetter(EntityInteractionRecipe::sound),
                Codec.BOOL.fieldOf("cantBeBaby").forGetter(EntityInteractionRecipe::cantBeBaby)

        ).apply(instance, EntityInteractionRecipe::new));
    }

    @Override
    public @NotNull ResourceLocation id() {
        return null;
    }

    @Override
    public boolean matches(EntityInteractionInventory container, Level level) {
        if (!ingredient.test(container.stack)) return false;
        if (!entity.matches(container.entity)) return false;
        if (container.entity.create(level) instanceof Animal animal) {
            if (animal.isBaby() && cantBeBaby) return false;
        }
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return EstrogenRecipes.ENTITY_INTERACTION_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return EstrogenRecipes.ENTITY_INTERACTION.get();
    }

    @Override
    public ItemStack assemble(@NotNull EntityInteractionInventory container, @NotNull RegistryAccess access) {
        return result.copy();
    }
}
