@file:EventSubscriber(env = [Environment.CLIENT])
package dev.mayaqq.estrogen.client

import dev.mayaqq.cynosure.client.entity.registerDefinition
import dev.mayaqq.cynosure.client.events.ParticleRenderTypeRegistrationEvent
import dev.mayaqq.cynosure.client.events.entity.RenderLayerRegistrationEvent
import dev.mayaqq.cynosure.client.render.gui.HudOverlayRegistry
import dev.mayaqq.cynosure.client.render.gui.VanillaHud
import dev.mayaqq.cynosure.data.registerResourcepackReloadListener
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.utils.Environment
import dev.mayaqq.estrogen.client.content.EstrogenKeybinds
import dev.mayaqq.estrogen.client.content.entityRenderers.boobs.BoobFeatureRenderer
import dev.mayaqq.estrogen.client.content.entityRenderers.moth.MothModel
import dev.mayaqq.estrogen.client.content.entityRenderers.mothElytra.MothElytraLayer
import dev.mayaqq.estrogen.client.content.entityRenderers.mothElytra.MothElytraModel
import dev.mayaqq.estrogen.client.content.particles.DashTrailParticle
import dev.mayaqq.estrogen.client.features.boobs.data.BreastArmorDataLoader
import dev.mayaqq.estrogen.client.features.dash.DashOverlay
import dev.mayaqq.estrogen.config.EstrogenClientConfig
import dev.mayaqq.estrogen.config.Instance
import dev.mayaqq.estrogen.id
import net.minecraft.client.Minecraft
import net.minecraft.world.entity.EntityType
import uwu.serenity.kittyconfig.api.defaults.load

fun estrogenClient() {
    EstrogenClientConfig.Instance.load()
    EstrogenKeybinds
    HudOverlayRegistry.register(VanillaHud.FROSTBITE, id("dash"), DashOverlay)
    MothElytraModel.LAYER_LOCATION.registerDefinition(MothElytraModel.Companion::createBodyLayer)
    MothModel.LAYER_LOCATION.registerDefinition(MothModel::createBodyLayer)
    // registerResourcepackReloadListener(id("dream_texture"), DreamTextureGenerator)
    registerResourcepackReloadListener(id("estrogen_armor_data"), BreastArmorDataLoader)
}

@Subscription
fun addRenderLayers(event: RenderLayerRegistrationEvent) {
    event.addLayer(EntityType.ARMOR_STAND) { MothElytraLayer(it, event.models) }
    event.addLayer(EntityType.PLAYER) { MothElytraLayer(it, event.models) }
    event.addLayer(EntityType.PLAYER) { BoobFeatureRenderer(it, Minecraft.getInstance().modelManager) }
}

@Subscription
fun registerParticleRenderTypes(event: ParticleRenderTypeRegistrationEvent) {
    event.register(DashTrailParticle.RENDER_TYPE)
}