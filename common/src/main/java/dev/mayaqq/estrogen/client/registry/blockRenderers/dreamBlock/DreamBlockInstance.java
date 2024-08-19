package dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.core.model.BlockModel;
import com.jozufozu.flywheel.core.model.Model;
import com.mojang.blaze3d.vertex.*;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.flywheel.DreamData;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.flywheel.DreamType;
import dev.mayaqq.estrogen.registry.blockEntities.DreamBlockEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public class DreamBlockInstance extends BlockEntityInstance<DreamBlockEntity> implements DynamicInstance {
    protected DreamData data;

    public DreamBlockInstance(MaterialManager materialManager, DreamBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    public void init() {
        ResourceLocation dream_block = Estrogen.id("textures/dream.png");
        data = materialManager.transparent(RenderType.entityTranslucentCull(dream_block))
            .material(DreamType.INSTANCE)
            .model("dream_block", this::buildModel)
            .createInstance();

        data.setPosition(getInstancePosition());
    }

    protected Model buildModel() {
        BufferBuilder builder = new BufferBuilder(6 * 4 * DefaultVertexFormat.BLOCK.getVertexSize());

        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK);

        this.face(builder, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, Direction.SOUTH);
        this.face(builder, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, Direction.NORTH);
        this.face(builder, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, Direction.EAST);
        this.face(builder, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, Direction.WEST);
        this.face(builder, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, Direction.DOWN);
        this.face(builder, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, Direction.UP);

        BufferBuilder.RenderedBuffer buffer = builder.end();
        return new BlockModel(buffer.vertexBuffer(), buffer.indexBuffer(), buffer.drawState(), "dream_block");
    }

    private void face(VertexConsumer consumer, float x0, float x1, float y0, float y1, float z0, float z1, float z2, float z3, Direction direction) {
        consumer.vertex(x0, y0, z0).color(0xFFFFFFFF).uv(0, 0)
            .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(0, 1, 0).endVertex();
        consumer.vertex(x1, y0, z1).color(0xFFFFFFFF).uv(0, 0)
            .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(0, 1, 0).endVertex();
        consumer.vertex(x1, y1, z2).color(0xFFFFFFFF).uv(0, 0)
            .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(0, 1, 0).endVertex();
        consumer.vertex(x0, y1, z3).color(0xFFFFFFFF).uv(0, 0)
            .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(0, 1, 0).endVertex();
    }

    @Override
    protected void remove() {
        data.delete();
    }

    @Override
    public void beginFrame() {

    }
}
