package dev.mayaqq.estrogen.mixin.client.accessor;

import net.minecraft.client.gui.components.AbstractWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractWidget.class)
public interface AbstractWidgetAccessor {
    @Accessor("x")
    void setX(int x);
    @Accessor("y")
    void setY(int y);
    @Accessor("width")
    void setWidth(int width);
    @Accessor("height")
    void setHeight(int height);

    @Accessor("x")
    int getX();
    @Accessor("y")
    int getY();
    @Accessor("width")
    int getWidth();
    @Accessor("height")
    int getHeight();
}
