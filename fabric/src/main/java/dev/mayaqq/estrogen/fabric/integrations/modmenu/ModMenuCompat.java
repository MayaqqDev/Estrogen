package dev.mayaqq.estrogen.fabric.integrations.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.config.EstrogenConfigScreen;

public class ModMenuCompat implements ModMenuApi {
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (screen) -> new EstrogenConfigScreen(screen, Estrogen.MOD_ID);
    }
}
