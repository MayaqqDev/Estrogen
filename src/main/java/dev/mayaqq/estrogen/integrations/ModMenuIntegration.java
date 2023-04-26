package dev.mayaqq.estrogen.integrations;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.mayaqq.estrogen.config.EstrogenConfig;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return EstrogenConfig.INSTANCE::makeScreen;
    }
}
