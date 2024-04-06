package dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.texture.DreamBlockTexture;
import dev.mayaqq.estrogen.registry.blockEntities.DreamBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class DreamBlockRenderer extends SafeBlockEntityRenderer<DreamBlockEntity> {

    public DreamBlockRenderer(BlockEntityRendererProvider.Context context) {}

    public void renderSafe(@NotNull DreamBlockEntity be, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        // if(Backend.canUseInstancing(be.getLevel())) return;

        Matrix4f matrix4f = poseStack.last().pose();
        if(be.getTexture() == null) be.setTexture(new DreamBlockTexture(be));

        DreamBlockTexture texture = be.getTexture();
        this.renderCube(texture, matrix4f, multiBufferSource.getBuffer(texture.getRenderType()));
        texture.animate(); // not good to call this each frame will optimize in the future
    }

    private void renderCube(DreamBlockTexture texture, Matrix4f pose, VertexConsumer consumer) {
        float f = this.getOffsetDown();
        float g = this.getOffsetUp();
        texture.renderFace(pose, consumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH, true);
        texture.renderFace(pose, consumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH, true);
        texture.renderFace(pose, consumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST, true);
        texture.renderFace(pose, consumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST, true);
        texture.renderFace(pose, consumer, 0.0F, 1.0F, f, f, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN, true);
        texture.renderFace(pose, consumer, 0.0F, 1.0F, g, g, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP, true);
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
