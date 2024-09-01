package dev.mayaqq.estrogen.client.cosmetics.ui;

import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.gui.Theme;
import com.simibubi.create.foundation.gui.element.TextStencilElement;
import com.simibubi.create.foundation.gui.widget.BoxWidget;
import dev.mayaqq.estrogen.client.ui.components.ImprovedBoxWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.MutableComponent;

public class UIComponents {

    private static final int BOX_PADDING = 2;
    private static final Theme.Key BOX_THEME = Theme.Key.BUTTON_IDLE;

    public static ImprovedBoxWidget button() {
        ImprovedBoxWidget box = new ImprovedBoxWidget();
        box.withBorderColors(Theme.p(BOX_THEME));
        box.withPadding(BOX_PADDING, BOX_PADDING);
        return box;
    }

    public static ImprovedBoxWidget textButton(MutableComponent text) {
        Font font = Minecraft.getInstance().font;
        return button().withShowingElement(widget ->
                new TextStencilElement(font, text)
                        .centered(true, true)
                        .withElementRenderer(BoxWidget.gradientFactory.apply(widget))
        );
    }

    public static ImprovedBoxWidget iconButton(AllIcons icon) {
        return button().withShowingElement(widget ->
                icon.asStencil().withElementRenderer(BoxWidget.gradientFactory.apply(widget))
        );
    }

    public static ImprovedBoxWidget box(int x, int y, int width, int height) {
        ImprovedBoxWidget box = new ImprovedBoxWidget(x, y, width, height);
        box.withBorderColors(Theme.p(BOX_THEME));
        box.withPadding(BOX_PADDING, BOX_PADDING);
        box.active = false;
        return box;
    }
}
