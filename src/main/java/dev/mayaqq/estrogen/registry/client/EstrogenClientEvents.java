package dev.mayaqq.estrogen.registry.client;

import dev.mayaqq.estrogen.client.entity.player.features.boobs.BoobFeatureRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.PlayerEntityRenderer;

public class EstrogenClientEvents {
    public static void register() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer instanceof PlayerEntityRenderer playerEntityRenderer) {
                registrationHelper.register(new BoobFeatureRenderer(playerEntityRenderer));
            }
        });
    }
}
