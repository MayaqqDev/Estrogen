package dev.mayaqq.estrogen.utils.client;

import com.jozufozu.flywheel.core.model.BlockModel;
import com.jozufozu.flywheel.core.model.ModelUtil;
import com.jozufozu.flywheel.core.model.ShadeSeparatedBufferedData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class ItemModelBufferer {

    public static BlockModel getModel(Level level, ItemStack stack, ItemDisplayContext context) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ShadeSeparatedBufferedData data = ModelUtil.getBufferedData((consumer, renderer, random) -> {
            PoseStack poseStack = new PoseStack();
            GlintFilteringBufferSource bufferSource = new GlintFilteringBufferSource(consumer);

            itemRenderer.renderStatic(stack, context,
                LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,
                poseStack, bufferSource, level, random.nextInt()
            );
        });

        BlockModel model = new BlockModel(data, stack.getItem().toString());
        data.release();
        return model;
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
                public void endVertex() {

                }

                @Override
                public void defaultColor(int defaultR, int defaultG, int defaultB, int defaultA) {

                }

                @Override
                public void unsetDefaultColor() {

                }
            };

        private static final List<RenderType> GLINT_TYPES = List.of(
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
                return (GLINT_TYPES.contains(renderType)) ? EMPTY : wrapped;
            }
        }
}
