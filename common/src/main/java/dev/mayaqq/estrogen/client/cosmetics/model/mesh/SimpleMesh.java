package dev.mayaqq.estrogen.client.cosmetics.model.mesh;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static dev.mayaqq.estrogen.client.cosmetics.model.CosmeticModelBakery.*;

public record SimpleMesh(int[] data, int vertexCount) implements Mesh {

    @Override
    public void renderInto(VertexConsumer consumer, @NotNull PoseStack transform, int color, int light, int overlay) {
        Vector4f posVec = new Vector4f();
        Vector3f normalVec = new Vector3f();

        PoseStack.Pose pose = transform.last();

        for(int vertex = 0; vertex < vertexCount; vertex++) {
            int pos = STRIDE * vertex;
            float x = Float.intBitsToFloat(data[pos]);
            float y = Float.intBitsToFloat(data[pos + 1]);
            float z = Float.intBitsToFloat(data[pos + 2]);
            float u = Float.intBitsToFloat(data[pos + 3]);
            float v = Float.intBitsToFloat(data[pos + 4]);
            int normal = data[pos + 5];

            float nx, ny, nz;

            posVec.set(x, y, z, 1.0f).mul(pose.pose());
            unpackNormal(normal, normalVec).mul(pose.normal());
            x = posVec.x;
            y = posVec.y;
            z = posVec.z;
            nx = normalVec.x;
            ny = normalVec.y;
            nz = normalVec.z;

            consumer.vertex(x, y, z, (color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f,
                (color & 0xFF) / 255f, (color >>> 24) / 255f, u, v, overlay, light, nx, ny, nz);
        }
    }
}
