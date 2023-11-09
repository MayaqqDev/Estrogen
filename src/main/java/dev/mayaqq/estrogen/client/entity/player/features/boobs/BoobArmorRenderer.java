package dev.mayaqq.estrogen.client.entity.player.features.boobs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.*;

import java.util.List;

@Environment(value=EnvType.CLIENT)
public class BoobArmorRenderer {
    public float pivotX;
    public float pivotY;
    public float pivotZ;
    public float pitch;
    public float yaw;
    public float roll;
    public float xScale = 1.0f;
    public float yScale = 1.0f;
    public float zScale = 1.0f;
    public boolean visible = true;
    public boolean hidden;
    private final List<BoobArmorModel> models;

    public BoobArmorRenderer(List<BoobArmorModel> models) {
        this.models = models;
    }

    public ModelTransform getTransform() {
        return ModelTransform.of(this.pivotX, this.pivotY, this.pivotZ, this.pitch, this.yaw, this.roll);
    }

    public void setTransform(ModelTransform rotationData) {
        this.pivotX = rotationData.pivotX;
        this.pivotY = rotationData.pivotY;
        this.pivotZ = rotationData.pivotZ;
        this.pitch = rotationData.pitch;
        this.yaw = rotationData.yaw;
        this.roll = rotationData.roll;
        this.xScale = 1.0f;
        this.yScale = 1.0f;
        this.zScale = 1.0f;
    }

    public void copyTransform(ModelPart part) {
        this.xScale = part.xScale;
        this.yScale = part.yScale;
        this.zScale = part.zScale;
        this.pitch = part.pitch;
        this.yaw = part.yaw;
        this.roll = part.roll;
        this.pivotX = part.pivotX;
        this.pivotY = part.pivotY;
        this.pivotZ = part.pivotZ;
    }

    public void setPivot(float x, float y, float z) {
        this.pivotX = x;
        this.pivotY = y;
        this.pivotZ = z;
    }

    public void setAngles(float pitch, float yaw, float roll) {
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        this.render(matrices, vertices, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        if (!this.visible) {
            return;
        }
        if (this.models.isEmpty()) {
            return;
        }
        matrices.push();
        this.rotate(matrices);
        if (!this.hidden) {
            this.renderModels(matrices.peek(), vertices, light, overlay, red, green, blue, alpha);
        }
        matrices.pop();
    }

    public void rotate(MatrixStack matrices) {
        matrices.translate(this.pivotX / 16.0f, this.pivotY / 16.0f, this.pivotZ / 16.0f);
        if (this.roll != 0.0f) {
            matrices.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(this.roll));
        }
        if (this.yaw != 0.0f) {
            matrices.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(this.yaw));
        }
        if (this.pitch != 0.0f) {
            matrices.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(this.pitch));
        }
        if (this.xScale != 1.0f || this.yScale != 1.0f || this.zScale != 1.0f) {
            matrices.scale(this.xScale, this.yScale, this.zScale);
        }
    }

    private void renderModels(MatrixStack.Entry entry, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        for (BoobArmorModel model : this.models) {
            model.renderBoobArmorModel(entry, vertexConsumer, light, overlay, red, green, blue, alpha);
        }
    }

    public void translate(Vec3f vec3f) {
        this.pivotX += vec3f.getX();
        this.pivotY += vec3f.getY();
        this.pivotZ += vec3f.getZ();
    }

    public void rotate(Vec3f vec3f) {
        this.pitch += vec3f.getX();
        this.yaw += vec3f.getY();
        this.roll += vec3f.getZ();
    }

    public void scale(Vec3f vec3f) {
        this.xScale += vec3f.getX();
        this.yScale += vec3f.getY();
        this.zScale += vec3f.getZ();
    }

    @Environment(value=EnvType.CLIENT)
    public static class BoobArmorModel {
        private final ModelPart.Quad[] sides;

        public BoobArmorModel(int u, int v, float sizeX, float sizeY, float sizeZ, boolean mirror, float textureWidth, float textureHeight) {
            this.sides = new ModelPart.Quad[4];

            ModelPart.Vertex vertex = new ModelPart.Vertex(-4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            ModelPart.Vertex vertex2 = new ModelPart.Vertex(4.0F, 0.0F, 0.0F, 0.0F, 8.0F);
            ModelPart.Vertex vertex3 = new ModelPart.Vertex(4.0F, 1.08F, 1.68F, 8.0F, 8.0F);
            ModelPart.Vertex vertex4 = new ModelPart.Vertex(-4.0F, 1.08F, 1.68F, 8.0F, 0.0F);
            ModelPart.Vertex vertex5 = new ModelPart.Vertex(-4.0F, -1.68F, 1.68F, 0.0F, 0.0F);
            ModelPart.Vertex vertex6 = new ModelPart.Vertex(4.0F, -1.68F, 1.68F, 0.0F, 8.0F);

            float j = u;
            float k = (float)u + sizeZ;
            float l = (float)u + sizeZ + sizeX;
            float n = (float)u + sizeZ + sizeX + sizeZ;
            float p = v;
            float q = (float)v + sizeZ;
            float r = (float)v + sizeZ + sizeY;
            this.sides[2] = new ModelPart.Quad(new ModelPart.Vertex[]{vertex6, vertex5, vertex, vertex2}, k, p, l, q, textureWidth, textureHeight, mirror, Direction.DOWN);
            this.sides[1] = new ModelPart.Quad(new ModelPart.Vertex[]{vertex, vertex5, vertex5, vertex4}, j, q, k, r, textureWidth, textureHeight, mirror, Direction.WEST);
            this.sides[3] = new ModelPart.Quad(new ModelPart.Vertex[]{vertex2, vertex, vertex4, vertex3}, k, q, l, r, textureWidth, textureHeight, mirror, Direction.NORTH);
            this.sides[0] = new ModelPart.Quad(new ModelPart.Vertex[]{vertex6, vertex2, vertex3, vertex3}, l, q, n, r, textureWidth, textureHeight, mirror, Direction.EAST);
        }

        public void renderBoobArmorModel(MatrixStack.Entry entry, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
            Matrix4f matrix4f = entry.getPositionMatrix();
            Matrix3f matrix3f = entry.getNormalMatrix();
            for (ModelPart.Quad quad : this.sides) {
                Vec3f vec3f = quad.direction.copy();
                vec3f.transform(matrix3f);
                float f = vec3f.getX();
                float g = vec3f.getY();
                float h = vec3f.getZ();
                for (ModelPart.Vertex vertex : quad.vertices) {
                    float i = vertex.pos.getX() / 16.0f;
                    float j = vertex.pos.getY() / 16.0f;
                    float k = vertex.pos.getZ() / 16.0f;
                    Vector4f vector4f = new Vector4f(i, j, k, 1.0f);
                    vector4f.transform(matrix4f);
                    vertexConsumer.vertex(vector4f.getX(), vector4f.getY(), vector4f.getZ(), red, green, blue, alpha, vertex.u, vertex.v, overlay, light, f, g, h);
                }
            }
        }
    }
}
