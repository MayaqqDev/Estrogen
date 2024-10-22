package dev.mayaqq.estrogen.client.cosmetics.model.mesh;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static dev.mayaqq.estrogen.client.cosmetics.model.CosmeticModelBakery.*;

public record SimpleMesh(int[] data, int vertexCount) implements Mesh {

    @Override
    public void renderInto(VertexConsumer consumer, @NotNull PoseStack transform, int color, int light, int overlay) {
        Vector4f position = new Vector4f();
        Vector3f normal = new Vector3f();

        PoseStack.Pose pose = transform.last();

        for(int vertex = 0; vertex < vertexCount; vertex++) {
            int pos = STRIDE * vertex;
            float x = Float.intBitsToFloat(data[pos]);
            float y = Float.intBitsToFloat(data[pos + 1]);
            float z = Float.intBitsToFloat(data[pos + 2]);
            float u = Float.intBitsToFloat(data[pos + 3]);
            float v = Float.intBitsToFloat(data[pos + 4]);
            int packedNormal = data[pos + 5];

            position.set(x, y, z, 1f);
            unpackNormal(packedNormal, normal);

            position.mul(pose.pose());
            normal.mul(pose.normal());

            consumer.vertex(position.x, position.y, position.z, (color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f,
                (color & 0xFF) / 255f, (color >>> 24) / 255f, u, v, overlay, light, normal.x, normal.y, normal.z);
        }
    }
}
