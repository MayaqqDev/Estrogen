package dev.mayaqq.estrogen.fabric.integrations.modmenu;

import com.simibubi.create.foundation.config.ui.BaseConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.mayaqq.estrogen.Estrogen;

public class ModMenuCompat implements ModMenuApi {
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (screen) -> new BaseConfigScreen(screen, Estrogen.MOD_ID);
    }
}
