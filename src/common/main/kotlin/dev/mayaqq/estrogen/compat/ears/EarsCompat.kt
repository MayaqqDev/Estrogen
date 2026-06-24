package dev.mayaqq.estrogen.compat.ears

import com.unascribed.ears.api.EarsFeatureType
import com.unascribed.ears.api.registry.EarsInhibitorRegistry
import dev.mayaqq.estrogen.client.features.boobs.Boob.shouldShow
import dev.mayaqq.estrogen.config.EstrogenClientConfig
import dev.mayaqq.estrogen.injection.chestConfig
import net.minecraft.world.entity.player.Player


object EarsCompat {
    fun boob() {
        EarsInhibitorRegistry.register(
            "estrogen"
        ) { part: EarsFeatureType, peer: Any ->
                EstrogenClientConfig.Compat.ears &&
                part == EarsFeatureType.CHEST &&
                shouldShow(peer as Player) &&
                EstrogenClientConfig.ChestRenderingGlobal.rendering &&
                peer.chestConfig != null && peer.chestConfig!!.enabled
        }
    }
}