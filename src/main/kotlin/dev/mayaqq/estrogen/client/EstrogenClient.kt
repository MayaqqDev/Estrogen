@file:EventSubscriber
package dev.mayaqq.estrogen.client

import dev.mayaqq.cynosure.client.entity.registerDefinition
import dev.mayaqq.cynosure.client.events.entity.RenderLayerRegistrationEvent
import dev.mayaqq.cynosure.client.render.gui.HudOverlayRegistry
import dev.mayaqq.cynosure.client.render.gui.VanillaHud
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.modId
import dev.mayaqq.estrogen.client.content.EstrogenKeybinds
import dev.mayaqq.estrogen.client.content.entityRenderers.mothElytra.MothElytraLayer
import dev.mayaqq.estrogen.client.content.entityRenderers.mothElytra.MothElytraModel
import dev.mayaqq.estrogen.client.features.dash.DashOverlay
import dev.mayaqq.estrogen.config.EstrogenClientConfig
import dev.mayaqq.estrogen.config.Instance
import net.minecraft.world.entity.EntityType
import uwu.serenity.kittyconfig.api.defaults.load

fun estrogenClient() {
    EstrogenClientConfig.Instance.load()
    EstrogenKeybinds
    HudOverlayRegistry.register(VanillaHud.FROSTBITE, modId("dash"), DashOverlay)
    MothElytraModel.LAYER_LOCATION.registerDefinition(MothElytraModel.Companion::createBodyLayer)
}

@Subscription
fun addRenderLayers(event: RenderLayerRegistrationEvent) {
    event.addLayer(EntityType.ARMOR_STAND) { MothElytraLayer(it, event.models) }
}