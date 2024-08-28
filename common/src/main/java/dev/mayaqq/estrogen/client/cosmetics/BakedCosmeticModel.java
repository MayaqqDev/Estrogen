package dev.mayaqq.estrogen.client.cosmetics;

import com.jozufozu.flywheel.core.model.*;
import com.mojang.blaze3d.vertex.*;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
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

    public void bufferInto(VertexConsumer consumer) {
        for(int vertex = 0; vertex < vertexCount; vertex++) {
            int pos = STRIDE * vertex;
            float x = Float.intBitsToFloat(data[pos]);
            float y = Float.intBitsToFloat(data[pos + 1]);
            float z = Float.intBitsToFloat(data[pos + 2]);
            float u = Float.intBitsToFloat(data[pos + 3]);
            float v = Float.intBitsToFloat(data[pos + 4]);
            int normal = data[pos + 5];

            // Need to use block vertex format if using bufferable, we ignore color and light (uv2)
            consumer.vertex(x, y, z).color(-1).uv(u, v).uv2(0)
                .normal(unpackNX(normal), unpackNY(normal), unpackNZ(normal)).endVertex();
        }
    }

    public SuperByteBuffer makeBuffer() {
        BufferBuilder builder = LOCAL_BUILDER.get();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK);
        bufferInto(builder);
        BufferBuilder.RenderedBuffer buffer = builder.end();
        SuperByteBuffer sbb = new SuperByteBuffer(buffer.vertexBuffer(), buffer.drawState());
        buffer.release();
        return sbb;
    }

}
