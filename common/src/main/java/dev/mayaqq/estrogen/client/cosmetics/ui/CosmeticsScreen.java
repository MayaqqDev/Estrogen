package dev.mayaqq.estrogen.client.cosmetics.ui;

import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.gui.Theme;
import com.simibubi.create.foundation.gui.widget.BoxWidget;
import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import dev.mayaqq.estrogen.client.cosmetics.Cosmetic;
import dev.mayaqq.estrogen.client.cosmetics.service.CosmeticsApi;
import net.minecraft.Optionull;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class CosmeticsScreen extends BaseScreen {

    private static final int PADDING = 20;

    public CosmeticsScreen(Screen parent) {
        super(parent);
    }

    @Override
    protected void init() {
        super.init();

        List<Cosmetic> cosmetics = new ArrayList<>();
        cosmetics.add(null);
        for (String s : CosmeticsApi.getAvailableCosmetics()) {
            Cosmetic cosmetic = CosmeticsApi.getCosmetic(s);
            if (cosmetic == null) continue;
            cosmetics.add(cosmetic);
        }

        int previewWidth = (int) (this.width * 0.25f);
        int cosmeticX = previewWidth + PADDING * 2;
        int cosmeticY = PADDING * 3;

        int columns = (this.width - cosmeticX - PADDING) / 52;

        for (int i = 0; i < cosmetics.size(); i++) {
            Cosmetic cosmetic = cosmetics.get(i);
            BoxWidget button = new BoxWidget(cosmeticX, cosmeticY, 32, 32)
                    .withBorderColors(Theme.p(Theme.Key.BUTTON_IDLE))
                    .withPadding(2, 2)
                    .withCallback(() -> {

                    });
            button.setTooltip(Tooltip.create(Component.literal(
                    Optionull.mapOrDefault(cosmetic, Cosmetic::name, "None")
            )));
            addRenderableWidget(button);

            if (i % columns == 0) {
                cosmeticX = previewWidth + PADDING * 2;
                cosmeticY += 52;
            } else {
                cosmeticX += 52;
            }
        }

        BoxWidget banner = new BoxWidget(
                previewWidth + PADDING * 2, PADDING,
                this.width - previewWidth - PADDING * 3, PADDING
        )
                .withBorderColors(Theme.p(Theme.Key.BUTTON_IDLE))
                .withPadding(2, 2);
        banner.active = false;
        addRenderableWidget(banner);

        BoxWidget preview = new BoxWidget(PADDING, PADDING, previewWidth, this.height - PADDING * 4)
                .withBorderColors(Theme.p(Theme.Key.BUTTON_IDLE))
                .withPadding(2, 2);
        preview.active = false;
        addRenderableWidget(preview);
        addRenderableWidget(new CosmeticPreview(
                preview.getX(), preview.getY(),
                preview.getWidth(), preview.getHeight()
        ));

        addRenderableWidget(
                createTextBox(
                        font,
                        PADDING * 3, this.height - PADDING * 2,
                        previewWidth - PADDING * 2, PADDING,
                        Component.literal("Claim Reward")
                )
                .withBorderColors(Theme.p(Theme.Key.BUTTON_IDLE))
                .withPadding(2, 2)
                .withCallback(() -> {
                    CosmeticsModal modal = new CosmeticsModal(this);
                    minecraft.setScreen(modal);
                })
        );

        BoxWidget backButton = new BoxWidget(PADDING, this.height - PADDING * 2, 20, 20)
                .withBorderColors(Theme.p(Theme.Key.BUTTON_IDLE))
                .withPadding(2, 2)
                .withCallback(this::onClose);
        backButton.setTooltip(Tooltip.create(Component.literal("Go Back")));
        backButton.showingElement(AllIcons.I_CONFIG_BACK.asStencil().withElementRenderer(BoxWidget.gradientFactory.apply(backButton)));

        addRenderableWidget(backButton);
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
}
