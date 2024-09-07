package dev.mayaqq.estrogen.utils.client;

import com.jozufozu.flywheel.core.model.BlockModel;
import com.jozufozu.flywheel.core.model.ModelUtil;
import com.jozufozu.flywheel.core.model.ShadeSeparatedBufferedData;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.mayaqq.estrogen.platform.ClientPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemModelBufferer {

    public static BlockModel getModel(Level level, ItemStack stack, ItemDisplayContext context) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ShadeSeparatedBufferedData data = ModelUtil.getBufferedData((consumer, renderer, random) -> {
            PoseStack poseStack = new PoseStack();
            BakedModel model = itemRenderer.getModel(stack, level, null, random.nextInt());

            poseStack.pushPose();
            ClientPlatform.applyItemTransforms(model, context, poseStack);
            poseStack.translate(-0.5f, -0.5f, -0.5f);
            itemRenderer.renderModelLists(model, stack, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, poseStack, consumer);
            poseStack.popPose();

        });

        BlockModel model = new BlockModel(data, stack.getItem().toString());
        data.release();
        return model;
    }
}
