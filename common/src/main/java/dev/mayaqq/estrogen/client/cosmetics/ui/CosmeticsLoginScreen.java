package dev.mayaqq.estrogen.client.cosmetics.ui;

import com.simibubi.create.foundation.gui.ScreenOpener;
import com.simibubi.create.foundation.gui.Theme;
import dev.mayaqq.estrogen.client.cosmetics.service.CosmeticsApi;
import dev.mayaqq.estrogen.client.cosmetics.ui.widgets.UIComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public class CosmeticsLoginScreen extends BaseCosmeticsScreen {

    private Component info;

    public CosmeticsLoginScreen(Screen parent) {
        super(parent);
    }

    @Override
    protected void contentInit(int x, int y, int width, int height) {
        this.claimButton.asDisabled();

        int loginButtonX = x + width / 2 - 50;
        int loginButtonY = (int) (y + height * 0.8f - 10);

        addRenderableWidget(UIComponents.textButton(CosmeticUI.LOGIN_BUTTON)
                .withPosition(loginButtonX, loginButtonY)
                .withBounds(100, 20)
                .withCallback(this::login)
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

        for (FormattedCharSequence text : font.split(CosmeticUI.LOGIN_DESCRIPTION, width)) {
            graphics.drawCenteredString(font, text, infoX, infoY, Theme.i(Theme.Key.BUTTON_IDLE));
            infoY += 10;
        }

        if (this.info != null) {
            infoY += 10;
            graphics.drawCenteredString(font, this.info, infoX, infoY, Theme.i(Theme.Key.BUTTON_FAIL));
        }
    }

    public void login() {
        this.info = CosmeticUI.getLoginMessage(null);

        CosmeticsApi.login().thenAcceptAsync(status -> {
            if (status == CosmeticsApi.StatusCode.OK) {
                this.info = CosmeticUI.getCosmeticsMessage(null);
                CosmeticsApi.getCosmetics().thenAcceptAsync(cosmeticsStatus -> {
                    if (cosmeticsStatus == CosmeticsApi.StatusCode.OK) {
                        Minecraft.getInstance().tell(() -> {
                            Screen screen = Minecraft.getInstance().screen;
                            if (screen != null) {
                                screen.onClose();
                                ScreenOpener.open(new CosmeticsScreen(null));
                            }
                        });
                    } else {
                        this.info = CosmeticUI.getCosmeticsMessage(cosmeticsStatus);
                    }
                });
            } else {
                this.info = CosmeticUI.getLoginMessage(status);
            }
        });
    }
}
