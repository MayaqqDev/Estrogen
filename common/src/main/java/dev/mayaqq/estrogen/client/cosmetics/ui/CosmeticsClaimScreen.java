package dev.mayaqq.estrogen.client.cosmetics.ui;

import com.simibubi.create.foundation.gui.Theme;
import dev.mayaqq.estrogen.client.cosmetics.service.CosmeticsApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public class CosmeticsClaimScreen extends BaseCosmeticsScreen {

    private Component info;

    public CosmeticsClaimScreen(Screen parent) {
        super(parent);
    }

    @Override
    protected void contentInit(int x, int y, int width, int height) {
        this.claimButton.asDisabled();

        int buttonX = x + width / 2 - 50;
        int buttonY = (int) (y + height * 0.75f - 10);

        // Claim Code Input
        addRenderableOnly(UIComponents.box(buttonX, buttonY, 100, 16));
        var textBox = addRenderableWidget(new EditBox(font, buttonX + 2, buttonY + 3, 96, 14, CommonComponents.EMPTY));
        textBox.setBordered(false);

        // Claim Button
        addRenderableWidget(UIComponents.textButton(CosmeticUI.CLAIM_BUTTON)
                .withPosition(buttonX, buttonY + 30)
                .withBounds(100, 20)
                .withCallback(() -> claim(textBox.getValue()))
        );
    }

    @Override
    protected void renderWindow(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.renderWindow(graphics, mouseX, mouseY, partialTicks);

        int x = (int) (this.width * 0.25f) + PADDING * 2;
        int y = PADDING * 3;
        int width = this.width - x - PADDING;

        int infoX = x + width / 2;
        int infoY = y;

        for (FormattedCharSequence text : font.split(CosmeticUI.CLAIM_DESCRIPTION, width)) {
            graphics.drawCenteredString(font, text, infoX, infoY, Theme.i(Theme.Key.BUTTON_IDLE));
            infoY += 10;
        }

        if (this.info != null) {
            infoY += 10;
            graphics.drawCenteredString(font, this.info, infoX, infoY, Theme.i(Theme.Key.BUTTON_FAIL));
        }
    }

    public void claim(String code) {
        CosmeticsApi.claimReward(code)
                .thenAcceptAsync(status -> {
                    if (status == CosmeticsApi.StatusCode.OK) {
                        Minecraft.getInstance().tell(() -> {
                            Screen screen = Minecraft.getInstance().screen;
                            if (screen != null) {
                                screen.onClose();
                            }
                        });
                    } else {
                        this.info = CosmeticUI.getClaimMessage(status);
                    }
                });
    }

    public static void open() {
        Minecraft.getInstance().setScreen(new CosmeticsClaimScreen(Minecraft.getInstance().screen));
    }
}
