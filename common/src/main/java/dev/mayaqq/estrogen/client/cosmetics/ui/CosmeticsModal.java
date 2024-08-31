package dev.mayaqq.estrogen.client.cosmetics.ui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

public class CosmeticsModal extends BaseScreen {

    public CosmeticsModal(Screen parent) {
        super(parent);
    }

    @Override
    protected void renderWindowBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.parent.render(graphics, -1, -1, partialTicks);
        super.renderWindowBackground(graphics, mouseX, mouseY, partialTicks);
    }
}
