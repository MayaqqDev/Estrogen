package dev.mayaqq.estrogen.registry.common;

import com.simibubi.create.Create;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.foundation.utility.Lang;
import dev.architectury.registry.registries.Registrar;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.common.recipes.CentrifugingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

import static dev.mayaqq.estrogen.Estrogen.id;

public enum EstrogenRecipes implements IRecipeTypeInfo {
    CENTRIFUGING(CentrifugingRecipe::new);

    Registrar<RecipeSerializer<?>> recipeSerializers = Estrogen.MANAGER.get().get(Registries.RECIPE_SERIALIZER);
    Registrar<RecipeType<?>> recipeTypes = Estrogen.MANAGER.get().get(Registries.RECIPE_TYPE);

    private final ResourceLocation id;
    private final RecipeSerializer<?> serializerObject;
    @Nullable
    private final RecipeType<?> typeObject;
    private final Supplier<RecipeType<?>> type;

    EstrogenRecipes(Supplier<RecipeSerializer<?>> serializerSupplier, Supplier<RecipeType<?>> typeSupplier, boolean registerType) {
        String name = Lang.asId(name());
        id = Create.asResource(name);
        serializerObject = recipeSerializers.register(Estrogen.id(name), () -> serializerSupplier.get()).get();
        if (registerType) {
            typeObject = typeSupplier.get();
            recipeTypes.register(id, () -> typeObject);
            type = typeSupplier;
        } else {
            typeObject = null;
            type = typeSupplier;
        }
    }

    EstrogenRecipes(Supplier<RecipeSerializer<?>> serializerSupplier) {
        String name = Lang.asId(name());
        id = id(name);
        serializerObject = recipeSerializers.register(Estrogen.id(name), () -> serializerSupplier.get()).get();
        typeObject = simpleType(id);
        recipeTypes.register(id, () -> typeObject);
        type = () -> typeObject;
    }

    EstrogenRecipes(ProcessingRecipeBuilder.ProcessingRecipeFactory<?> processingFactory) {
        this(() -> new ProcessingRecipeSerializer<>(processingFactory));
    }

    public static <T extends Recipe<?>> RecipeType<T> simpleType(ResourceLocation id) {
        String stringId = id.toString();
        return new RecipeType<T>() {
            @Override
            public String toString() {
                return stringId;
            }
        };
    }

    public static void register() {}

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeSerializer<?>> T getSerializer() {
        return (T) serializerObject;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeType<?>> T getType() {
        return (T) type.get();
    }

    public <C extends Inventory, T extends Recipe<C>> Optional<T> find(C inv, Level world) {
        return world.getRecipeManager()
                .getRecipeFor(getType(), inv, world);
    }

    public static boolean shouldIgnoreInAutomation(Recipe<?> recipe) {
        RecipeSerializer<?> serializer = recipe.getSerializer();
        if (serializer != null)
            return true;
        return recipe.getId()
                .getPath()
                .endsWith("_manual_only");
    }
}
