package dev.mayaqq.estrogen.integrations;

import com.unascribed.ears.api.EarsFeatureType;
import com.unascribed.ears.api.registry.EarsInhibitorRegistry;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.registry.EffectRegistry;
import net.minecraft.entity.player.PlayerEntity;

public class EarsCompat {
    public static void boob() {
        EarsInhibitorRegistry.register("estrogen", (part, peer) -> {
            PlayerEntity player = (PlayerEntity)peer;
            // I could simplify this if statement but this makes it much cleaner
            if (part == EarsFeatureType.CHEST && !player.hasStatusEffect(EffectRegistry.WOMAN_EFFECT) && EstrogenConfig.INSTANCE.enableNoBoobsWithEars) {
                return true;
            }
            return false;
        });
    }
}