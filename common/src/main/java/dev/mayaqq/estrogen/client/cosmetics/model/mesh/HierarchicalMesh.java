package dev.mayaqq.estrogen.client.cosmetics.model.mesh;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mayaqq.estrogen.client.cosmetics.model.animation.Animatable;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import org.joml.Vector3f;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;

public class HierarchicalMesh extends TransformableMesh implements Animatable.Provider {

    private final Vector3f origin;
    private final Map<String, HierarchicalMesh> children;

    public HierarchicalMesh(int[] data, int vertexCount, Vector3f origin, Map<String, HierarchicalMesh> children) {
        super(data, vertexCount);
        this.origin = origin;
        this.children = children;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void applyTransform(PoseStack poseStack) {
        poseStack.translate(origin.x, origin.y, origin.z);
        super.applyTransform(poseStack);
        poseStack.translate(-origin.x, -origin.y, -origin.z);
    }

    @Override
    public void renderInto(VertexConsumer consumer, PoseStack transform, int color, int light, int overlay) {
        super.renderInto(consumer, transform, color, light, overlay);

        for(Mesh child : children.values()) {
            transform.pushPose();
            child.renderInto(consumer, transform, color, light, overlay);
            transform.popPose();
        }
    }

    public Mesh getChild(String name) {
        return children.get(name);
    }

    public void traverse(BiConsumer<String, HierarchicalMesh> visitor) {
        traverse("root", visitor);
    }

    protected void traverse(String name, BiConsumer<String, HierarchicalMesh> visitor) {
        visitor.accept(name, this);
        for(Map.Entry<String, HierarchicalMesh> entry : children.entrySet()) {
            String childName = name + "/" + entry.getKey();
            entry.getValue().traverse(childName, visitor);
        }
    }

    public Optional<Animatable> getAny(String name) {
        if(children.containsKey(name)) return Optional.of(children.get(name));
        return children.entrySet().stream()
            .flatMap(entry -> entry.getValue().getAny(name).stream())
            .findAny();
    }

    public static class Builder {

        private int[] data;
        private int vertexCount;
        private Vector3f origin;

        private final Map<String, HierarchicalMesh> children = new Object2ObjectArrayMap<>();

        private Builder() {}

        public Builder data(SimpleMesh source) {
            this.vertexCount = source.vertexCount();
            int length = source.data().length;
            this.data = new int[length];
            System.arraycopy(source.data(), 0, data, 0, length);
            return this;
        }

        public Builder data(int[] data, int vertexCount) {
            this.data = data;
            this.vertexCount = vertexCount;
            return this;
        }

        public Builder origin(Vector3f origin) {
            this.origin = origin;
            return this;
        }

        public Builder addChild(String name, HierarchicalMesh child) {
            children.put(name, child);
            return this;
        }

        public HierarchicalMesh build() {
            if(origin == null) origin = new Vector3f();
            Objects.requireNonNull(data, "No data set for this mesh");
            return new HierarchicalMesh(data, vertexCount, origin, children);
        }

    }
}
