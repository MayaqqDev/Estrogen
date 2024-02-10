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
        // Chest Config Group
        public final ConfigGroup chest = group(1, "chest", "Settings for the chest feature");
        public final ConfigBool chestFeature = b(true, "chestFeature", "Enable chest feature");
        public final ConfigBool chestArmor = b(true, "chestArmor", "Enable chest feature armor");

        // UI Config Group
        public final ConfigGroup ui = group(1, "ui", "UI element Configuration");
        public final ConfigBool dashOverlay = b(true, "dashOverlay", "Enable dash overlay");

        @Override
        public String getName() {
            return "client";
        }
    }

    public static class Common extends ConfigBase {
        // Estrogen Patches group
        public final ConfigGroup estrogenPatches = group(1, "estrogenPatches", "Settings for the estrogen patches");
        public final ConfigInt estrogenPatchesStackSize = i(1, 1, 64, "estrogenPatchesStackSize", "§4THIS ONLY WORKS ON §fFABRIC§7/§dQUILT§4 MOD LOADER", "The stack size limit of estrogen patches", ConfigAnnotations.RequiresRestart.BOTH.asComment());

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

        // Centrifuge group
        public final ConfigGroup centrifuge = group(1, "centrifuge", "Settings for the centrifuge");
        public final ConfigFloat centrifugeSpeedRequired = f(256, 0, 256, "centrifugeSpeedRequired", "Minimum speed required for the centrifuge to work", ConfigAnnotations.RequiresRestart.BOTH.asComment());

        @Override
        public String getName() {
            return "server";
        }
    }
}
