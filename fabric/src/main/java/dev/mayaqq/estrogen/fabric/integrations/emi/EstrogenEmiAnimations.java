package dev.mayaqq.estrogen.fabric.integrations.emi;

import com.simibubi.create.compat.emi.CreateEmiAnimations;
import dev.emi.emi.api.widget.WidgetHolder;
import dev.mayaqq.estrogen.registry.client.EstrogenRenderer;
import dev.mayaqq.estrogen.registry.common.EstrogenBlocks;

public class EstrogenEmiAnimations extends CreateEmiAnimations {
    public static void addCentrifuge(WidgetHolder widgets, int x, int y) {
        widgets.addDrawable(x, y, 0, 0, (matrices, mouseX, mouseY, delta) -> {
            int scale = 22;

            blockElement(EstrogenRenderer.CENTRIFUGE_COG)
                    .rotateBlock(22.5, getCurrentAngle() * 10, 0)
                    .scale(scale)
                    .render(matrices);

            blockElement(EstrogenBlocks.CENTRIFUGE.getDefaultState())
                    .rotateBlock(22.5, 22.5, 0)
                    .scale(scale)
                    .render(matrices);
        });
    }
}
