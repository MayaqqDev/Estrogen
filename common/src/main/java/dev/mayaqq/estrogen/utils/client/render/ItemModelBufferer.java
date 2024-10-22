package dev.mayaqq.estrogen.utils.client.render;

import com.jozufozu.flywheel.core.model.BlockModel;
import com.mojang.blaze3d.vertex.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Environment(EnvType.CLIENT)
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

}
