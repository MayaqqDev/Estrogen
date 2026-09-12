package dev.mayaqq.estrogen.client.content.entityRenderers.boobs

import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.estrogen.client.features.boobs.BoobArmorHandling.getDefaultTexture
import dev.mayaqq.estrogen.client.features.boobs.data.BreastArmorDataLoader.getData
import net.minecraft.client.player.AbstractClientPlayer
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite
import net.minecraft.client.renderer.texture.SimpleTexture
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import java.util.Optional
import kotlin.collections.set

object BoobRendering {

    private val boobArmorTextureCache: MutableMap<ResourceLocation, AbstractTexture> = mutableMapOf()

    @JvmStatic
    val ARMOR_TEXTURE_CACHE = mutableMapOf<String, ResourceLocation>()

    @JvmStatic
    public fun getArmorTexture(player: AbstractClientPlayer, overlay: Boolean): Optional<TextureData> {
        val itemStack = player.getItemBySlot(EquipmentSlot.CHEST)
        if (itemStack.item !is ArmorItem) return Optional.empty<TextureData?>()
        val item = itemStack.item as ArmorItem
        // Check if the item is not like air or some shit
        val data = getData(BuiltInRegistries.ITEM.getKey(item))
        if (data != null) {
            return Optional.ofNullable<TextureData?>(data.toTextureData(overlay))
        } else {
            var layer: ArmorMaterial.Layer? = null
            if (item.getMaterial().value().layers().isNotEmpty()) {
                layer = item.getMaterial().value().layers().first()
            }
            val string = getDefaultTexture(item, itemStack, player, EquipmentSlot.CHEST, layer, overlay)
            val location: ResourceLocation = ARMOR_TEXTURE_CACHE.computeIfAbsent(string) { ResourceLocation.tryParse(it)!! }
            if (location != null) {
                val textureData = TextureData(location, 20f, 23f, 18f, 23f, 28f, 23f, 64.0f, 32.0f)
                val texture: AbstractTexture?
                if (boobArmorTextureCache.containsKey(textureData.location)) {
                    texture = boobArmorTextureCache[textureData.location]
                } else {
                    texture = McClient.textureManager.loadTexture(textureData.location, SimpleTexture(textureData.location))
                    boobArmorTextureCache[textureData.location] = texture
                }
                if (texture === MissingTextureAtlasSprite.getTexture()) return Optional.empty<TextureData?>()
                return Optional.of<TextureData?>(textureData)
            }
            return Optional.empty<TextureData?>()
        }
    }
}