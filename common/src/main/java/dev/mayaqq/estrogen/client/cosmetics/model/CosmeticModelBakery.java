package dev.mayaqq.estrogen.client.cosmetics.model;

import com.mojang.math.Axis;
import com.teamresourceful.resourcefullib.common.exceptions.UtilityClassException;
import dev.mayaqq.estrogen.client.cosmetics.model.mesh.HierarchicalMesh;
import dev.mayaqq.estrogen.client.cosmetics.model.mesh.SimpleMesh;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.client.renderer.FaceInfo;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import org.joml.*;
import org.joml.Math;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

@ParametersAreNonnullByDefault
public final class CosmeticModelBakery {

    private static final float RESCALE_22_5 = 1.0F / (float)Math.cos(0.39269909262657166) - 1.0F;
    private static final float RESCALE_45 = 1.0F / (float)Math.cos(0.7853981852531433) - 1.0F;
    private static final float PACK = 127.0f;
    private static final float UNPACK = 1.0f / PACK;

    private CosmeticModelBakery() throws UtilityClassException {
        throw new UtilityClassException();
    }

    public static BakedCosmeticModel bake(PreparedModel model) {
        return model.hasGroups() ? bakeGrouped(model) : bakeSimple(model);
    }

    private static BakedCosmeticModel bakeSimple(PreparedModel model) {
        Vector3f minBound = new Vector3f(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
        Vector3f maxBound = new Vector3f();

        return new BakedCosmeticModel(bakeMesh(model.elements(), minBound, maxBound), minBound, maxBound);
    }

    private static BakedCosmeticModel bakeGrouped(PreparedModel model) {
        Vector3f minBound = new Vector3f(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
        Vector3f maxBound = new Vector3f();

        List<BlockElement> elements = model.elements();
        HierarchicalMesh.Builder builder = HierarchicalMesh.builder();
        IntSet ungrouped = IntStream.range(0, elements.size())
            .collect(() -> createIntSet(elements.size()), IntSet::add, IntSet::addAll);

        // Groups are stored hierarchically while elements aren't
        for(BlockElementGroup rootGroup : model.groups()) {
            builder.addChild(rootGroup.name(), compileGroup(rootGroup, index -> {
                ungrouped.remove(index);
                return elements.get(index);
            }, minBound, maxBound));
        }

        builder.data(bakeMesh(ungrouped.intStream().mapToObj(elements::get).toList(), minBound, maxBound));

        return new BakedCosmeticModel(builder.build(), minBound, maxBound);
    }

    private static HierarchicalMesh compileGroup(BlockElementGroup group, IntFunction<BlockElement> elementGetter, Vector3f minBoundMut,Vector3f maxBoundMut) {

        List<BlockElement> elements = group.elementIndices()
            .intStream()
            .mapToObj(elementGetter)
            .toList();

        HierarchicalMesh.Builder builder = HierarchicalMesh.builder()
            .data(bakeMesh(elements, minBoundMut, maxBoundMut))
            .origin(new Vector3f(group.origin()).div(16));

        for(BlockElementGroup subGroup : group.subGroups()) {
            builder.addChild(subGroup.name(), compileGroup(subGroup, elementGetter, minBoundMut, maxBoundMut));
        }

        return builder.build();

    }

    private static SimpleMesh bakeMesh(List<BlockElement> elements, @Nullable Vector3f minBoundMut, @Nullable Vector3f maxBoundMut) {
        int vertices = elements.stream().mapToInt(e -> e.faces.size()).sum() * 4;
        int[] vertexData = new int[vertices * BakedCosmeticModel.STRIDE];

        // Reusing ALL the instances
        Vector4f position = new Vector4f();
        Vector3f normal = new Vector3f();
        Matrix4f modelMat = new Matrix4f();
        Matrix3f normalMat = new Matrix3f();

        int index = 0;
        for(BlockElement element : elements) {

            float[] shape = setupShape(element.from, element.to);
            BlockElementRotation rot = element.rotation;

            // IGNORE IDEA ADVICE rot can very much be null
            //noinspection ConstantValue
            if (rot != null) applyElementRotation(rot, modelMat, normalMat);

            for (Map.Entry<Direction, BlockElementFace> entry : element.faces.entrySet()) {
                Direction direction = entry.getKey();
                BlockFaceUV uv = entry.getValue().uv;
                FaceInfo face = FaceInfo.fromFacing(direction);

                for (int i = 0; i < 4; i++) {
                    FaceInfo.VertexInfo vertex = face.getVertexInfo(i);
                    position.set(shape[vertex.xFace], shape[vertex.yFace], shape[vertex.zFace], 1f);
                    normal.set(direction.getStepX(), direction.getStepY(), direction.getStepZ());
                    modelMat.transform(position);
                    normalMat.transform(normal);

                    // Update the model bounds
                    if(minBoundMut != null) minBoundMut.set(Math.min(minBoundMut.x, position.x), Math.min(minBoundMut.y, position.y), Math.min(minBoundMut.z, position.z));
                    if(maxBoundMut != null) maxBoundMut.set(Math.max(maxBoundMut.x, position.x), Math.max(maxBoundMut.y, position.y), Math.max(maxBoundMut.z, position.z));

                    putVertex(vertexData, index, i, position, uv, normal);
                    index++;
                }
            }

            modelMat.identity();
            normalMat.identity();
        }

        return new SimpleMesh(vertexData, vertices);

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

    private static void applyElementRotation(BlockElementRotation rotation, Matrix4f modelMat, Matrix3f normalMat) {
        Vector3f origin = rotation.origin();
        modelMat.translate(origin.x / 16f, origin.y / 16f, origin.z / 16f);

        Quaternionf quat = axisRotation(rotation.axis(), rotation.angle());
        modelMat.rotate(quat);
        normalMat.rotate(quat);

        if (rotation.rescale()) {
            Vector3f scale = getRescaleVector(rotation.axis());
            scale.mul((Math.abs(rotation.angle()) == 22.5f) ? RESCALE_22_5 : RESCALE_45);
            modelMat.scale(scale.x, scale.y, scale.z);

            float nx = 1.0f / scale.x;
            float ny = 1.0f / scale.y;
            float nz = 1.0f / scale.z;
            float i = Mth.fastInvCubeRoot(nx * ny * nz);
            normalMat.scale(nx * i, ny * i, nz * i);
        }
        modelMat.translate(-(origin.x / 16f), -(origin.y / 16f), -(origin.z / 16f));
    }

    private static Vector3f getRescaleVector(Direction.Axis axis) {
        return switch (axis) {
            case X -> new Vector3f(0, 1f, 1f);
            case Y -> new Vector3f(1f, 0, 1f);
            case Z -> new Vector3f(1f, 1f, 0);
        };
    }

    private static Quaternionf axisRotation(Direction.Axis axis, float degrees) {
        return switch (axis) {
            case X -> Axis.XP.rotationDegrees(degrees);
            case Y -> Axis.YP.rotationDegrees(degrees);
            case Z -> Axis.ZP.rotationDegrees(degrees);
        };
    }

    private static void putVertex(int[] data, int index, int indexInFace, Vector4f position, BlockFaceUV uv, Vector3f normal) {
        int pos = index * BakedCosmeticModel.STRIDE;

        float u = uv.getU(indexInFace) / 16f;
        float v = uv.getV(indexInFace) / 16f;

        data[pos] = Float.floatToRawIntBits(position.x);
        data[pos + 1] = Float.floatToRawIntBits(position.y);
        data[pos + 2] = Float.floatToRawIntBits(position.z);
        data[pos + 3] = Float.floatToRawIntBits(u);
        data[pos + 4] = Float.floatToRawIntBits(v);
        data[pos + 5] = packNormal(normal.x, normal.y, normal.z);
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

    public static Vector3f unpackNormal(int packedNormal, Vector3f dest) {
        dest.set(unpackNX(packedNormal), unpackNY(packedNormal), unpackNZ(packedNormal));
        return dest;
    }

    public static IntSet createIntSet(int size) {
        return size > 4 ? new IntOpenHashSet() : new IntArraySet();
    }

}
