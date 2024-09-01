package dev.mayaqq.estrogen.client.cosmetics.ui;

import dev.mayaqq.estrogen.client.cosmetics.Cosmetic;
import dev.mayaqq.estrogen.client.cosmetics.service.CosmeticsApi;
import dev.mayaqq.estrogen.client.cosmetics.ui.widgets.CosmeticIconWidget;
import dev.mayaqq.estrogen.client.cosmetics.ui.widgets.LayoutGroup;
import dev.mayaqq.estrogen.client.cosmetics.ui.widgets.UIComponents;
import net.minecraft.Optionull;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.Layout;
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

            group.addChild(UIComponents.button()
                    .withSize(32, 32)
                    .withTooltip(Component.literal(Optionull.mapOrDefault(cosmetic, Cosmetic::name, "None")))
                    .withCallback(() -> CosmeticsApi.setCosmetic(cosmetic))
            );
            if (cosmetic != null) {
                group.addChild(new CosmeticIconWidget(cosmetic, 0, 0, null)
                                .setRotationSpeed(0.5f)
                        .setScale(1.5f)
                );
            }

            layout.addChild(group);
        }

        layout.getGrid().arrangeElements();
        layout.getGrid().visitWidgets(this::addRenderableWidget);
    }
}
