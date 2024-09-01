package dev.mayaqq.estrogen.client.ui.components;

import com.simibubi.create.foundation.gui.element.RenderElement;
import com.simibubi.create.foundation.gui.widget.BoxWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

import java.util.function.Function;

public class ImprovedBoxWidget extends BoxWidget {

    public ImprovedBoxWidget() {
        super();
    }

    public ImprovedBoxWidget(int x, int y) {
        super(x, y);
    }

    public ImprovedBoxWidget(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public ImprovedBoxWidget withPosition(int x, int y) {
        this.setPosition(x, y);
        return this;
    }

    public ImprovedBoxWidget withSize(int width, int height) {
        this.withBounds(width, height);
        return this;
    }

    public ImprovedBoxWidget withTooltip(Component component) {
        setTooltip(Tooltip.create(component));
        return this;
    }

    public ImprovedBoxWidget withShowingElement(Function<ImprovedBoxWidget, RenderElement> showingElement) {
        super.showingElement(showingElement.apply(this));
        return this;
    }

    public ImprovedBoxWidget asDisabled() {
        this.active = false;
        updateColorsFromState();
        return this;
    }

}
