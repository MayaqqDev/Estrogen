package dev.mayaqq.estrogen.config;

import com.simibubi.create.foundation.config.ConfigBase;
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
        public final ConfigBool chestFeature = b(true, "chestFeature", "Enable chest feature");
        public final ConfigBool chestArmor = b(true, "chestArmor", "Enable chest feature armor");

        @Override
        public String getName() {
            return "client";
        }
    }

    public static class Common extends ConfigBase {
        public final ConfigBool placeholder = b(true, "placeholder", "Placeholder");

        @Override
        public String getName() {
            return "common";
        }
    }

    public static class Server extends ConfigBase {
        public final ConfigBool dashEnabled = b(true, "dashEnabled", "Enable dash from the Effect of Estrogen");

        @Override
        public String getName() {
            return "server";
        }
    }
}
