package dev.mayaqq.estrogen

import dev.mayaqq.estrogen.api.EstrogenEntrypoint
import dev.mayaqq.estrogen.api.EstrogenFlag
import dev.mayaqq.estrogen.api.EstrogenModule
import dev.mayaqq.estrogen.api.ScreenProvider
import dev.mayaqq.estrogen.client.content.screen.EstrogenMenuScreen
import invoke.kitty.kritter.utils.color.Color
import invoke.kitty.kritter.utils.color.LightBlue

@EstrogenEntrypoint
class EstrogenModuleModule : EstrogenModule {
    override fun createConfigScreen(): ScreenProvider = { EstrogenMenuScreen(it) }
    override val flags: Array<EstrogenFlag> = arrayOf()
    override val color: Color = LightBlue
    override val description: String = "Base Estrogen, contains some recipes + a build-in datapack for vanilla integration."
}