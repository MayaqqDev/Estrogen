package dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.flywheel;

import com.jozufozu.flywheel.api.struct.Batched;
import com.jozufozu.flywheel.api.struct.Instanced;
import com.jozufozu.flywheel.api.struct.StructType;
import com.jozufozu.flywheel.api.struct.StructWriter;
import com.jozufozu.flywheel.backend.gl.buffer.VecBuffer;
import com.jozufozu.flywheel.backend.gl.shader.GlShader;
import com.jozufozu.flywheel.core.layout.BufferLayout;
import com.jozufozu.flywheel.core.layout.CommonItems;
import com.jozufozu.flywheel.core.model.ModelTransformer;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class DreamType implements Instanced<DreamData>, Batched<DreamData> {

    public static final StructType<DreamData> INSTANCE = new DreamType();
    public static final BufferLayout LAYOUT = BufferLayout.builder()
        .addItems(CommonItems.LIGHT, CommonItems.RGBA, CommonItems.VEC3)
        .build();

    @Override
    public void transform(DreamData d, ModelTransformer.Params b) {
    }

    @Override
    public @NotNull StructWriter<DreamData> getWriter(@NotNull VecBuffer backing) {
        return new UnsafeDreamWriter(backing, this);
    }

    @Override
    public @NotNull ResourceLocation getProgramSpec() {
        return Estrogen.id("dream");
    }

    @Override
    public @NotNull DreamData create() {
        return new DreamData();
    }

    @Override
    public @NotNull BufferLayout getLayout() {
        return LAYOUT;
    }
}
