package dev.mayaqq.estrogen.fabric.integrations.rei.animated;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.rei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderer;
import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import net.minecraft.client.gui.GuiGraphics;

public class AnimatedCentrifuge extends AnimatedKinetics {

    @Override
    public void draw(GuiGraphics graphics, int xOffset, int yOffset) {
        PoseStack matrixStack = graphics.pose();
        matrixStack.pushPose();
        matrixStack.translate(xOffset, yOffset, 0);
        AllGuiTextures.JEI_SHADOW.render(graphics, -16, 13);
        matrixStack.translate(-2, 18, 0);
        int scale = 22;

        blockElement(EstrogenRenderer.CENTRIFUGE_COG)
                .rotateBlock(22.5, getCurrentAngle() * 2, 0)
                .scale(scale)
                .render(graphics);

        blockElement(EstrogenBlocks.CENTRIFUGE.get().defaultBlockState())
                .rotateBlock(22.5, 22.5, 0)
                .scale(scale)
                .render(graphics);

        matrixStack.popPose();
    }
}
