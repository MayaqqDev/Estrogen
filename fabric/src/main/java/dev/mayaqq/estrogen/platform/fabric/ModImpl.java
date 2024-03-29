package dev.mayaqq.estrogen.platform.fabric;

import dev.mayaqq.estrogen.platform.Mod;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;

import java.util.Optional;

public class ModImpl {
    public static Optional<Mod> getOptionalMod(String modid) {
        if (FabricLoader.getInstance().getModContainer(modid).orElse(null) != null) {
            ModMetadata metadata = FabricLoader.getInstance().getModContainer(modid).get().getMetadata();
            return Optional.of(new Mod(modid, metadata.getVersion().getFriendlyString(), metadata.getName(), metadata.getDescription()));
        }
        return Optional.empty();
    }
}