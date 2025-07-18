@file:EventSubscriber(env = [Environment.CLIENT])
package dev.mayaqq.estrogen.client

import dev.mayaqq.cynosure.client.entity.registerDefinition
import dev.mayaqq.cynosure.client.events.ClientTickEvent
import dev.mayaqq.cynosure.client.events.ParticleRenderTypeRegistrationEvent
import dev.mayaqq.cynosure.client.events.entity.RenderLayerRegistrationEvent
import dev.mayaqq.cynosure.client.render.gui.HudOverlayRegistry
import dev.mayaqq.cynosure.client.render.gui.VanillaHud
import dev.mayaqq.cynosure.client.splash.data.CynosureSplashLoader
import dev.mayaqq.cynosure.client.utils.DefaultSkin
import dev.mayaqq.cynosure.core.Environment
import dev.mayaqq.cynosure.core.isModLoaded
import dev.mayaqq.cynosure.data.registerResourcepackReloadListener
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.estrogen.client.content.EstrogenKeybinds
import dev.mayaqq.estrogen.client.content.EstrogenRenderTypes
import dev.mayaqq.estrogen.client.content.entityRenderers.boobs.BoobFeatureRenderer
import dev.mayaqq.estrogen.client.content.entityRenderers.moth.MothModel
import dev.mayaqq.estrogen.client.content.entityRenderers.mothElytra.MothElytraLayer
import dev.mayaqq.estrogen.client.content.entityRenderers.mothElytra.MothElytraModel
import dev.mayaqq.estrogen.client.content.particles.DashTrailParticle
import dev.mayaqq.estrogen.client.features.boobs.data.BreastArmorDataLoader
import dev.mayaqq.estrogen.client.features.dash.DashOverlay
import dev.mayaqq.estrogen.compat.ears.EarsCompat
import dev.mayaqq.estrogen.config.EstrogenClientConfig
//import dev.mayaqq.estrogen.config.Instance
import dev.mayaqq.estrogen.config.types.ChestConfig
import dev.mayaqq.estrogen.id
import dev.mayaqq.estrogen.injection.chestConfig
import dev.mayaqq.estrogen.network.EstrogenNetwork
import dev.mayaqq.estrogen.network.messages.c2s.SetChestConfigPacket
import net.minecraft.client.Minecraft
import net.minecraft.world.entity.EntityType
//import uwu.serenity.kittyconfig.api.defaults.load

internal var chestConfigSet = false

fun estrogenClient() {
    CynosureSplashLoader.amount += 30
    //EstrogenClientConfig.Instance.load()
    EstrogenKeybinds
    EstrogenRenderTypes
    HudOverlayRegistry.register(VanillaHud.FROSTBITE, id("dash"), DashOverlay)
    MothElytraModel.LAYER_LOCATION.registerDefinition(MothElytraModel.Companion::createBodyLayer)
    MothModel.LAYER_LOCATION.registerDefinition(MothModel::createBodyLayer)
    // registerResourcepackReloadListener(recipeId("dream_texture"), DreamTextureGenerator)
    registerResourcepackReloadListener(id("estrogen_armor_data"), BreastArmorDataLoader)

    if (isModLoaded("ears")) EarsCompat.boob()
}

@Subscription
internal fun addRenderLayers(event: RenderLayerRegistrationEvent) {
    event.addLayer(EntityType.ARMOR_STAND) { MothElytraLayer(it, event.models) }
    DefaultSkin.entries.forEach { skin ->
        event.addLayer(skin) { MothElytraLayer(it, event.models) }
        event.addLayer(skin) { BoobFeatureRenderer(it, Minecraft.getInstance().modelManager) }
    }
}

@Subscription
internal fun registerParticleRenderTypes(event: ParticleRenderTypeRegistrationEvent) {
    event.register(DashTrailParticle.RENDER_TYPE)
}

@Subscription
internal fun ticking(event: ClientTickEvent) {
    //TODO: THIS
    if (!chestConfigSet) {
        val player = Minecraft.getInstance().player ?: return
        val config = ChestConfig(EstrogenClientConfig.ChestFeature.enabled, EstrogenClientConfig.ChestFeature.armor, EstrogenClientConfig.ChestFeature.physics, EstrogenClientConfig.ChestFeature.bounciness.toFloat(), EstrogenClientConfig.ChestFeature.damping)
        player.chestConfig = config
        EstrogenNetwork.sendToServer(SetChestConfigPacket(config))
        chestConfigSet = true
    }


}