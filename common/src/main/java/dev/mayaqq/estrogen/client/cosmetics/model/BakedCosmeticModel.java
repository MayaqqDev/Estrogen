package dev.mayaqq.estrogen.client.cosmetics.model;

import com.jozufozu.flywheel.core.model.BlockModel;
import com.jozufozu.flywheel.core.model.Model;
import com.mojang.blaze3d.vertex.*;
import dev.mayaqq.estrogen.client.cosmetics.model.animation.AnimationDefinition;
import dev.mayaqq.estrogen.client.cosmetics.model.animation.Animations;
import dev.mayaqq.estrogen.client.cosmetics.model.mesh.MeshTree;
import dev.mayaqq.estrogen.client.cosmetics.model.mesh.Mesh;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.joml.Math;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class BakedCosmeticModel {

    private static final ThreadLocal<BufferBuilder> LOCAL_BUILDER = ThreadLocal.withInitial(() -> new BufferBuilder(512));

    protected final Mesh mesh;

    private final Vector3f minBound;
    private final Vector3f maxBound;

    private final Vector3f animCache = new Vector3f();

    public BakedCosmeticModel(Mesh mesh, Vector3f minBound, Vector3f maxBound) {
        this.mesh = mesh;
        this.minBound = minBound;
        this.maxBound = maxBound;
    }

    public void runAnimation(AnimationDefinition definition) {
        if(mesh instanceof MeshTree tree) {
            tree.reset();
            Animations.animate(tree, definition, animCache);
        }
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Vector3fc getMinBound() {
        return minBound;
    }

    public Vector3fc getMaxBound() {
        return maxBound;
    }

    /**
     * Get the model size in a 0-16 scale, the model size will never be smaller than 16x16x16
     * @return computed model size
     */
    public Vector3f computeModelSize() {
        Vector3f vec = new Vector3f(maxBound).sub(minBound);
        vec.set(Math.max(vec.x, 1f), Math.max(vec.y, 1f), Math.max(vec.z, 1f));
        vec.mul(16f);
        return vec;
    }

    // This exists incase you wanna use a cosmetic in an instance (im planning on possibly doing tha)
    public Model createModel() {
        BufferBuilder builder = LOCAL_BUILDER.get();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK);
        mesh.renderInto(builder, new PoseStack(), -1, 0, OverlayTexture.NO_OVERLAY);
        BufferBuilder.RenderedBuffer buffer = builder.end();
        BlockModel model = new BlockModel(buffer.vertexBuffer(), buffer.indexBuffer(), buffer.drawState(), 0, "cosmetic");
        buffer.release();
        return model;
    }

}
