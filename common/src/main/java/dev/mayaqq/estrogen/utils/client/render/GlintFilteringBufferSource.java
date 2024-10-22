package dev.mayaqq.estrogen.utils.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;

import java.util.Set;

// This fixes visual bugs as enchantment glint isn't currently supported by flywheel
@Environment(EnvType.CLIENT)
record GlintFilteringBufferSource(VertexConsumer wrapped) implements MultiBufferSource {

    private static final VertexConsumer EMPTY_CONSUMER = new VertexConsumer() {
        @Override
        public VertexConsumer vertex(double x, double y, double z) {
            return this;
        }

        @Override
        public VertexConsumer color(int red, int green, int blue, int alpha) {
            return this;
        }

        @Override
        public VertexConsumer uv(float u, float v) {
            return this;
        }

        @Override
        public VertexConsumer overlayCoords(int u, int v) {
            return this;
        }

        @Override
        public VertexConsumer uv2(int u, int v) {
            return this;
        }

        @Override
        public VertexConsumer normal(float x, float y, float z) {
            return this;
        }

        @Override
        public void endVertex() {}

        @Override
        public void defaultColor(int defaultR, int defaultG, int defaultB, int defaultA) {}

        @Override
        public void unsetDefaultColor() {}

        @Override
        public void putBulkData(PoseStack.Pose poseEntry, BakedQuad quad, float red, float green, float blue, int combinedLight, int combinedOverlay) {}

        @Override
        public void putBulkData(PoseStack.Pose poseEntry, BakedQuad quad, float[] colorMuls, float red, float green, float blue, int[] combinedLights, int combinedOverlay, boolean mulColor) {}
    };

    private static final Set<RenderType> GLINT_TYPES = Set.of(
        RenderType.glint(),
        RenderType.glintDirect(),
        RenderType.glintTranslucent(),
        RenderType.entityGlint(),
        RenderType.entityGlintDirect(),
        RenderType.armorGlint(),
        RenderType.armorEntityGlint()
    );

    @Override
    public VertexConsumer getBuffer(RenderType renderType) {
        return GLINT_TYPES.contains(renderType) ? EMPTY_CONSUMER : wrapped;
    }
}
