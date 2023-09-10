package dev.mayaqq.estrogen.integrations.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.mayaqq.estrogen.config.EstrogenConfig;

public class ModMenuCompat implements ModMenuApi {
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return EstrogenConfig.INSTANCE::makeScreen;
    }
}
