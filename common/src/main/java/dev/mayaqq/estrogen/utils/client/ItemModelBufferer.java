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
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemModelBufferer {

    private static final ThreadLocal<PoseStack> LOCAL_POSE = ThreadLocal.withInitial(PoseStack::new);

    public static BlockModel getModel(Level level, ItemStack stack, ItemDisplayContext context) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ShadeSeparatedBufferedData data = ModelUtil.getBufferedData((consumer, renderer, random) -> {
            PoseStack poseStack = LOCAL_POSE.get();
            BakedModel model = itemRenderer.getModel(stack, level, null, random.nextInt());

            poseStack.pushPose();
            model.getTransforms().getTransform(context).apply(false, poseStack);
            poseStack.translate(0.5f, 0.5f, 0.5f);
            itemRenderer.renderModelLists(model, stack, 0, OverlayTexture.NO_OVERLAY, poseStack, consumer);
            poseStack.popPose();

        });

        BlockModel model = new BlockModel(data, stack.getItem().toString());
        data.release();
        return model;
    }


    static class SingletBufferSource implements MultiBufferSource {

        private final VertexConsumer consumer;

        public SingletBufferSource(VertexConsumer consumer) {
            this.consumer = consumer;

        }

        @Override
        public VertexConsumer getBuffer(RenderType renderType) {
            return consumer;
        }
    }
}
