package dev.mayaqq.estrogen.client;

import com.simibubi.create.foundation.block.connected.CTModel;
import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import dev.mayaqq.estrogen.client.config.ConfigSync;
import dev.mayaqq.estrogen.client.cosmetics.EstrogenCosmetics;
import dev.mayaqq.estrogen.client.registry.EstrogenKeybinds;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderer;
import dev.mayaqq.estrogen.integrations.ears.EarsCompat;
import dev.mayaqq.estrogen.registry.EstrogenPonderScenes;
import earth.terrarium.botarium.util.CommonHooks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resources.model.BakedModel;

public class EstrogenClient {
    public static void init() {
        EstrogenCosmetics.init();
        ConfigSync.cacheConfig();
        EstrogenRenderer.register();
        EstrogenPonderScenes.register();
        EstrogenKeybinds.register();

        // mod compat
        if (CommonHooks.isModLoaded("ears")) {
            EarsCompat.boob();
        }
    }

    @Environment(EnvType.CLIENT)
    public record CTModelProvider(ConnectedTextureBehaviour behavior) implements NonNullFunction<BakedModel, BakedModel> {
        @Override
        public BakedModel apply(BakedModel bakedModel) {
            return new CTModel(bakedModel, behavior);
        }
    }
}
