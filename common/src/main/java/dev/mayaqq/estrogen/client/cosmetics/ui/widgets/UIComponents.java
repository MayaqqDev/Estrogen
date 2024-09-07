package dev.mayaqq.estrogen.client.cosmetics.ui.widgets;

import com.simibubi.create.foundation.gui.Theme;
import com.simibubi.create.foundation.gui.element.DelegatedStencilElement;
import com.simibubi.create.foundation.gui.element.ScreenElement;
import com.simibubi.create.foundation.gui.element.TextStencilElement;
import com.simibubi.create.foundation.gui.widget.BoxWidget;
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

    public static ImprovedBoxWidget iconButton(ScreenElement icon) {
        return button().withShowingElement(widget -> {
            DelegatedStencilElement element = new DelegatedStencilElement();
            element.withStencilRenderer((ms, w, h, alpha) -> icon.render(ms, (w - 16) / 2, (h - 16) / 2));
            element.withBounds(16, 16);
            element.withElementRenderer(BoxWidget.gradientFactory.apply(widget));
            return element;
        });
    }

    public static ImprovedBoxWidget box(int x, int y, int width, int height) {
        ImprovedBoxWidget box = new ImprovedBoxWidget(x, y, width, height);
        box.withBorderColors(Theme.p(BOX_THEME));
        box.withPadding(BOX_PADDING, BOX_PADDING);
        box.active = false;
        return box;
    }
}
