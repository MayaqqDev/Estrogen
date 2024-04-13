package dev.mayaqq.estrogen.config;

import com.simibubi.create.foundation.config.ConfigBase;
import com.simibubi.create.foundation.config.ui.ConfigAnnotations;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class EstrogenConfig {
    public static final Map<ModConfig.Type, ConfigBase> CONFIGS = new EnumMap<>(ModConfig.Type.class);

    private static Client client;
    private static Common common;
    private static Server server;

    public static Client client() {
        return client;
    }

    public static Common common() {
        return common;
    }

    public static Server server() {
        return server;
    }

    public static ConfigBase byType(ModConfig.Type type) {
        return CONFIGS.get(type);
    }

    private static <T extends ConfigBase> T register(Supplier<T> factory, ModConfig.Type side) {
        Pair<T, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(builder -> {
            T config = factory.get();
            config.registerAll(builder);
            return config;
        });

        T config = specPair.getLeft();
        config.specification = specPair.getRight();
        CONFIGS.put(side, config);
        return config;
    }

    public static void register() {
        client = register(Client::new, ModConfig.Type.CLIENT);
        common = register(Common::new, ModConfig.Type.COMMON);
        server = register(Server::new, ModConfig.Type.SERVER);
    }

    public static void onLoad(ModConfig modConfig) {
        for (ConfigBase config : CONFIGS.values())
            if (config.specification == modConfig
                    .getSpec())
                config.onLoad();
    }

    public static void onReload(ModConfig modConfig) {
        for (ConfigBase config : CONFIGS.values())
            if (config.specification == modConfig
                    .getSpec())
                config.onReload();
    }

    public static class Client extends ConfigBase {

        // Global Chest Group
        public final ConfigGroup chestGlobal = group(1, "chestRenderingGlobal", "Global settings for the chest feature");
        public final ConfigBool chestFeatureRendering = b(true, "chestFeatureRendering", "Enable chest feature rendering");
        public final ConfigBool chestArmorRendering = b(true, "chestArmorRendering", "Enable chest feature armor rendering");
        public final ConfigBool chestPhysicsRendering = b(true, "chestPhysicsRendering", "Enable chest feature physics rendering");

        // Chest Config Group
        public final ConfigGroup chest = group(1, "chestEstrogen", "Settings for the chest feature (for the local player)");
        public final ConfigBool chestFeature = b(true, "chestFeature","Enable chest feature");
        public final ConfigBool chestArmor = b(true, "chestArmor", "Enable chest feature armor");
        public final ConfigBool chestPhysics = b(true, "chestPhysics", "Enable chest feature physics");
        public final ConfigFloat chestBounciness = f(0.27f, 0.0f, 1.0f, "chestBounciness", "Chest feature bounciness");
        public final ConfigFloat chestDamping = f(0.375f, 0.0f, 1.0f, "chestDamping", "Chest feature physics damping");

        // UI Config Group
        public final ConfigGroup ui = group(1, "ui", "UI element Configuration");
        public final ConfigBool dashOverlay = b(true, "dashOverlay", "Enable dash overlay");

        // Misc Config Group
        public final ConfigGroup misc = group(1, "misc", "Miscellaneous settings");
        public final ConfigBool entityPatting = b(true, "entityPatting", "Allows you to pat entities by shift right-clicking them!");

        // Accessory Config Group
        public final ConfigGroup accessory = group(1, "accessory", "Settings for Equippable Items");
        public final ConfigBool estrogenPatchRender = b(true, "estrogenPatchRender", "Render estrogen patches on the player");
      
        // Compat Config Group
        public final ConfigGroup compat = group(1, "compat", "Compatibility between other mods settings");
        public final ConfigBool ears = b(true, "ears", "Enable ears Compatibility");
        public final ConfigBool figura = b(true, "figura", "Enable figura Compatibility");

        public final ConfigGroup sounds = group(1, "sounds", "Settings for custom sounds and music");
        public final ConfigBool ambientMusic = b(true, "ambientMusic", "Enable ambient music which starts playing on specific moments");

        @Override
        public String getName() {
            return "client";
        }
    }

    public static class Common extends ConfigBase {
        // Minigame Group
        public final ConfigGroup minigame = group(1, "minigame", "Settings which are more fun and not fit for survival");
        public final ConfigBool minigameEnabled = b(false, "minigameEnabled", "Enable/Disable all minigame settings");
        public final ConfigBool permaDash = b(true, "permaDash", "Gives you permanent, unremovable Girl Power Effect");
        public final ConfigInt girlPowerLevel = i(0, 0, 255, "permaDashAmount", "The level of Girl Power Effect when Perma-Dash is enabled");

        @Override
        public String getName() {
            return "common";
        }
    }

    public static class Server extends ConfigBase {
        // Dash group
        public final ConfigGroup dash = group(1, "dash", "Settings for the dash effect");
        public final ConfigBool dashEnabled = b(true, "dashEnabled", "Enable dash from the Effect of Estrogen");
        public final ConfigInt dashDeltaModifier = i(2, 0, 100, "dashDeltaModifier", "The multiplier for the dash delta movement");

        // Patch group
        public final ConfigGroup patch = group(1, "patch", "Settings for the estrogen patches");
        public final ConfigInt patchGirlPowerAmount = i(1, 0, 255, "patchGirlPowerAmount", "The level of girlpower you get from Estrogen Patches");
        public final ConfigBool patchDrain = b(true, "patchDrain", "Enable the estrogen patches to drain");
        public final ConfigInt patchDrainAmount = i(72, 0, "patchDrainAmount", "The amount of ticks it takes for the estrogen patches to drain a millibucket");

        // Centrifuge group
        public final ConfigGroup centrifuge = group(1, "centrifuge", "Settings for the centrifuge");
        public final ConfigFloat centrifugeSpeedRequired = f(256, 0, 256, "centrifugeSpeedRequired", "Minimum speed required for the centrifuge to work", ConfigAnnotations.RequiresRestart.BOTH.asComment());

        @Override
        public String getName() {
            return "server";
        }
    }
}
