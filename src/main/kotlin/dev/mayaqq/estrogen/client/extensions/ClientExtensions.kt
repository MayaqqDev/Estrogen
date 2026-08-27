package dev.mayaqq.estrogen.client.extensions

import net.minecraft.client.gui.components.AbstractWidget

var AbstractWidget.posX
    get() = (this).getX()
    set(value) = (this).setX(value)

var AbstractWidget.posY
    get() = (this).getY()
    set(value) = (this).setY(value)

var AbstractWidget.widgetWidth
    get() = (this).getWidth()
    set(value) = (this).setWidth(value)

var AbstractWidget.widgetHeight
    get() = (this).getHeight()
    set(value) = (this).setHeight(value)