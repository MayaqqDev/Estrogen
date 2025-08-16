package dev.mayaqq.estrogen.fabric.compat

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import dev.mayaqq.estrogen.client.content.screen.EstrogenMenuScreen

class ModMenuIntegration : ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory { screen -> EstrogenMenuScreen(screen) }
    }
}