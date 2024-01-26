package dev.mayaqq.estrogen.fabric.integrations.rei.animated;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.rei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import dev.mayaqq.estrogen.registry.client.EstrogenRenderer;
import dev.mayaqq.estrogen.registry.common.EstrogenBlocks;
import net.minecraft.client.gui.components.events.GuiEventListener;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AnimatedCentrifuge extends AnimatedKinetics {

    @Override
    public void render(PoseStack matrixStack, int xOffset, int yOffset, float f) {
        matrixStack.pushPose();
        matrixStack.translate(xOffset, yOffset, 0);
        AllGuiTextures.JEI_SHADOW.render(matrixStack, -16, 13);
        matrixStack.translate(-2, 18, 0);
        int scale = 22;

        blockElement(EstrogenRenderer.CENTRIFUGE_COG)
                .rotateBlock(22.5, getCurrentAngle() * 2, 0)
                .scale(scale)
                .render(matrixStack);

        blockElement(EstrogenBlocks.CENTRIFUGE.getDefaultState())
                .rotateBlock(22.5, 22.5, 0)
                .scale(scale)
                .render(matrixStack);

        matrixStack.popPose();
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return null;
    }
}
