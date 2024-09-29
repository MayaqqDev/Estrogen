package dev.mayaqq.estrogen.client.cosmetics.model.mesh;

import com.jozufozu.flywheel.util.transform.Transform;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mayaqq.estrogen.client.cosmetics.model.animation.Animatable;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import org.joml.*;

import static dev.mayaqq.estrogen.client.cosmetics.model.CosmeticModelBakery.*;

public class TransformableMesh implements Mesh, Animatable {

    private final int[] data;
    private final int vertexCount;

    private float x;
    private float y;
    private float z;
    private float xRot;
    private float yRot;
    private float zRot;
    private float xScale = 1f;
    private float yScale = 1f;
    private float zScale = 1f;

    public TransformableMesh(int[] data, int vertexCount) {
        this.data = data;
        this.vertexCount = vertexCount;
    }

    @Override
    public void offsetPosition(Vector3fc offset) {
        x += offset.x();
        y += offset.y();
        z += offset.z();
    }

    @Override
    public void offsetRotation(Vector3fc offset) {
        xRot += offset.x();
        yRot += offset.y();
        zRot += offset.z();
    }

    @Override
    public void offsetScale(Vector3fc offset) {
        xScale += offset.x();
        yScale += offset.y();
        zScale += offset.z();
    }

    public void applyTransform(PoseStack poseStack) {
        poseStack.translate(x / 16f, y / 16f, z / 16f);

        if(xRot != 0 || yRot != 0 || zRot != 0) {
            poseStack.mulPose(new Quaternionf().rotationXYZ(xRot * Mth.DEG_TO_RAD, yRot * Mth.DEG_TO_RAD, zRot * Mth.DEG_TO_RAD));
        }
        if(xScale != 1 || yScale != 1 || zScale != 1) {
            poseStack.scale(xScale, yScale, zScale);
        }
    }

    @Override
    public void renderInto(VertexConsumer consumer, PoseStack transform, int color, int light, int overlay) {
        Vector4f posVec = new Vector4f();
        Vector3f normalVec = new Vector3f();
        applyTransform(transform);

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

            posVec.set(x, y, z, 1f);
            unpackNormal(normal, normalVec);

            posVec.mul(pose.pose());
            normalVec.mul(pose.normal());

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

    @Override
    public int vertexCount() {
        return vertexCount;
    }
}
