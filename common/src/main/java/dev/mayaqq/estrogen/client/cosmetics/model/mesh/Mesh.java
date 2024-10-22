package dev.mayaqq.estrogen.client.cosmetics.model.mesh;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;

public interface Mesh {

    Mesh EMPTY = new Mesh() {
        @Override
        public void renderInto(VertexConsumer consumer, @NotNull PoseStack transform, int color, int light, int overlay) {}

        @Override
        public int vertexCount() {
            return 0;
        }

        @Override
        public String toString() {
            return "Empty Mesh";
        }
    };

    int STRIDE = 6;

    void renderInto(VertexConsumer consumer, @NotNull PoseStack transform, int color, int light, int overlay);

    int vertexCount();
}
