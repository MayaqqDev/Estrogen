package dev.mayaqq.estrogen.client.cosmetics;

import com.jozufozu.flywheel.core.model.BlockModel;
import com.jozufozu.flywheel.core.model.Model;
import com.mojang.blaze3d.vertex.*;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.jetbrains.annotations.Nullable;
import static dev.mayaqq.estrogen.client.cosmetics.CosmeticModelBakery.*;

public class BakedCosmeticModel {

    private static final ThreadLocal<BufferBuilder> LOCAL_BUILDER = ThreadLocal.withInitial(() -> new BufferBuilder(512));

    private final int[] data;
    private final int vertexCount;

    public BakedCosmeticModel(int[] data, int vertexCount) {
        this.data = data;
        this.vertexCount = vertexCount;
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

            if(transform == null) {
                consumer.vertex(x, y, z)
                    .color(color)
                    .uv(u, v)
                    .overlayCoords(overlay)
                    .uv2(light)
                    .normal(unpackNX(normal), unpackNY(normal), unpackNZ(normal))
                    .endVertex();
            } else {
                consumer.vertex(transform.pose(), x, y, z)
                    .color(color)
                    .overlayCoords(overlay)
                    .uv(u, v)
                    .uv2(light)
                    .normal(transform.normal(), unpackNX(normal), unpackNY(normal), unpackNZ(normal))
                    .endVertex();
            }
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
