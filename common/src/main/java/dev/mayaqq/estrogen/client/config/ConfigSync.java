package dev.mayaqq.estrogen.client.config;

import dev.mayaqq.estrogen.config.ChestConfig;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.networking.EstrogenNetworkManager;
import dev.mayaqq.estrogen.networking.messages.c2s.SetChestConfigPacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.config.ModConfig;

public class ConfigSync {
    private static ChestConfig cache;

    public static void cacheConfig() {
        ConfigSync.cache = currentConfig();
    }

    public static void onLoad(ModConfig modConfig) {
        if (EstrogenConfig.client().specification == modConfig.getSpec()) {
            cacheConfig();
        }
    }

    public static void onReload(ModConfig modConfig) {
        if (EstrogenConfig.client().specification == modConfig.getSpec()) {
            ChestConfig config = currentConfig();
            if (!ConfigSync.cache.equals(config)) {
                sendConfig(config);
                ConfigSync.cache = config;
            }
        }
    }

    private static ChestConfig currentConfig() {
        return new ChestConfig(
                EstrogenConfig.client().chestFeature.get(),
                EstrogenConfig.client().chestArmor.get(),
                EstrogenConfig.client().chestPhysics.get(),
                EstrogenConfig.client().chestBounciness.get().floatValue(),
                EstrogenConfig.client().chestDamping.get().floatValue()
        );
    }

    private static void sendConfig(ChestConfig config) {
        if (Minecraft.getInstance().getConnection() == null) return;
        EstrogenNetworkManager.CHANNEL.sendToServer(new SetChestConfigPacket(config));
    }

    public static void sendCurrentConfig() {
        EstrogenNetworkManager.CHANNEL.sendToServer(new SetChestConfigPacket(currentConfig()));
    }
}
