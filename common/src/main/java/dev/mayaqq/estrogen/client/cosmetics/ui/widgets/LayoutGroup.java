package dev.mayaqq.estrogen.client.cosmetics.ui.widgets;

import net.minecraft.client.gui.layouts.Layout;
import net.minecraft.client.gui.layouts.LayoutElement;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class LayoutGroup implements Layout {

    private final List<LayoutElement> children = new ArrayList<>();
    private final int width;
    private final int height;
    private int x;
    private int y;

    public LayoutGroup(int width, int height) {
        this(width, height, 0, 0);
    }

    public LayoutGroup(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public void addChild(LayoutElement child) {
        children.add(child);
    }

    @Override
    public void arrangeElements() {
        visitChildren(child -> child.setPosition(x, y));
    }

    @Override
    public void visitChildren(@NotNull Consumer<LayoutElement> consumer) {
        children.forEach(consumer);
    }

    @Override
    public void setX(int x) {
        this.x = x;
        visitChildren(child -> child.setX(x));
    }

    @Override
    public void setY(int y) {
        this.y = y;
        visitChildren(child -> child.setY(y));
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }
}
