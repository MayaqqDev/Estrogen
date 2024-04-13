package dev.mayaqq.estrogen.client.config;

import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.networking.EstrogenNetworkManager;
import dev.mayaqq.estrogen.networking.messages.c2s.SetChestConfigPacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.config.ModConfig;

public class ConfigSync {
    private static PreciseChestConfig cache;

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
            PreciseChestConfig config = currentConfig();
            if (!ConfigSync.cache.equals(config)) {
                sendConfig(config);
                ConfigSync.cache = config;
            }
        }
    }

    private static PreciseChestConfig currentConfig() {
        return new PreciseChestConfig(EstrogenConfig.client().chestFeature.get(), EstrogenConfig.client().chestArmor.get(), EstrogenConfig.client().chestPhysics.get(), EstrogenConfig.client().chestBounciness.get(), EstrogenConfig.client().chestDamping.get());
    }

    private static void sendConfig(PreciseChestConfig config) {
        if (Minecraft.getInstance().getConnection() != null) {
            EstrogenNetworkManager.CHANNEL.sendToServer(new SetChestConfigPacket(config.enabled(), config.armorEnabled(), config.physicsEnabled(), (float) config.bounciness(), (float) config.damping()));
        }
    }

    // Uses doubles for bounciness and damping instead of floats
    private record PreciseChestConfig(boolean enabled, boolean armorEnabled, boolean physicsEnabled, double bounciness, double damping) {}
}
