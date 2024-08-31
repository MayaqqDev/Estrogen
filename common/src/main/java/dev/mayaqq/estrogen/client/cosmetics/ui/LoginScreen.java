package dev.mayaqq.estrogen.client.cosmetics.ui;

import com.simibubi.create.foundation.config.ui.*;
import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.gui.ScreenOpener;
import com.simibubi.create.foundation.gui.Theme;
import com.simibubi.create.foundation.gui.UIRenderHelper;
import com.simibubi.create.foundation.gui.element.TextStencilElement;
import com.simibubi.create.foundation.gui.widget.BoxWidget;
import com.simibubi.create.foundation.utility.Components;
import dev.mayaqq.estrogen.client.cosmetics.service.CosmeticsApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoginScreen extends ConfigScreen {
    BoxWidget clientConfigWidget;
    BoxWidget goBack;
    BoxWidget title;

    String clientTitle = "Login";

    public LoginScreen(Screen parent) {
        super(parent);
        modID = "estrogen";
    }

    @Override
    protected void init() {
        super.init();

        AtomicBoolean loggingIn = new AtomicBoolean(false);

        TextStencilElement clientText = new TextStencilElement(font, Components.literal(clientTitle)).centered(true, true);
        addRenderableWidget(clientConfigWidget = new BoxWidget(width / 2 - 100, height / 2 - 15 - 30, 200, 16).showingElement(clientText));
        clientConfigWidget.withCallback(() -> {
            if (loggingIn.get()) return;

            clientConfigWidget.setMessage(Components.literal("Logging in..."));

            CosmeticsApi.login().thenAcceptAsync(status -> {
                switch (status) {
                    case OK: {
                        clientConfigWidget.setMessage(Components.literal("Getting Cosmetics..."));
                        CosmeticsApi.getCosmetics().thenAcceptAsync(cosmeticsStatus -> {
                            switch (cosmeticsStatus) {
                                case OK: {
                                    Minecraft.getInstance().tell(() -> {
                                        Screen screen = Minecraft.getInstance().screen;
                                        if (screen != null) {
                                            screen.onClose();
                                            //TODO: open CosmeticsModal
                                        }
                                    });
                                }
                                case UNAUTHORIZED: {
                                    clientConfigWidget.setMessage(Components.literal("Failed to get cosmetics, try again later."));
                                }
                                case INTERNAL_SERVER_ERROR: {
                                    clientConfigWidget.setMessage(Components.literal("Error occurred on server side while getting cosmetics."));
                                }
                                case UNKOWN_ERROR: {
                                    clientConfigWidget.setMessage(Components.literal("Unknown error occurred while getting cosmetics."));
                                }
                            }
                        });
                    }
                    case UNAUTHORIZED: {
                        clientConfigWidget.setMessage(Components.literal("Failed to login, check if you are logged in to Minecraft."));
                    }
                    case INTERNAL_SERVER_ERROR: {
                        clientConfigWidget.setMessage(Components.literal("Error occurred on server side while logging in."));
                    }
                    case UNKOWN_ERROR: {
                        clientConfigWidget.setMessage(Components.literal("Unknown error occurred while logging in."));
                    }
                }});
        });
        clientText.withElementRenderer(BoxWidget.gradientFactory.apply(clientConfigWidget));

        TextStencilElement titleText = new TextStencilElement(font, modID.toUpperCase(Locale.ROOT))
                .centered(true, true)
                .withElementRenderer((ms, w, h, alpha) -> {
                    UIRenderHelper.angledGradient(ms, 0, 0, h / 2, h, w / 2, Theme.p(Theme.Key.CONFIG_TITLE_A));
                    UIRenderHelper.angledGradient(ms, 0, w / 2, h / 2, h, w / 2, Theme.p(Theme.Key.CONFIG_TITLE_B));
                });
        int boxWidth = width + 10;
        int boxHeight = 39;
        int boxPadding = 4;
        title = new BoxWidget(-5, height / 2 - 110, boxWidth, boxHeight)
                //.withCustomBackground(new Color(0x20_000000, true))
                .withBorderColors(Theme.p(Theme.Key.BUTTON_IDLE))
                .withPadding(0, boxPadding)
                .rescaleElement(boxWidth / 2f, (boxHeight - 2 * boxPadding) / 2f)//double the text size by telling it the element is only half as big as the available space
                .showingElement(titleText.at(0, 7));
        title.active = false;

        addRenderableWidget(title);

        goBack = new BoxWidget(width / 2 - 134, height / 2, 20, 20).withPadding(2, 2)
                .withCallback(() -> linkTo(parent));
        goBack.showingElement(AllIcons.I_CONFIG_BACK.asStencil()
                .withElementRenderer(BoxWidget.gradientFactory.apply(goBack)));
        goBack.getToolTip()
                .add(Components.literal("Go Back"));
        addRenderableWidget(goBack);

    }

    @Override
    protected void renderWindow(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.drawCenteredString(font, "Login", width / 2, height / 2 - 105, Theme.i(Theme.Key.TEXT_ACCENT_STRONG));
    }

    private void linkTo(Screen screen) {
        ScreenOpener.open(screen);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers))
            return true;
        if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
            linkTo(parent);
        }
        return false;
    }
}
