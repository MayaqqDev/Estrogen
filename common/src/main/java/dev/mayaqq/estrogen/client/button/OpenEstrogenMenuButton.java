package dev.mayaqq.estrogen.client.button;

import com.simibubi.create.foundation.gui.ScreenOpener;
import com.simibubi.create.foundation.utility.Components;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.config.EstrogenConfigScreen;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.item.ItemStack;

public class OpenEstrogenMenuButton extends Button {

    public static final ItemStack ICON = EstrogenItems.ESTROGEN_PILL.asStack();

    public OpenEstrogenMenuButton(int x, int y) {
        super(x, y, 20, 20, Components.immutableEmpty(), OpenEstrogenMenuButton::click, DEFAULT_NARRATION);
    }

    @Override
    public void renderString(GuiGraphics graphics, Font pFont, int pColor) {
        graphics.renderItem(ICON, getX() + 2, getY() + 2);
    }

    public static void click(Button b) {
        ScreenOpener.open(new EstrogenConfigScreen(Minecraft.getInstance().screen, Estrogen.MOD_ID));
    }
}
