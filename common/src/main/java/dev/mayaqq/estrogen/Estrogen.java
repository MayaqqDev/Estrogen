package dev.mayaqq.estrogen;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import dev.mayaqq.estrogen.networking.EstrogenNetworkManager;
import dev.mayaqq.estrogen.registry.*;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Estrogen {
    public static final String MOD_ID = "estrogen";

    public static final Logger LOGGER = LoggerFactory.getLogger("Estrogen");

    // Used to register some of the registry objects, other is done by resourcefullib
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create("estrogen");

    static {
        REGISTRATE.setTooltipModifierFactory(item ->
                new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE)
                        .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
        );
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static void init() {
        // Init all the different classes
        EstrogenAttributes.ATTRIBUTES.init();
        EstrogenFluids.FLUIDS.init();
        EstrogenBlocks.BLOCKS.init();
        EstrogenBlockEntities.BLOCK_ENTITIES.init();
        EstrogenFluidProperties.FLUID_PROPERTIES.initialize();
        EstrogenEffects.MOB_EFFECTS.init();
        EstrogenPotions.POTIONS.init();
        EstrogenEnchantments.ENCHANTMENTS.init();
        EstrogenItems.ITEMS.init();
        EstrogenRecipeRegistries.RECIPE_SERIALIZERS.init();
        EstrogenRecipeRegistries.RECIPE_TYPES.init();
        EstrogenRecipes.register();
        EstrogenSounds.SOUNDS.init();
        EstrogenCreativeTab.init();
        EstrogenNetworkManager.register();
        EstrogenItems.registerTooltips();
        EstrogenBlocks.registerExtraProperties();
    }
}