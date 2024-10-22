package dev.mayaqq.estrogen.client.cosmetics.model.mesh;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mayaqq.estrogen.client.cosmetics.model.animation.Animatable;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.Optionull;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

public class MeshTree implements Mesh, Animatable, Animatable.Provider {

    private float x;
    private float y;
    private float z;
    private float xRot;
    private float yRot;
    private float zRot;
    private float xScale = 1f;
    private float yScale = 1f;
    private float zScale = 1f;

    private final Vector3f origin;
    private final Mesh mesh;
    private final Map<String, MeshTree> children;

    public MeshTree(Mesh mesh, Vector3f origin, Map<String, MeshTree> children) {
        this.mesh = mesh;
        this.origin = origin;
        this.children = children;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void offsetPosition(Vector3fc offset) {
        x = offset.x();
        y = offset.y();
        z = offset.z();
    }

    @Override
    public void offsetRotation(Vector3fc offset) {
        xRot = offset.x();
        yRot = offset.y();
        zRot = offset.z();
    }

    @Override
    public void offsetScale(Vector3fc offset) {
        xScale = offset.x();
        yScale = offset.y();
        zScale = offset.z();
    }

    @Override
    public void reset() {
        x = y = z = 0;
        xRot = yRot = zRot = 0;
        xScale = yScale = zScale = 1f;
    }

    public void applyTransform(PoseStack poseStack) {
        poseStack.translate(x / 16f, y / 16f, z / 16f);
        poseStack.translate(origin.x, origin.y, origin.z);

        if(xRot != 0 || yRot != 0 || zRot != 0) {
            poseStack.mulPose(new Quaternionf().rotationXYZ(xRot, yRot, zRot));
        }
        if(xScale != 1 || yScale != 1 || zScale != 1) {
            poseStack.scale(xScale, yScale, zScale);
        }

        poseStack.translate(-origin.x, -origin.y, -origin.z);
    }

    @Override
    public void renderInto(VertexConsumer consumer, PoseStack transform, int color, int light, int overlay) {

        applyTransform(transform);
        mesh.renderInto(consumer, transform, color, light, overlay);

        for(MeshTree child : children.values()) {
            transform.pushPose();
            child.renderInto(consumer, transform, color, light, overlay);
            transform.popPose();
        }
    }

    public MeshTree getChild(String name) {
        return children.get(name);
    }

    public void traverse(BiConsumer<String, MeshTree> visitor) {
        traverse("root", visitor);
    }

    protected void traverse(String name, BiConsumer<String, MeshTree> visitor) {
        visitor.accept(name, this);
        for(Map.Entry<String, MeshTree> entry : children.entrySet()) {
            String childName = name + "/" + entry.getKey();
            entry.getValue().traverse(childName, visitor);
        }
    }

    public Optional<Animatable> getAny(String name) {
        return Optionull.mapOrElse(children.get(name), Optional::of, () ->
            children.entrySet().stream()
            .flatMap(entry -> entry.getValue().getAny(name).stream())
            .findAny());
    }

    @Override
    public int vertexCount() {
        return mesh.vertexCount();
    }

    public static class Builder {

        private Mesh mesh = Mesh.EMPTY;
        private Vector3f origin;

        private final Map<String, MeshTree> children = new Object2ObjectArrayMap<>();

        private Builder() {}

        public Builder mesh(Mesh mesh) {
            this.mesh = mesh;
            return this;
        }

        public Builder origin(Vector3f origin) {
            this.origin = origin;
            return this;
        }

        public Builder addChild(String name, MeshTree child) {
            children.put(name, child);
            return this;
        }

        public MeshTree build() {
            if(origin == null) origin = new Vector3f();
            return new MeshTree(mesh, origin, children);
        }

    }
}
