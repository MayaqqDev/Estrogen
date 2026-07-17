package dev.mayaqq.estrogen.neoforge.client

import dev.mayaqq.estrogen.client.content.screen.EstrogenMenuScreen
import invoke.kitty.kritter.platform.Mod
import invoke.kitty.kritter.platform.forge.EntrypointHandler
import invoke.kitty.kritter.platform.forge.modContainer
import net.neoforged.neoforge.client.gui.IConfigScreenFactory

object EstrogenForgeClient {
    @EntrypointHandler("client")
    fun onClientInit(mod: Mod) {
        mod.modContainer!!.registerExtensionPoint(
            IConfigScreenFactory::class.java,
            IConfigScreenFactory { _, screen -> EstrogenMenuScreen(screen) }
        )
    }
}

