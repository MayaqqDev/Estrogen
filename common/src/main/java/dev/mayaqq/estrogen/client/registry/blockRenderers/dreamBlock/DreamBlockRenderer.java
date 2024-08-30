package dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.kinds.IdF;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import dev.mayaqq.estrogen.client.ShaderHelper;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.texture.DreamBlockTexture;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.texture.advanced.DynamicDreamTexture;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.registry.blockEntities.DreamBlockEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class DreamBlockRenderer extends SafeBlockEntityRenderer<DreamBlockEntity> {

    public DreamBlockRenderer(BlockEntityRendererProvider.Context context) {}

    public static boolean useAdvancedRenderer() {
        return switch (EstrogenConfig.client().advancedRendering.get()) {
            case ALWAYS -> true;
            case NEVER -> false;
            case DEFAULT -> !ShaderHelper.isShaderPackInUse();
        };
    }

    public void renderSafe(@NotNull DreamBlockEntity be, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        Matrix4f matrix4f = poseStack.last().pose();

        if (useAdvancedRenderer()) {
            if (be.getTexture() != null) be.setTexture(null);
            DynamicDreamTexture.INSTANCE.prepare();
            DynamicDreamTexture.setActive();
            this.renderCubeShader(be, matrix4f, multiBufferSource.getBuffer(DynamicDreamTexture.INSTANCE.getRenderType()));
        } else {
            if(be.getTexture() == null) be.setTexture(new DreamBlockTexture(be));
            DreamBlockTexture texture = be.getTexture();
            this.renderCube(texture, matrix4f, multiBufferSource.getBuffer(texture.getRenderType()));
            texture.animate(); // not good to call this each frame will optimize in the future
        }
    }


    private void renderCube(DreamBlockTexture texture, Matrix4f pose, VertexConsumer consumer) {
        texture.renderFace(pose, consumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH, true);
        texture.renderFace(pose, consumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH, true);
        texture.renderFace(pose, consumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST, true);
        texture.renderFace(pose, consumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST, true);
        texture.renderFace(pose, consumer, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN, true);
        texture.renderFace(pose, consumer, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP, true);
    }

    private void renderCubeShader(DreamBlockEntity blockEntity, Matrix4f pose, VertexConsumer consumer) {
        this.renderFaceShader(blockEntity, pose, consumer, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, Direction.SOUTH);
        this.renderFaceShader(blockEntity, pose, consumer, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, Direction.NORTH);
        this.renderFaceShader(blockEntity, pose, consumer, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, Direction.EAST);
        this.renderFaceShader(blockEntity, pose, consumer, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, Direction.WEST);
        this.renderFaceShader(blockEntity, pose, consumer, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, Direction.DOWN);
        this.renderFaceShader(blockEntity, pose, consumer, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, Direction.UP);
    }

    private void renderFaceShader(DreamBlockEntity blockEntity, Matrix4f pose, VertexConsumer consumer, float x0, float x1, float y0, float y1, float z0, float z1, float z2, float z3, Direction direction) {

        addInnerVertexShader(blockEntity, pose, consumer, x0, y0, z0);
        addInnerVertexShader(blockEntity, pose, consumer, x1, y0, z1);
        addInnerVertexShader(blockEntity, pose, consumer, x1, y1, z2);
        addInnerVertexShader(blockEntity, pose, consumer, x0, y1, z3);

        if (!blockEntity.isTouchingDreamBlock(direction)) {
            addOuterVertexShader(blockEntity, pose, consumer, x0, y1, z3);
            addOuterVertexShader(blockEntity, pose, consumer, x1, y1, z2);
            addOuterVertexShader(blockEntity, pose, consumer, x1, y0, z1);
            addOuterVertexShader(blockEntity, pose, consumer, x0, y0, z0);
        }
    }

    /**
     * Vertices for the inner faces, which will have the shader applied.
     * Vertices are moved when there are neighboring dream blocks, so that their interiors connect.
     */
    private void addInnerVertexShader(DreamBlockEntity blockEntity, Matrix4f pose, VertexConsumer consumer, float x, float y, float z) {
        // ternary nightmare
        float x2 = blockEntity.isTouchingDreamBlock(x > 0.5 ? Direction.EAST : Direction.WEST) ? x : x * 7f/8f + 1f/16f;
        float y2 = blockEntity.isTouchingDreamBlock(y > 0.5 ? Direction.UP : Direction.DOWN) ? y : y * 7f/8f + 1f/16f;
        float z2 = blockEntity.isTouchingDreamBlock(z > 0.5 ? Direction.SOUTH : Direction.NORTH) ? z : z * 7f/8f + 1f/16f;

        addVertexShader(pose, consumer, x2, y2, z2, false);
    }

    /**
     * Workaround to changing canOcclude() via config
     */
    private void addOuterVertexShader(DreamBlockEntity blockEntity, Matrix4f pose, VertexConsumer consumer, float x, float y, float z) {
        float x2 = x * 0.999f + 0.0005f;
        float y2 = y * 0.999f + 0.0005f;
        float z2 = z * 0.999f + 0.0005f;

        addVertexShader(pose, consumer, x2, y2, z2, true);
    }

    private void addVertexShader(Matrix4f pose, VertexConsumer consumer, float x, float y, float z, boolean isBorder) {
        consumer.vertex(pose, x, y, z);
        if (isBorder) {
            consumer.color(255, 255, 255, 255);
        } else {
            consumer.color(0, 0, 0, 0);
        }
        consumer.uv(0, 0)
            .uv2(LightTexture.FULL_BRIGHT)
            .normal(0, 0, 0)
            .endVertex();
    }

    public int getViewDistance() {
        return 256;
    }
}
