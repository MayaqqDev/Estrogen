package dev.mayaqq.estrogen.client.cosmetics.model;

import com.jozufozu.flywheel.core.model.BlockModel;
import com.jozufozu.flywheel.core.model.Model;
import com.mojang.blaze3d.vertex.*;
import dev.mayaqq.estrogen.client.cosmetics.model.mesh.Mesh;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4f;

import static dev.mayaqq.estrogen.client.cosmetics.model.CosmeticModelBakery.*;

public class BakedCosmeticModel {

    public static final int STRIDE = 6;

    private static final ThreadLocal<BufferBuilder> LOCAL_BUILDER = ThreadLocal.withInitial(() -> new BufferBuilder(512));

    private final Mesh mesh;

    private final Vector3f minBound;
    private final Vector3f maxBound;

    public BakedCosmeticModel(Mesh mesh, Vector3f minBound, Vector3f maxBound) {
        this.mesh = mesh;
        this.minBound = minBound;
        this.maxBound = maxBound;
    }

    public void renderInto(VertexConsumer consumer, @NotNull PoseStack transform, int color, int light, int overlay) {
        mesh.renderInto(consumer, transform, color, light, overlay);
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
        renderInto(builder, new PoseStack(), -1, 0, OverlayTexture.NO_OVERLAY);
        BufferBuilder.RenderedBuffer buffer = builder.end();
        BlockModel model = new BlockModel(buffer.vertexBuffer(), buffer.indexBuffer(), buffer.drawState(), 0, "cosmetic");
        buffer.release();
        return model;
    }

}
