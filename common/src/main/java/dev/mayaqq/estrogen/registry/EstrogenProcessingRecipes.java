package dev.mayaqq.estrogen.registry;

import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.foundation.utility.Lang;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.recipes.CentrifugingRecipe;
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
import static dev.mayaqq.estrogen.registry.EstrogenRecipeRegistries.RECIPE_SERIALIZERS;
import static dev.mayaqq.estrogen.registry.EstrogenRecipeRegistries.RECIPE_TYPES;

public enum EstrogenProcessingRecipes implements IRecipeTypeInfo {
    CENTRIFUGING(CentrifugingRecipe::new);

    private final ResourceLocation id;
    private final RegistryEntry<? extends RecipeSerializer<?>> serializerSupplier;
    @Nullable
    private final RecipeType<?> typeObject;
    private final Supplier<RecipeType<?>> type;

    EstrogenProcessingRecipes(Supplier<RecipeSerializer<?>> serializerSupplier, Supplier<RecipeType<?>> typeSupplier, boolean registerType) {
        String name = Lang.asId(name());
        id = Estrogen.id(name);
        this.serializerSupplier = registerSerializer(name, serializerSupplier);
        if (registerType) {
            typeObject = typeSupplier.get();
            registryEntry();
        } else {
            typeObject = null;
        }
        type = typeSupplier;
    }

    EstrogenProcessingRecipes(Supplier<RecipeSerializer<?>> serializerSupplier) {
        String name = Lang.asId(name());
        id = id(name);
        this.serializerSupplier = registerSerializer(name, serializerSupplier);
        typeObject = simpleType(id);
        registryEntry();
        type = () -> typeObject;
    }

    public static void register() {}

    public RegistryEntry<? extends RecipeSerializer<?>> registerSerializer(String name, Supplier<RecipeSerializer<?>> serializerSupplier) {
        return RECIPE_SERIALIZERS.register(name, serializerSupplier);
    }

    public void registryEntry() {
        RECIPE_TYPES.register(id.getPath(), () -> typeObject);
    }

    EstrogenProcessingRecipes(ProcessingRecipeBuilder.ProcessingRecipeFactory<?> processingFactory) {
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

    public static boolean shouldIgnoreInAutomation(Recipe<?> recipe) {
        RecipeSerializer<?> serializer = recipe.getSerializer();
        if (serializer != null)
            return true;
        return recipe.getId()
                .getPath()
                .endsWith("_manual_only");
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeSerializer<?>> T getSerializer() {
        return (T) serializerSupplier.get();
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
}
