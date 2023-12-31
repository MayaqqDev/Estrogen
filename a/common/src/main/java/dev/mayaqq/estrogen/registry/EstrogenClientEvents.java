package dev.mayaqq.estrogen.registry;

import dev.mayaqq.estrogen.client.entity.player.features.boobs.BoobFeatureRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;

public class EstrogenClientEvents {
    public static void register() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer instanceof PlayerRenderer playerEntityRenderer) {
                registrationHelper.register(new BoobFeatureRenderer(playerEntityRenderer));
            }
        });
    }
}
