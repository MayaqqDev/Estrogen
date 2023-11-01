package dev.mayaqq.estrogen.client.entity.player.features.boobs;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.random.RandomGenerator;
import org.joml.*;

import java.util.List;

@Environment(value=EnvType.CLIENT)
public final class BoobArmorRenderer {
    public static final float DEFAULT_SCALE = 1.0f;
    public float pivotX;
    public float pivotY;
    public float pivotZ;
    public float pitch;
    public float yaw;
    public float roll;
    public float scaleX = 1.0f;
    public float scaleY = 1.0f;
    public float scaleZ = 1.0f;
    public boolean visible = true;
    public boolean skipDraw;
    private final List<BoobArmorModel> models;
    private ModelTransform initalTransform = ModelTransform.NONE;

    public BoobArmorRenderer(List<BoobArmorModel> models) {
        this.models = models;
    }

    public ModelTransform getTransform() {
        return ModelTransform.of(this.pivotX, this.pivotY, this.pivotZ, this.pitch, this.yaw, this.roll);
    }

    public ModelTransform getInitialTransform() {
        return this.initalTransform;
    }

    public void setInitialTransform(ModelTransform initialTransform) {
        this.initalTransform = initialTransform;
    }

    public void resetTransform() {
        this.setTransform(this.initalTransform);
    }

    public void setTransform(ModelTransform rotationData) {
        this.pivotX = rotationData.pivotX;
        this.pivotY = rotationData.pivotY;
        this.pivotZ = rotationData.pivotZ;
        this.pitch = rotationData.pitch;
        this.yaw = rotationData.yaw;
        this.roll = rotationData.roll;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.scaleZ = 1.0f;
    }

    public void copyTransform(ModelPart part) {
        this.scaleX = part.scaleX;
        this.scaleY = part.scaleY;
        this.scaleZ = part.scaleZ;
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
        if (!this.skipDraw) {
            this.renderCuboids(matrices.peek(), vertices, light, overlay, red, green, blue, alpha);
        }
        matrices.pop();
    }

    public void rotate(MatrixStack matrix) {
        matrix.translate(this.pivotX / 16.0f, this.pivotY / 16.0f, this.pivotZ / 16.0f);
        if (this.pitch != 0.0f || this.yaw != 0.0f || this.roll != 0.0f) {
            matrix.multiply(new Quaternionf().rotationZYX(this.roll, this.yaw, this.pitch));
        }
        if (this.scaleX != 1.0f || this.scaleY != 1.0f || this.scaleZ != 1.0f) {
            matrix.scale(this.scaleX, this.scaleY, this.scaleZ);
        }
    }

    private void renderCuboids(MatrixStack.Entry entry, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        for (BoobArmorModel boobArmorModel : this.models) {
            boobArmorModel.renderCuboid(entry, vertexConsumer, light, overlay, red, green, blue, alpha);
        }
    }

    public BoobArmorModel getRandomCuboid(RandomGenerator random) {
        return this.models.get(random.nextInt(this.models.size()));
    }

    public boolean isEmpty() {
        return this.models.isEmpty();
    }

    public void translate(Vector3f vec) {
        this.pivotX += vec.x();
        this.pivotY += vec.y();
        this.pivotZ += vec.z();
    }

    public void rotate(Vector3f vec) {
        this.pitch += vec.x();
        this.yaw += vec.y();
        this.roll += vec.z();
    }

    public void scale(Vector3f vec) {
        this.scaleX += vec.x();
        this.scaleY += vec.y();
        this.scaleZ += vec.z();
    }

    @FunctionalInterface
    @Environment(value=EnvType.CLIENT)
    public static interface CuboidConsumer {
        public void visit(MatrixStack.Entry var1, String var2, int var3, BoobArmorModel var4);
    }

    @Environment(value=EnvType.CLIENT)
    public static class BoobArmorModel {
        private final ModelPart.Quad[] sides;

        public BoobArmorModel(int textureWidth, int textureHeight, float sizeX, float sizeY, float sizeZ, boolean mirror, float squishU, float squishV) {
            this.sides = new ModelPart.Quad[4];

            ModelPart.Vertex vertex = new ModelPart.Vertex(-4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            ModelPart.Vertex vertex2 = new ModelPart.Vertex(4.0F, 0.0F, 0.0F, 0.0F, 8.0F);
            ModelPart.Vertex vertex3 = new ModelPart.Vertex(4.0F, 1.08F, 1.68F, 8.0F, 8.0F);
            ModelPart.Vertex vertex4 = new ModelPart.Vertex(-4.0F, 1.08F, 1.68F, 8.0F, 0.0F);
            ModelPart.Vertex vertex5 = new ModelPart.Vertex(-4.0F, -1.68F, 1.68F, 0.0F, 0.0F);
            ModelPart.Vertex vertex6 = new ModelPart.Vertex(4.0F, -1.68F, 1.68F, 0.0F, 8.0F);
            float j = textureWidth;
            float k = (float)textureWidth + sizeZ;
            float l = (float)textureWidth + sizeZ + sizeX;
            float n = (float)textureWidth + sizeZ + sizeX + sizeZ;
            float p = textureHeight;
            float q = (float)textureHeight + sizeZ;
            float r = (float)textureHeight + sizeZ + sizeY;
            this.sides[2] = new ModelPart.Quad(new ModelPart.Vertex[]{vertex6, vertex5, vertex, vertex2}, k, p, l, q, squishU, squishV, mirror, Direction.DOWN);
            this.sides[1] = new ModelPart.Quad(new ModelPart.Vertex[]{vertex, vertex5, vertex5, vertex4}, j, q, k, r, squishU, squishV, mirror, Direction.WEST);
            this.sides[3] = new ModelPart.Quad(new ModelPart.Vertex[]{vertex2, vertex, vertex4, vertex3}, k, q, l, r, squishU, squishV, mirror, Direction.NORTH);
            this.sides[0] = new ModelPart.Quad(new ModelPart.Vertex[]{vertex6, vertex2, vertex3, vertex3}, l, q, n, r, squishU, squishV, mirror, Direction.EAST);
        }

        public void renderCuboid(MatrixStack.Entry entry, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
            Matrix4f matrix4f = entry.getModel();
            Matrix3f matrix3f = entry.getNormal();
            for (ModelPart.Quad quad : this.sides) {
                Vector3f vector3f = matrix3f.transform(new Vector3f(quad.direction));
                float f = vector3f.x();
                float g = vector3f.y();
                float h = vector3f.z();
                for (ModelPart.Vertex vertex : quad.vertices) {
                    float i = vertex.pos.x() / 16.0f;
                    float j = vertex.pos.y() / 16.0f;
                    float k = vertex.pos.z() / 16.0f;
                    Vector4f vector4f = matrix4f.transform(new Vector4f(i, j, k, 1.0f));
                    vertexConsumer.vertex(vector4f.x(), vector4f.y(), vector4f.z(), red, green, blue, alpha, vertex.u, vertex.v, overlay, light, f, g, h);
                }
            }
        }
    }
}

