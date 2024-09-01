package dev.mayaqq.estrogen.client.cosmetics;

import com.jozufozu.flywheel.core.model.BlockModel;
import com.jozufozu.flywheel.core.model.Model;
import com.mojang.blaze3d.vertex.*;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import static dev.mayaqq.estrogen.client.cosmetics.CosmeticModelBakery.*;

public class BakedCosmeticModel {

    private static final ThreadLocal<BufferBuilder> LOCAL_BUILDER = ThreadLocal.withInitial(() -> new BufferBuilder(512));

    private final int[] data;
    private final int vertexCount;
    public final Vector3f minBound;
    public final Vector3f maxBound;

    public BakedCosmeticModel(int[] data, int vertexCount, Vector3f minBound, Vector3f maxBound) {
        this.data = data;
        this.vertexCount = vertexCount;
        this.minBound = minBound;
        this.maxBound = maxBound;
    }

    public void renderInto(VertexConsumer consumer, @Nullable PoseStack.Pose transform, int color, int light, int overlay) {
        for(int vertex = 0; vertex < vertexCount; vertex++) {
            int pos = STRIDE * vertex;
            float x = Float.intBitsToFloat(data[pos]);
            float y = Float.intBitsToFloat(data[pos + 1]);
            float z = Float.intBitsToFloat(data[pos + 2]);
            float u = Float.intBitsToFloat(data[pos + 3]);
            float v = Float.intBitsToFloat(data[pos + 4]);
            int normal = data[pos + 5];

            if(transform != null) {
                consumer.vertex(transform.pose(), x, y, z);
            } else consumer.vertex(x, y, z);

            consumer.color(color);
            consumer.uv(u, v);
            consumer.overlayCoords(overlay);
            consumer.uv2(light);

            if(transform != null) {
                consumer.normal(transform.normal(), unpackNX(normal), unpackNY(normal), unpackNZ(normal));
            } else consumer.normal(unpackNX(normal), unpackNY(normal), unpackNZ(normal));

            consumer.endVertex();
        }
    }

    // This exists incase you wanna use a cosmetic in an instance (im planning on possibly doing tha)
    public Model createModel() {
        BufferBuilder builder = LOCAL_BUILDER.get();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK);
        renderInto(builder, null, -1, 0, OverlayTexture.NO_OVERLAY);
        BufferBuilder.RenderedBuffer buffer = builder.end();
        BlockModel model = new BlockModel(buffer.vertexBuffer(), buffer.indexBuffer(), buffer.drawState(), "cosmetic");
        buffer.release();
        return model;
    }

}
