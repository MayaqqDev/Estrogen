package dev.mayaqq.estrogen.client.features.boobs

import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.utils.contains
import dev.mayaqq.estrogen.client.content.entityRenderers.boobs.BoobRendering
import dev.mayaqq.estrogen.client.content.entityRenderers.boobs.BoobRendering.ARMOR_TEXTURE_CACHE
import dev.mayaqq.estrogen.client.content.entityRenderers.boobs.TextureData
import dev.mayaqq.estrogen.client.features.boobs.BoobArmorHandling.getDefaultTexture
import dev.mayaqq.estrogen.client.features.boobs.data.BreastArmorDataLoader.getData
import dev.mayaqq.estrogen.config.EstrogenClientConfig
import dev.mayaqq.estrogen.config.types.ChestConfig
import dev.mayaqq.estrogen.content.EstrogenAttributes
import dev.mayaqq.estrogen.content.EstrogenTags
import dev.mayaqq.estrogen.injection.chestConfig
import dev.mayaqq.estrogen.mixin.client.PlayerModelMixin
import invoke.kitty.kritter.registry.api.entry.holder
import net.minecraft.client.player.AbstractClientPlayer
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite
import net.minecraft.client.renderer.texture.SimpleTexture
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import java.util.*

object Boob {

    /**
     * Calculate boob size for rendering based on the time the effect was applied.
     *
     * @param startTime   The time when the effect was applied. World time in ticks.
     * @param currentTime Current world time in ticks.
     * @param initialSize Size between 0 and 1. Added to the calculated size.
     * @param tickDelta   Time elapsed since the last tick.
     * @return Boob size. Floating point value between 0 and 1.
     */
    fun boobSize(startTime: Double, currentTime: Double, initialSize: Float, tickDelta: Float): Float {
        return Mth.clamp(((currentTime - startTime + tickDelta).toFloat() * 50 / 20000) + initialSize, 0.0f, 1.0f)
    }

    fun physicsCheck(player: Player, config: ChestConfig): Boolean {
        if (!config.physicsEnabled) return false
        if (EstrogenClientConfig.ChestRenderingGlobal.armorPhysicsHandling && shouldShowArmor(player)) {
            return when(player.getItemBySlot(EquipmentSlot.CHEST)) {
                in EstrogenTags.Items.CHEST_PHYSICS_DISABLE -> false
                in EstrogenTags.Items.CHEST_PHYSICS_ENABLE -> true
                else -> false
            }
        }
        return true
    }

    fun shouldShow(player: Player): Boolean {
        return player.getAttributeValue(EstrogenAttributes.ShowBoobs.holder) > 0.0 &&
                EstrogenClientConfig.ChestRenderingGlobal.rendering &&
                player.chestConfig?.enabled == true
    }

    @JvmStatic
    fun boobFunc(level: Float): Float {
        return 1.48f - Mth.invSqrt((level + 0.6f) * 0.95f)
    }

    @JvmStatic
    fun boobFuncSized(level: Float, player: Player): Float {
        val size = player.chestConfig?.scale?.let { it / 100F }?: 1F
        return boobFunc(level) * size
    }

    @JvmStatic
    // If armor should render like at all
    fun shouldShowArmor(player: Player): Boolean {
        return !player.getItemBySlot(EquipmentSlot.CHEST).isEmpty &&
                player.getItemBySlot(EquipmentSlot.CHEST) !in EstrogenTags.Items.CHEST_ARMOR_IGNORE &&
                EstrogenClientConfig.ChestRenderingGlobal.armorRendering &&
                player.chestConfig?.armorEnabled == true
    }
}