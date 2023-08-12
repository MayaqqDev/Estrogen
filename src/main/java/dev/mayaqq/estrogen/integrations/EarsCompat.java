package dev.mayaqq.estrogen.integrations;

import com.unascribed.ears.api.EarsFeatureType;
import com.unascribed.ears.api.registry.EarsInhibitorRegistry;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
import net.minecraft.entity.player.PlayerEntity;

public class EarsCompat {
    public static void boob() {
        EarsInhibitorRegistry.register("estrogen", (part, peer) -> part == EarsFeatureType.CHEST && ((PlayerEntity)peer).hasStatusEffect(EstrogenEffects.ESTROGEN_EFFECT));
    }
}