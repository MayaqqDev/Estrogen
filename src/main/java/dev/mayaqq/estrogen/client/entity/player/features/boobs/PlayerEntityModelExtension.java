package dev.mayaqq.estrogen.client.entity.player.features.boobs;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public interface PlayerEntityModelExtension {
    public void estrogen$renderBoobs(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, AbstractClientPlayerEntity player, float scale);
}
