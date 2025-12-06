@file:EventSubscriber(env = [Environment.CLIENT])
package dev.mayaqq.estrogen.client

//import dev.mayaqq.estrogen.config.Instance
import dev.mayaqq.cynosure.client.entity.registerDefinition
import dev.mayaqq.cynosure.client.events.ClientReloadListenerEvent
import dev.mayaqq.cynosure.client.events.ClientTickEvent
import dev.mayaqq.cynosure.client.events.ParticleRenderTypeRegistrationEvent
import dev.mayaqq.cynosure.client.events.entity.RenderLayerRegistrationEvent
import dev.mayaqq.cynosure.client.render.gui.HudOverlayRegistry
import dev.mayaqq.cynosure.client.render.gui.VanillaHud
import dev.mayaqq.cynosure.client.splash.data.CynosureSplashLoader
import dev.mayaqq.cynosure.client.utils.DefaultSkin
import dev.mayaqq.cynosure.core.Environment
import dev.mayaqq.cynosure.core.isModLoaded
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.helpers.McPlayer
import dev.mayaqq.estrogen.client.content.EstrogenRenderTypes
import dev.mayaqq.estrogen.client.content.entityRenderers.boobs.BoobFeatureLayer
import dev.mayaqq.estrogen.client.content.entityRenderers.moth.MothModel
import dev.mayaqq.estrogen.client.content.entityRenderers.mothElytra.MothElytraLayer
import dev.mayaqq.estrogen.client.content.entityRenderers.mothElytra.MothElytraModel
import dev.mayaqq.estrogen.client.content.particles.DashTrailParticle
import dev.mayaqq.estrogen.client.cosmetics.CosmeticAPI
import dev.mayaqq.estrogen.client.cosmetics.CosmeticRenderLayer
import dev.mayaqq.estrogen.client.features.boobs.data.BreastArmorDataLoader
import dev.mayaqq.estrogen.client.features.dash.DashOverlay
import dev.mayaqq.estrogen.compat.ears.EarsCompat
import dev.mayaqq.estrogen.compat.recipeviewers.api.rei.ReiPluginRegister
import dev.mayaqq.estrogen.config.EstrogenClientConfig
import dev.mayaqq.estrogen.config.types.ChestConfig
import dev.mayaqq.estrogen.id
import dev.mayaqq.estrogen.injection.chestConfig
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityType
import uwu.serenity.kittyconfig.loadConfig


@JvmField
val THIGH_HIGH_ITEM_LOCATION: ResourceLocation = ModelResourceLocation(id("thigh_highs"), "inventory")

const val THIGH_HIGH_MODELS_DIRECTORY = "models/thigh_highs"

const val THIGH_HIGH_ITEM_TEXTURES = "textures/item/thigh_highs"

var chestConfigSet = false

fun estrogenClient() {
    loadConfig(EstrogenClientConfig)
    CynosureSplashLoader.amount += 30
    EstrogenRenderTypes
    CosmeticAPI
    HudOverlayRegistry.register(VanillaHud.FROSTBITE, id("dash"), DashOverlay)
    MothElytraModel.LAYER_LOCATION.registerDefinition(MothElytraModel.Companion::createBodyLayer)
    MothModel.LAYER_LOCATION.registerDefinition(MothModel::createBodyLayer)
    // registerResourcepackReloadListener(recipeId("dream_texture"), DreamTextureGenerator)

    if (isModLoaded("ears")) EarsCompat.boob()
    if (isModLoaded("roughlyenoughitems")) ReiPluginRegister.register()
}

@Subscription
internal fun onReloadListeners(event: ClientReloadListenerEvent) {
    event.register(id("estrogen_armor_data"), BreastArmorDataLoader)
}

@Subscription
internal fun addRenderLayers(event: RenderLayerRegistrationEvent) {
    event.addLayer(EntityType.ARMOR_STAND) { MothElytraLayer(it, event.models) }
    DefaultSkin.entries.forEach { skin ->
        event.addLayer(skin) { MothElytraLayer(it, event.models) }
        event.addLayer(skin) { BoobFeatureLayer(it, Minecraft.getInstance().modelManager) }
        event.addLayer(skin) { CosmeticRenderLayer(it) }
    }
}

@Subscription
internal fun registerParticleRenderTypes(event: ParticleRenderTypeRegistrationEvent) {
    event.register(DashTrailParticle.RenderType)
}

@Subscription
internal fun ticking(event: ClientTickEvent) {
    //Meh good enough
    if (!chestConfigSet && McClient.connection != null) {
        val player = McPlayer ?: return
        val config = ChestConfig(
            EstrogenClientConfig.ChestFeature.enabled,
            EstrogenClientConfig.ChestFeature.armor,
            EstrogenClientConfig.ChestFeature.physics,
            EstrogenClientConfig.ChestFeature.bounciness,
            EstrogenClientConfig.ChestFeature.damping
        )
        player.chestConfig = config
        ChestConfig.sync()
        chestConfigSet = true
    }
}