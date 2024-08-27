package dev.mayaqq.estrogen.client.cosmetics;

import com.jozufozu.flywheel.core.model.*;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import static dev.mayaqq.estrogen.client.cosmetics.CosmeticModelBakery.*;

public class BakedCosmeticModel implements Bufferable {

    public static final BakedCosmeticModel EMPTY = new BakedCosmeticModel(new int[0], 0);

    private final int[] data;
    private final int vertexCount;

    public BakedCosmeticModel(int[] data, int vertexCount) {
        this.data = data;
        this.vertexCount = vertexCount;
    }

    @Override
    public void bufferInto(@NotNull VertexConsumer consumer, @Nullable ModelBlockRenderer renderer, @Nullable RandomSource random) {
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
        ShadeSeparatedBufferedData data = ModelUtil.getBufferedData(this);
        SuperByteBuffer sbb = new SuperByteBuffer(data);
        data.release();
        return sbb;
    }

}
