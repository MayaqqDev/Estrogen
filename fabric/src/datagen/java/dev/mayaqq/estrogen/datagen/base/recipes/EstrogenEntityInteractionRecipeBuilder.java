package dev.mayaqq.estrogen.datagen.base.recipes;

import com.google.gson.JsonObject;
import dev.mayaqq.estrogen.registry.EstrogenRecipes;
import dev.mayaqq.estrogen.registry.recipes.objects.EntityObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class EstrogenEntityInteractionRecipeBuilder implements RecipeBuilder {

        private final Item ingredient;
        private final Item result;
        private final EntityObject entity;
        private final ResourceLocation sound;
        private final Boolean cantBeBaby;
        private final int count;
        private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
        private boolean showNotification = true;

        public EstrogenEntityInteractionRecipeBuilder(ItemLike ingredient, ItemLike result, int count, EntityObject entity, ResourceLocation sound, Boolean cantBeBaby) {
            this.ingredient = ingredient.asItem();
            this.result = result.asItem();
            this.count = count;
            this.entity = entity;
            this.sound = sound;
            this.cantBeBaby = cantBeBaby;
        }

        public static EstrogenEntityInteractionRecipeBuilder create(ItemLike ingredient, ItemLike result, int count, EntityObject entity, ResourceLocation sound, Boolean cantBeBaby) {
            return new EstrogenEntityInteractionRecipeBuilder(ingredient, result, count, entity, sound, cantBeBaby);
        }

        public static EstrogenEntityInteractionRecipeBuilder create(ItemLike ingredient, ItemLike result, int count, EntityObject entity, ResourceLocation sound) {
            return new EstrogenEntityInteractionRecipeBuilder(ingredient, result, count, entity, sound, false);
        }

        public static EstrogenEntityInteractionRecipeBuilder create(ItemLike ingredient, ItemLike result, EntityObject entity, ResourceLocation sound) {
            return new EstrogenEntityInteractionRecipeBuilder(ingredient, result, 1, entity, sound, false);
        }

        public EstrogenEntityInteractionRecipeBuilder showNotification(boolean showNotification) {
            this.showNotification = showNotification;
            return this;
        }

        public @NotNull RecipeBuilder unlockedBy(@NotNull String criterionName, @NotNull CriterionTriggerInstance criterionTrigger) {
            this.advancement.addCriterion(criterionName, criterionTrigger);
            return this;
        }

        @Override
        public @NotNull RecipeBuilder group(@Nullable String groupName) {
            return this;
        }

        @Override
        public @NotNull Item getResult() {
            return this.result;
        }

        @Override
        public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, @NotNull ResourceLocation recipeId) {
            this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(RequirementsStrategy.OR);
            finishedRecipeConsumer.accept(new Result(recipeId, result, count, ingredient, entity, sound, cantBeBaby, advancement, recipeId.withPrefix("recipes/"), showNotification));
        }

        public record Result(ResourceLocation id, Item result, int count, Item ingredient, EntityObject entity, ResourceLocation sound, Boolean cantBeBaby,Advancement.Builder advancement, ResourceLocation advancementId, boolean showNotification) implements FinishedRecipe {

            @Override
            public void serializeRecipeData(JsonObject json) {
                JsonObject ingredient = new JsonObject();
                ingredient.addProperty("item", BuiltInRegistries.ITEM.getKey(this.ingredient).toString());
                json.add("ingredient", ingredient);

                JsonObject result = new JsonObject();
                result.addProperty("id", BuiltInRegistries.ITEM.getKey(this.result).toString());
                result.addProperty("count", this.count);

                json.add("result", result);

                json.add("entity", EntityObject.toJson(this.entity));

                json.addProperty("sound", this.sound.toString());
                json.addProperty("cant_be_baby", this.cantBeBaby);
            }

            @Override
            public @NotNull ResourceLocation getId() {
                return id;
            }

            @Override
            public @NotNull RecipeSerializer<?> getType() {
                return EstrogenRecipes.ENTITY_INTERACTION_SERIALIZER.get();
            }

            @Override
            public JsonObject serializeAdvancement() {
                return this.advancement.serializeToJson();
            }

            @Nullable
            @Override
            public ResourceLocation getAdvancementId() {
                return advancementId;
            }
        }
    }