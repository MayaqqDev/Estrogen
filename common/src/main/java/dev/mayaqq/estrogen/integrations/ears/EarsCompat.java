package dev.mayaqq.estrogen.integrations.ears;

import com.unascribed.ears.api.EarsFeatureType;
import com.unascribed.ears.api.registry.EarsInhibitorRegistry;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.config.PlayerEntityExtension;
import dev.mayaqq.estrogen.utils.Boob;
import net.minecraft.world.entity.player.Player;

public class EarsCompat {
    // Disables the rendering of the chest feature from the mod "ears" when the player has the estrogen effect and has the config option for it enabled
    public static void boob() {
        EarsInhibitorRegistry.register("estrogen", (part, peer) ->
                EstrogenConfig.client().ears.get() &&
                part == EarsFeatureType.CHEST &&
                Boob.shouldShow((Player) peer) &&
                EstrogenConfig.client().chestFeatureRendering.get() &&
                ((PlayerEntityExtension) peer).estrogen$getChestConfig() != null &&
                ((PlayerEntityExtension) peer).estrogen$getChestConfig().enabled()
        );
    }
}