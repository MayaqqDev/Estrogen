@file:JvmName("EstrogenClientFabric")
package dev.mayaqq.estrogen.fabric.client

import com.mojang.blaze3d.pipeline.RenderTarget
import dev.mayaqq.cynosure.client.models.baked.CustomBakedModel
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.client.content.EstrogenKeybinds
import dev.mayaqq.estrogen.client.estrogenClient
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.EstrogenItems
import dev.mayaqq.estrogen.content.items.DreamCatcherItem
import dev.mayaqq.estrogen.content.items.ThighHighsItem
import invoke.kitty.kritter.client.events.ClientLifecycleEvents
import kotlinx.coroutines.ThreadContextElement
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.fabricmc.fabric.impl.client.keybinding.KeyBindingRegistryImpl
import net.fabricmc.fabric.mixin.client.keybinding.KeyBindingAccessor
import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft
import net.minecraft.client.Options
import net.minecraft.client.gui.screens.options.OptionsScreen
import net.minecraft.client.particle.SingleQuadParticle
import net.minecraft.client.renderer.ShaderInstance
import org.figuramc.figura.utils.ui.CustomFramebuffer
import org.figuramc.figura.utils.ui.StencilHelper
import org.lwjgl.opengl.GL30
import org.lwjgl.opengl.GL40

fun init() {
    estrogenClient()
    KeyBindingHelper.registerKeyBinding(EstrogenKeybinds.DASH_KEY)
//    ColorProviderRegistry.ITEM.register(ThighHighsItem::getItemColor, EstrogenItems.ThighHighs.get())
//    ColorProviderRegistry.ITEM.register(DreamCatcherItem::getItemColor, EstrogenBlocks.DreamCatcher.get().asItem())
}

fun initFabric() {
}