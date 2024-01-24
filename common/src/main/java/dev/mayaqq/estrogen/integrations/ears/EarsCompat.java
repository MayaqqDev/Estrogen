package dev.mayaqq.estrogen.integrations.ears;

import com.unascribed.ears.api.EarsFeatureType;
import com.unascribed.ears.api.registry.EarsInhibitorRegistry;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import net.minecraft.world.entity.player.Player;

public class EarsCompat {
    public static void boob() {
        EarsInhibitorRegistry.register("estrogen", (part, peer) -> part == EarsFeatureType.CHEST && ((Player) peer).hasEffect(EstrogenEffects.ESTROGEN_EFFECT) && EstrogenConfig.client().chestFeature.get());
    }
}