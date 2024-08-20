package dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.flywheel;

import com.jozufozu.flywheel.api.struct.StructType;
import com.jozufozu.flywheel.backend.gl.buffer.VecBuffer;
import com.jozufozu.flywheel.core.materials.BasicWriterUnsafe;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.MemoryUtil;

public class UnsafeDreamWriter extends BasicWriterUnsafe<DreamData> {
    protected UnsafeDreamWriter(VecBuffer backingBuffer, StructType<DreamData> vertexType) {
        super(backingBuffer, vertexType);
    }

    @Override
    protected void writeInternal(@NotNull DreamData data) {
        super.writeInternal(data);
        long addr = writePointer;
        MemoryUtil.memPutFloat(addr + 6, data.posX);
        MemoryUtil.memPutFloat(addr + 10, data.posY);
        MemoryUtil.memPutFloat(addr + 14, data.posZ);
    }
}
