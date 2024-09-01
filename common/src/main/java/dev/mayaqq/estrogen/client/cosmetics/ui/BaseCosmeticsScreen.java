package dev.mayaqq.estrogen.client.cosmetics.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.simibubi.create.foundation.gui.AbstractSimiScreen;
import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.gui.Theme;
import com.simibubi.create.foundation.gui.UIRenderHelper;
import com.simibubi.create.foundation.gui.widget.BoxWidget;
import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import com.teamresourceful.resourcefullib.client.components.CursorWidget;
import com.teamresourceful.resourcefullib.client.screens.CursorScreen;
import com.teamresourceful.resourcefullib.client.utils.CursorUtils;
import dev.mayaqq.estrogen.client.ui.components.ImprovedBoxWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.MutableComponent;
import org.lwjgl.opengl.GL30;

public class BaseCosmeticsScreen extends AbstractSimiScreen implements CursorScreen {

    protected static final int PADDING = 20;
    protected final Screen parent;
    protected ImprovedBoxWidget claimButton;

    private Cursor cursor = Cursor.DEFAULT;

    public BaseCosmeticsScreen(Screen parent) {
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        int previewWidth = (int) (this.width * 0.25f);
        int contentWidth = this.width - previewWidth - PADDING * 3;

        // Banner
        addRenderableOnly(UIComponents.box(previewWidth + PADDING * 2, PADDING, contentWidth, PADDING));

        // Preview
        var preview = addRenderableOnly(UIComponents.box(PADDING, PADDING, previewWidth, this.height - PADDING * 4));
        addRenderableWidget(new CosmeticPreview(preview.getX(), preview.getY(), preview.getWidth(), preview.getHeight()));

        // Close/Back Button
        AllIcons icon = this.parent == null ? AllIcons.I_DISABLE : AllIcons.I_CONFIG_BACK;
        MutableComponent tooltip = this.parent == null ? CosmeticUI.CLOSE : CosmeticUI.GO_BACK;
        addRenderableWidget(UIComponents.iconButton(icon)
                .withTooltip(tooltip)
                .withPosition(PADDING, this.height - PADDING * 2)
                .withBounds(PADDING, PADDING)
                .withCallback(this::onClose)
        );

        // Claim Button
        this.claimButton = addRenderableWidget(UIComponents.textButton(CosmeticUI.CLAIM_REWARD)
                .withPosition(PADDING * 3, this.height - PADDING * 2)
                .withBounds(previewWidth - PADDING * 2, PADDING)
                .withCallback(CosmeticsClaimScreen::open)
        );

        int x = previewWidth + PADDING * 2;
        int y = PADDING * 3;
        int width = this.width - x - PADDING;
        int height = this.height - y - PADDING;
        contentInit(x, y, width, height);
    }

    protected void contentInit(int x, int y, int width, int height) {

    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        boolean wihinBounds = mouseX >= 0 && mouseY >= 0 && mouseX < this.width && mouseY < this.height;
        if (wihinBounds) setCursor(Cursor.DEFAULT);
        super.render(graphics, mouseX, mouseY, partialTicks);
        if (wihinBounds) setCursor(mouseX, mouseY);
    }

    @Override
    protected void renderWindow(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        try (var stack = new CloseablePoseStack(graphics)) {
            int bannerCenterX = (int) ((this.width * 0.25f) + 40) + (int) (this.width - (this.width * 0.25f) - 60) / 2;
            stack.translate(bannerCenterX, 24, 0);
            stack.scale(1.5f, 1.5f, 1.5f);
            graphics.drawCenteredString(
                    font,
                    "Estrogen Cosmetics",
                    0, 0,
                    Theme.i(Theme.Key.TEXT_ACCENT_STRONG)
            );
        }
    }

    @Override
    protected void prepareFrame() {
        UIRenderHelper.swapAndBlitColor(minecraft.getMainRenderTarget(), UIRenderHelper.framebuffer);
        RenderSystem.clear(GL30.GL_STENCIL_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT, Minecraft.ON_OSX);
    }

    @Override
    protected void endFrame() {
        UIRenderHelper.swapAndBlitColor(UIRenderHelper.framebuffer, minecraft.getMainRenderTarget());
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(parent);
    }

    @Override
    public void removed() {
        super.removed();
        CursorUtils.setDefault();
    }

    private void setCursor(float mouseX, float mouseY) {
        for (GuiEventListener child : children()) {
            ScreenRectangle rectangle = child.getRectangle();
            boolean hovering = rectangle.left() <= mouseX && mouseX < rectangle.right() && rectangle.top() <= mouseY && mouseY < rectangle.bottom();
            if (child instanceof CursorWidget widget) {
                if (hovering && widget.getCursor() != Cursor.DEFAULT) {
                    setCursor(widget.getCursor());
                    break;
                }
            } else if (child instanceof AbstractWidget widget && hovering && widget.visible) {
                if (widget.active) {
                    setCursor(widget instanceof EditBox || widget instanceof MultiLineEditBox ? Cursor.TEXT : Cursor.POINTER);
                } else {
                    setCursor(Cursor.DISABLED);
                }
                break;
            }
        }
        switch (cursor) {
            case DEFAULT -> CursorUtils.setDefault();
            case POINTER -> CursorUtils.setPointing();
            case DISABLED -> CursorUtils.setDisabled();
            case TEXT -> CursorUtils.setText();
            case CROSSHAIR -> CursorUtils.setCrosshair();
            case RESIZE_EW -> CursorUtils.setResizeEastWest();
            case RESIZE_NS -> CursorUtils.setResizeNorthSouth();
            case RESIZE_NESW -> CursorUtils.setResizeNorthEastSouthWest();
            case RESIZE_NWSE -> CursorUtils.setResizeNorthWestSouthEast();
            case RESIZE_ALL -> CursorUtils.setResizeAll();
        }
    }

    @Override
    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

}
