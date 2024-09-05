package dev.mayaqq.estrogen;

import dev.mayaqq.estrogen.networking.EstrogenNetworkManager;
import dev.mayaqq.estrogen.registry.*;
import dev.mayaqq.estrogen.registry.items.ThighHighsItem;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Estrogen {
    public static final String MOD_ID = "estrogen";

    public static final Logger LOGGER = LoggerFactory.getLogger("Estrogen");

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static void init() {
        // Init all the different classes
        EstrogenAttributes.ATTRIBUTES.register();
        EstrogenDataSerializers.DATA_SERIALIZERS.init();
        EstrogenEntities.ENTITIES.register();
        EstrogenFluids.FLUIDS.init();
        EstrogenSounds.SOUNDS.register();
        EstrogenBlocks.BLOCKS.register();
        EstrogenBlockEntities.BLOCK_ENTITIES.register();
        EstrogenFluidProperties.FLUID_PROPERTIES.initialize();
        EstrogenEffects.MOB_EFFECTS.register();
        EstrogenPotions.POTIONS.register();
        EstrogenEnchantments.ENCHANTMENTS.register();
        EstrogenItems.ITEMS.register();
        EstrogenItems.BUCKETS.init();
        EstrogenRecipes.RECIPES.init();
        EstrogenRecipeRegistries.RECIPE_TYPES.register();
        EstrogenRecipeRegistries.RECIPE_SERIALIZERS.register();
        EstrogenAdvancementCriteria.CRITERIAS.init();
        EstrogenParticles.PARTICLES.register();
        EstrogenCreativeTab.TAB.register();
        EstrogenNetworkManager.NETWORK_MANAGER.init();
        EstrogenProcessingRecipes.register();

        LOGGER.info("Injecting Estrogen into your veins!");
    }

    public static void postInit() {
        EstrogenPotatoProjectiles.register();
        CauldronInteraction.WATER.put(EstrogenItems.THIGH_HIGHS.get(), ThighHighsItem.CAULDRON_INTERACTION);
    }
}