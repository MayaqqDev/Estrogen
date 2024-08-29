package dev.mayaqq.estrogen.client.cosmetics.ui;

import com.simibubi.create.foundation.config.ui.ConfigScreen;
import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.gui.ScreenOpener;
import com.simibubi.create.foundation.gui.Theme;
import com.simibubi.create.foundation.gui.UIRenderHelper;
import com.simibubi.create.foundation.gui.element.TextStencilElement;
import com.simibubi.create.foundation.gui.widget.BoxWidget;
import com.simibubi.create.foundation.utility.Components;
import dev.mayaqq.estrogen.client.cosmetics.Cosmetic;
import dev.mayaqq.estrogen.client.cosmetics.service.CosmeticsApi;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class CosmeticsScreen extends ConfigScreen {

    BoxWidget clientConfigWidget;
    BoxWidget goBack;
    BoxWidget title;

    String clientTitle = "Login";

    public CosmeticsScreen(Screen parent) {
        super(parent);
        modID = "estrogen";
    }

    @Override
    protected void init() {
        super.init();

        List<Cosmetic> cosmetics = new ArrayList<>();
        for (String s : CosmeticsApi.getAvailableCosmetics()) {
            Cosmetic cosmetic = CosmeticsApi.getCosmetic(s);
            if (cosmetic == null) continue;
            cosmetics.add(cosmetic);
        }

        TextStencilElement clientText = new TextStencilElement(font, Components.literal(clientTitle)).centered(true, true);
        addRenderableWidget(clientConfigWidget = new BoxWidget(width / 2 - 100, height / 2 - 15 - 30, 200, 16).showingElement(clientText));
        clientConfigWidget.withCallback(() -> {

        });
        clientText.withElementRenderer(BoxWidget.gradientFactory.apply(clientConfigWidget));

        addRenderableWidget(title);

        goBack = new BoxWidget(width / 2 - 134, height / 2, 20, 20).withPadding(2, 2)
                .withCallback(() -> linkTo(parent));
        goBack.showingElement(AllIcons.I_CONFIG_BACK.asStencil()
                .withElementRenderer(BoxWidget.gradientFactory.apply(goBack)));
        goBack.getToolTip()
                .add(Components.literal("Go Back"));
        addRenderableWidget(goBack);
    }

    @Override
    protected void renderWindow(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.drawCenteredString(font, "Login", width / 2, height / 2 - 105, Theme.i(Theme.Key.TEXT_ACCENT_STRONG));
    }

    private void linkTo(Screen screen) {
        ScreenOpener.open(screen);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers))
            return true;
        if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
            linkTo(parent);
        }
        return false;
    }
}
