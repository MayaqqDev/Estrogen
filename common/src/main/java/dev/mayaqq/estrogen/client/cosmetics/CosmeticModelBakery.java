package dev.mayaqq.estrogen.client.cosmetics;

import com.jozufozu.flywheel.util.RenderMath;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.mayaqq.estrogen.Estrogen;
import net.fabricmc.fabric.impl.client.indigo.renderer.helper.NormalHelper;
import net.minecraft.client.renderer.FaceInfo;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;
import java.util.Map;

public final class CosmeticModelBakery {

    public static final int STRIDE = 6;

    private static final float PACK = 127.0f;
    private static final float UNPACK = 1.0f / PACK;

    private CosmeticModelBakery() {}

    public static BakedCosmeticModel bake(List<BlockElement> elements) {
        int vertices = elements.stream().mapToInt(e -> e.faces.size()).sum() * 4;
        int[] vertexData = new int[vertices * STRIDE];
        Vector3f normal = new Vector3f();
        PoseStack transforms = new PoseStack();

        int index = 0;
        for(BlockElement element : elements) {
            float[] shape = setupShape(element.from, element.to);
            transforms.pushPose();

            BlockElementRotation rot = element.rotation;

            // IGNORE IDEA ADVICE rot can very much be null
            if(rot != null) {
                Vector3f origin = rot.origin();
                transforms.translate(origin.x, origin.y, origin.z);
                transforms.mulPose(fromDirectionAxis(rot.axis()).rotationDegrees(rot.angle()));
                transforms.translate(0 - origin.x, 0 - origin.y, 0 - origin.z);
            }

            for(Map.Entry<Direction, BlockElementFace> entry : element.faces.entrySet()) {
                Direction direction = entry.getKey();
                BlockElementFace face = entry.getValue();
                FaceInfo info = FaceInfo.fromFacing(direction);

                for(int i = 0; i < 4; i++) {
                    FaceInfo.VertexInfo vertex = info.getVertexInfo(i);
                    float x = shape[vertex.xFace];
                    float y = shape[vertex.yFace];
                    float z = shape[vertex.zFace];
                    normal.set(direction.getStepX(), direction.getStepY(), direction.getStepZ());
                    putVertex(transforms.last(), vertexData, index, x, y, z, face.uv, normal);
                    index++;
                }
            }

            transforms.popPose();
        }

        return new BakedCosmeticModel(vertexData, vertices);
    }

    private static void putVertex(PoseStack.Pose transform, int[] data, int index, float x, float y, float z, BlockFaceUV uv, Vector3f normal) {
        int pos = index * STRIDE;
        int quadIndex = index % 4;
        float u = uv.getU(quadIndex) / 16f;
        float v = uv.getV(quadIndex) / 16f;

        Vector4f position = new Vector4f(x, y, z, 1f);
        transform.pose().transform(position);
        transform.normal().transform(normal);

        data[pos] = Float.floatToIntBits(position.x);
        data[pos + 1] = Float.floatToIntBits(position.y);
        data[pos + 2] = Float.floatToIntBits(position.z);
        data[pos + 3] = Float.floatToIntBits(u);
        data[pos + 4] = Float.floatToIntBits(v);
        data[pos + 5] = packNormal(normal.x, normal.y, normal.z);
    }

    private static float[] setupShape(Vector3f min, Vector3f max) {
        float[] fs = new float[Direction.values().length];
        fs[FaceInfo.Constants.MIN_X] = min.x() / 16.0F;
        fs[FaceInfo.Constants.MIN_Y] = min.y() / 16.0F;
        fs[FaceInfo.Constants.MIN_Z] = min.z() / 16.0F;
        fs[FaceInfo.Constants.MAX_X] = max.x() / 16.0F;
        fs[FaceInfo.Constants.MAX_Y] = max.y() / 16.0F;
        fs[FaceInfo.Constants.MAX_Z] = max.z() / 16.0F;
        return fs;
    }

    private static Axis fromDirectionAxis(Direction.Axis input) {
        return switch (input) {
            case X -> Axis.XP;
            case Y -> Axis.YP;
            case Z -> Axis.ZP;
        };
    }


    public static int packNormal(float x, float y, float z) {
        x = Mth.clamp(x, -1, 1);
        y = Mth.clamp(y, -1, 1);
        z = Mth.clamp(z, -1, 1);

        return ((int) (x * PACK) & 0xFF) | (((int) (y * PACK) & 0xFF) << 8) | (((int) (z * PACK) & 0xFF) << 16);
    }

    public static float unpackNX(int packedNormal) {
        return ((byte) (packedNormal & 0xFF)) * UNPACK;
    }

    public static float unpackNY(int packedNormal) {
        return ((byte) ((packedNormal >>> 8) & 0xFF)) * UNPACK;
    }

    public static float unpackNZ(int packedNormal) {
        return ((byte) ((packedNormal >>> 16) & 0xFF)) * UNPACK;
    }
}
