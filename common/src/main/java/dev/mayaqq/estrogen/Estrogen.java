package dev.mayaqq.estrogen;

import dev.mayaqq.estrogen.networking.EstrogenNetworkManager;
import dev.mayaqq.estrogen.registry.*;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uwu.serenity.critter.RegistryManager;

public class Estrogen {
    public static final String MOD_ID = "estrogen";

    public static final Logger LOGGER = LoggerFactory.getLogger("Estrogen");

    public static final RegistryManager REGISTRIES = RegistryManager.create(MOD_ID);

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static void init() {
        // Init all the different classes
        EstrogenAttributes.init();
        EstrogenDataSerializers.DATA_SERIALIZERS.init();
        EstrogenEntities.ENTITIES.register();
        EstrogenFluids.FLUIDS.register();
        EstrogenSounds.SOUNDS.register();
        EstrogenBlocks.BLOCKS.register();
        EstrogenBlockEntities.BLOCK_ENTITIES.register();
        EstrogenEffects.MOB_EFFECTS.register();
        EstrogenPotions.POTIONS.register();
        EstrogenEnchantments.ENCHANTMENTS.register();
        EstrogenItems.ITEMS.register();
        // Recipes need to be registered before completing the recipe registers
        EstrogenRecipes.RECIPE_TYPES.register();
        EstrogenRecipes.RECIPE_SERIALIZERS.register();
        EstrogenAdvancementCriteria.CRITERIAS.init();
        EstrogenParticles.PARTICLES.register();
        EstrogenCreativeTab.TAB.register();
        EstrogenNetworkManager.NETWORK_MANAGER.init();

        LOGGER.info("Injecting Estrogen into your veins!");
    }
}