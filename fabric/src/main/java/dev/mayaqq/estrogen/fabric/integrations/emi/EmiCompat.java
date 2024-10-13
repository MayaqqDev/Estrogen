package dev.mayaqq.estrogen.fabric.integrations.emi;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.compat.emi.DoubleItemIcon;
import com.simibubi.create.content.fluids.VirtualFluid;
import com.simibubi.create.foundation.gui.menu.AbstractSimiContainerScreen;
import com.simibubi.create.foundation.item.TagDependentIngredientItem;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiRenderable;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.Bounds;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.fabric.integrations.emi.recipes.CentrifugingEmiRecipe;
import dev.mayaqq.estrogen.fabric.integrations.emi.recipes.EntityInteractionEmiRecipe;
import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.recipes.CentrifugingRecipe;
import dev.mayaqq.estrogen.registry.recipes.EntityInteractionRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@EmiEntrypoint
public class EmiCompat implements EmiPlugin {
    public static final Map<ResourceLocation, EmiRecipeCategory> ALL = new LinkedHashMap<>();

    public static final EmiRecipeCategory
            CENTRIFUGING = register("centrifuging", EmiStack.of(EstrogenBlocks.CENTRIFUGE.get())),
            ENTITY_INTERACTION = register("entity_interaction", DoubleItemIcon.of(Items.ZOMBIE_HEAD, AllItems.BRASS_HAND));

    public static boolean doInputsMatch(Recipe<?> a, Recipe<?> b) {
        if (!a.getIngredients().isEmpty() && !b.getIngredients().isEmpty()) {
            ItemStack[] matchingStacks = a.getIngredients().get(0).getItems();
            if (matchingStacks.length != 0) {
                if (b.getIngredients().get(0).test(matchingStacks[0])) {
                    return true;
                }
            }
        }
        return false;
    }

    private static EmiRecipeCategory register(String name, EmiRenderable icon) {
        ResourceLocation id = Estrogen.id(name);
        EmiRecipeCategory category = new EmiRecipeCategory(id, icon);
        ALL.put(id, category);
        return category;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void register(EmiRegistry registry) {
        registry.removeEmiStacks(s -> {
            Object key = s.getKey();
            if (key instanceof TagDependentIngredientItem tagDependent && tagDependent.shouldHide())
                return true;
            return key instanceof VirtualFluid;
        });

        registry.addGenericExclusionArea((screen, consumer) -> {
            if (screen instanceof AbstractSimiContainerScreen<?> simi) {
                simi.getExtraAreas().forEach(r -> consumer.accept(new Bounds(r.getX(), r.getY(), r.getWidth(), r.getHeight())));
            }
        });

        ALL.forEach((id, category) -> registry.addCategory(category));

        registry.addWorkstation(CENTRIFUGING, EmiStack.of(EstrogenBlocks.CENTRIFUGE.get()));

        addAll(registry, EntityInteractionRecipe.getRecipeTypeInfo(), EntityInteractionEmiRecipe::new);
        addAll(registry, CentrifugingRecipe.getRecipeTypeInfo(), CentrifugingEmiRecipe::new);
    }

    @SuppressWarnings("unchecked")
    private <T extends Recipe<?>> void addAll(EmiRegistry registry, IRecipeTypeInfo type, Function<T, EmiRecipe> constructor) {
        for (T recipe : (List<T>) registry.getRecipeManager().getAllRecipesFor(type.getType())) {
            registry.addRecipe(constructor.apply(recipe));
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends Recipe<?>> void addAll(EmiRegistry registry, AllRecipeTypes type, EmiRecipeCategory category, BiFunction<EmiRecipeCategory, T, EmiRecipe> constructor) {
        for (T recipe : (List<T>) registry.getRecipeManager().getAllRecipesFor(type.getType())) {
            registry.addRecipe(constructor.apply(category, recipe));
        }
    }
}
