package dev.mayaqq.estrogen.client.features.boobs.data

import com.google.gson.JsonElement
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper

data class BreastArmorData(
    val textureLocation: ResourceLocation?,
    val overlayLocation: ResourceLocation?,
    val uv: Pair<Float, Float>,
    val leftUV: Pair<Float, Float>,
    val rightUV: Pair<Float, Float>,
    val textureSize: Pair<Float, Float>
) {
    companion object {
        fun fromJson(jsonElement: JsonElement): BreastArmorData {
            val json = jsonElement.asJsonObject
            return BreastArmorData(
                textureLocation = ResourceLocation.tryParse(GsonHelper.getAsString(json, "texture")),
                overlayLocation = json.takeIf { it.has("texture_overlay") }
                    ?.let { ResourceLocation.tryParse(GsonHelper.getAsString(it, "texture_overlay")) },
                uv = GsonHelper.getAsJsonArray(json, "uv").let { 
                    Pair(it[0].asFloat, it[1].asFloat) 
                },
                leftUV = GsonHelper.getAsJsonArray(json, "left_uv").let { 
                    Pair(it[0].asFloat, it[1].asFloat) 
                },
                rightUV = GsonHelper.getAsJsonArray(json, "right_uv").let { 
                    Pair(it[0].asFloat, it[1].asFloat) 
                },
                textureSize = GsonHelper.getAsJsonArray(json, "size").let { 
                    Pair(it[0].asFloat, it[1].asFloat) 
                }
            )
        }
    }
}