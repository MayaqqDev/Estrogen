package dev.mayaqq.estrogen.client.entity.player.features.boobs;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ArmorItem;
import org.jetbrains.annotations.Nullable;

public interface PlayerEntityModelExtension {
    public void estrogen$renderBoobs(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, AbstractClientPlayerEntity player, float scale);
    public void estrogen$renderBoobArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorItem item, boolean glint, boolean secondTextureLayer, float red, float green, float blue, @Nullable String overlay, AbstractClientPlayerEntity player, float size);
}
