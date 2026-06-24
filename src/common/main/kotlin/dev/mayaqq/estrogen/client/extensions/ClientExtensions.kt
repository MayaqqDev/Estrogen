package dev.mayaqq.estrogen.client.extensions

import dev.mayaqq.estrogen.mixin.client.accessor.AbstractWidgetAccessor
import net.minecraft.client.gui.components.AbstractWidget

var AbstractWidget.posX
    get() = (this as AbstractWidgetAccessor).getX()
    set(value) = (this as AbstractWidgetAccessor).setX(value)

var AbstractWidget.posY
    get() = (this as AbstractWidgetAccessor).getY()
    set(value) = (this as AbstractWidgetAccessor).setY(value)

var AbstractWidget.widgetWidth
    get() = (this as AbstractWidgetAccessor).getWidth()
    set(value) = (this as AbstractWidgetAccessor).setWidth(value)

var AbstractWidget.widgetHeight
    get() = (this as AbstractWidgetAccessor).getHeight()
    set(value) = (this as AbstractWidgetAccessor).setHeight(value)