package dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock;

import com.mojang.blaze3d.vertex.*;
import com.simibubi.create.CreateClient;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.render.SuperByteBufferCache;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderer;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.texture.DreamBlockTexture;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.texture.DynamicDreamTexture;
import dev.mayaqq.estrogen.registry.blockEntities.DreamBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;

// Code Saved for a future rewrite (keeping the same look mostly) of the cpu dream block renderer

public class DreamBlockRenderer extends SafeBlockEntityRenderer<DreamBlockEntity> {

    public DreamBlockRenderer(BlockEntityRendererProvider.Context context) {}

    public void renderSafe(@NotNull DreamBlockEntity be, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        // if(Backend.canUseInstancing(be.getLevel())) return;

        Matrix4f matrix4f = poseStack.last().pose();
        if(be.getTexture() == null) be.setTexture(new DreamBlockTexture(be));

        DreamBlockTexture texture = be.getTexture();
        this.renderCube(be, poseStack, multiBufferSource.getBuffer(DynamicDreamTexture.INSTANCE.getRenderType()));
        texture.animate(); // not good to call this each frame will optimize in the future
    }

    private void renderCube(DreamBlockEntity be, PoseStack ms, VertexConsumer consumer) {
        SuperByteBuffer buffer = CreateClient.BUFFER_CACHE.get(EstrogenRenderer.DREAM_BLOCKS, be, () -> buildCube(be));
        buffer.renderInto(ms, consumer);
    }

    private SuperByteBuffer buildCube(DreamBlockEntity entity) {
        RandomSource randomSource = Minecraft.getInstance().level.getRandom();
        int uOffset = randomSource.nextInt(0, 128);
        int vOffset = randomSource.nextInt(0, 128);

        float minU = uOffset / 128f;
        float minV = vOffset / 128f;
        float maxU = (uOffset + 16) / 128f;
        float maxV = (vOffset + 16) / 128f;

        BufferBuilder builder = new BufferBuilder(6 * 4 * DefaultVertexFormat.BLOCK.getVertexSize());

        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK);

        this.face(entity, builder, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, minU, minV, maxU, maxV, Direction.SOUTH);
        this.face(entity, builder, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, minU, minV, maxU, maxV, Direction.NORTH);
        this.face(entity, builder, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, minU, minV, maxU, maxV, Direction.EAST);
        this.face(entity, builder, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, minU, minV, maxU, maxV, Direction.WEST);
        this.face(entity, builder, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, minU, minV, maxU, maxV, Direction.DOWN);
        this.face(entity, builder, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, minU, minV, maxU, maxV, Direction.UP);

        BufferBuilder.RenderedBuffer buffer = builder.end();
        return new SuperByteBuffer(buffer.vertexBuffer(), buffer.drawState());
    }

    private void face(DreamBlockEntity be, VertexConsumer consumer, float x0, float x1, float y0, float y1, float z0, float z1, float z2, float z3, float minU, float minV, float maxU, float maxV, Direction direction) {
        if(be.isTouchingDreamBlock(direction)) return;
        Vector3f normal = new Vector3f(0, 1, 0);
        normal.rotate(direction.getRotation()).normalize();

        addVertex(consumer, x0, y0, z0, minU, minV, normal);
        addVertex(consumer, x1, y0, z1, maxU, minV, normal);
        addVertex(consumer, x1, y1, z2, maxU, maxV, normal);
        addVertex(consumer, x0, y1, z3, minU, maxV, normal);
    }

    private void addVertex(VertexConsumer consumer, float x, float y, float z, float u, float v, Vector3f normal) {
        consumer.vertex(x, y, z).color(0xffffffff).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY)
            .uv2(LightTexture.FULL_BRIGHT).normal(normal.x, normal.y, normal.z).endVertex();
    }

    protected float getOffsetUp() {
        return 1.0F;
    }

    protected float getOffsetDown() {
        return 0.0F;
    }

    public int getViewDistance() {
        return 256;
    }
}
