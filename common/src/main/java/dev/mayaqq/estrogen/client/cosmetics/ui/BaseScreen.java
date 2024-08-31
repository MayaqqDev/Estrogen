package dev.mayaqq.estrogen.client.cosmetics.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.simibubi.create.foundation.gui.AbstractSimiScreen;
import com.simibubi.create.foundation.gui.UIRenderHelper;
import com.simibubi.create.foundation.gui.element.TextStencilElement;
import com.simibubi.create.foundation.gui.widget.BoxWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.MutableComponent;
import org.lwjgl.opengl.GL30;

public class BaseScreen extends AbstractSimiScreen {

    protected final Screen parent;

    public BaseScreen(Screen parent) {
        this.parent = parent;
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(parent);
    }

    @Override
    protected void renderWindow(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {

    }

    @Override
    protected void prepareFrame() {
        UIRenderHelper.swapAndBlitColor(minecraft.getMainRenderTarget(), UIRenderHelper.framebuffer);
        RenderSystem.clear(GL30.GL_STENCIL_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT, Minecraft.ON_OSX);
    }

    @Override
    protected void endFrame() {
        UIRenderHelper.swapAndBlitColor(UIRenderHelper.framebuffer, minecraft.getMainRenderTarget());
    }

    public static BoxWidget createTextBox(Font font, int x, int y, int width, int height, MutableComponent text) {
        BoxWidget widget = new BoxWidget(x, y, width, height);
        widget.showingElement(new TextStencilElement(font, text)
                .centered(true, true)
                .withElementRenderer(BoxWidget.gradientFactory.apply(widget))
        );
        return widget;
    }
}
