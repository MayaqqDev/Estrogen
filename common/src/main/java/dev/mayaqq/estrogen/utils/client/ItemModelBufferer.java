package dev.mayaqq.estrogen.utils.client;

import com.jozufozu.flywheel.core.model.BlockModel;
import com.jozufozu.flywheel.core.model.ModelUtil;
import com.mojang.blaze3d.vertex.*;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import java.util.Set;

public class ItemModelBufferer {

    private static final ThreadLocal<ThreadLocalObjects> LOCAL_OBJECTS = ThreadLocal.withInitial(ThreadLocalObjects::new);

    public static BlockModel bufferModel(Level level, ItemStack stack, ItemDisplayContext context) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ThreadLocalObjects objects = LOCAL_OBJECTS.get();

        objects.begin();
        itemRenderer.renderStatic(stack, context,
            LightTexture.FULL_BRIGHT,
            OverlayTexture.NO_OVERLAY,
            objects.poseStack,
            objects.bufferSource,
            level, 42);

        return objects.end(stack.getItem().toString());
    }


    private static class ThreadLocalObjects {

        private final PoseStack poseStack = new PoseStack();
        private final BufferBuilder builder = new BufferBuilder(512);
        private final GlintFilteringBufferSource bufferSource = new GlintFilteringBufferSource(builder);

        public void begin() {
            poseStack.pushPose();
            builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK);
        }

        public BlockModel end(String name) {
            BufferBuilder.RenderedBuffer buffer = builder.end();

            // Item models are always shaded, but using the always-shaded overload breaks Iris FLW Compat
            BlockModel model = new BlockModel(buffer.vertexBuffer(), buffer.indexBuffer(),
                buffer.drawState(), buffer.drawState().vertexCount(), name);

            buffer.release();
            poseStack.popPose();
            return model;
        }

    }

    // This fixes visual bugs as enchantment glint isn't currently supported by flywheel
    private record GlintFilteringBufferSource(VertexConsumer wrapped) implements MultiBufferSource {

            private static final VertexConsumer EMPTY = new VertexConsumer() {
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
            return GLINT_TYPES.contains(renderType) ? EMPTY : wrapped;
        }
    }
}
