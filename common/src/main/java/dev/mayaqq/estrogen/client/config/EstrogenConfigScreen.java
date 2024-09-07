package dev.mayaqq.estrogen.client.config;

import com.simibubi.create.foundation.config.ui.BaseConfigScreen;
import com.simibubi.create.foundation.gui.element.TextStencilElement;
import com.simibubi.create.foundation.gui.widget.BoxWidget;
import com.simibubi.create.foundation.utility.Components;
import dev.mayaqq.estrogen.client.cosmetics.ui.CosmeticUI;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

public class EstrogenConfigScreen extends BaseConfigScreen {

    protected BoxWidget clientCosmeticWidget;

    protected String cosmeticTitle = "Cosmetics";

    public EstrogenConfigScreen(Screen parent, @NotNull String modID) {
        super(parent, modID);
    }

    @Override
    protected void init() {
        super.init();

        TextStencilElement cosmeticText = new TextStencilElement(font, Components.literal(cosmeticTitle)).centered(true, true);
        addRenderableWidget(clientCosmeticWidget = new BoxWidget(width / 2 - 100, height / 2 - 15 + 120, 200, 16).showingElement(cosmeticText));

        if (clientSpec != null) {
            clientCosmeticWidget.withCallback(() -> CosmeticUI.open(this));
            cosmeticText.withElementRenderer(BoxWidget.gradientFactory.apply(clientCosmeticWidget));
        } else {
            clientCosmeticWidget.active = false;
            clientCosmeticWidget.updateColorsFromState();
            cosmeticText.withElementRenderer(DISABLED_RENDERER);
        }
    }
}
