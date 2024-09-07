package dev.mayaqq.estrogen.client.cosmetics.ui;

import com.simibubi.create.foundation.gui.AllIcons;
import dev.mayaqq.estrogen.client.cosmetics.Cosmetic;
import dev.mayaqq.estrogen.client.cosmetics.service.CosmeticsApi;
import dev.mayaqq.estrogen.client.cosmetics.ui.widgets.CosmeticIconWidget;
import dev.mayaqq.estrogen.client.cosmetics.ui.widgets.ImprovedBoxWidget;
import dev.mayaqq.estrogen.client.cosmetics.ui.widgets.LayoutGroup;
import dev.mayaqq.estrogen.client.cosmetics.ui.widgets.UIComponents;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class CosmeticsScreen extends BaseCosmeticsScreen {

    public CosmeticsScreen(Screen parent) {
        super(parent);
    }

    @Override
    protected void contentInit(int x, int y, int width, int height) {
        List<Cosmetic> cosmetics = new ArrayList<>();
        cosmetics.add(null);
        for (String s : CosmeticsApi.getAvailableCosmetics()) {
            Cosmetic cosmetic = CosmeticsApi.getCosmetic(s);
            if (cosmetic == null) continue;
            cosmetics.add(cosmetic);
        }

        var layout = new GridLayout(x, y)
                .spacing(PADDING)
                .createRowHelper(width / 52);

        for (Cosmetic cosmetic : cosmetics) {
            var group = new LayoutGroup(32, 32);

            if (cosmetic != null) {
                ImprovedBoxWidget button = UIComponents.button()
                        .withSize(32, 32)
                        .withTooltip(Component.literal(cosmetic.name()))
                        .withCallback(() -> CosmeticsApi.setCosmetic(cosmetic));

                group.addChild(button);
                group.addChild(CosmeticIconWidget.of(cosmetic)
                        .withSize(32, 32)
                        .withHighlightPredicate((widget, mouseX, mouseY) -> button.isHovered())
                        .withRotationSpeed(0.5f)
                );
            } else {
                group.addChild(UIComponents.iconButton(AllIcons.I_CONFIG_RESET)
                        .withSize(32, 32)
                        .withTooltip(CosmeticUI.NONE)
                        .withPadding(0, 0)
                        .withCallback(() -> CosmeticsApi.setCosmetic(null))
                );
            }

            layout.addChild(group);
        }

        layout.getGrid().arrangeElements();
        layout.getGrid().visitWidgets(this::addRenderableWidget);
    }
}
