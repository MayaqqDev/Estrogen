@file:EventSubscriber(Side.CLIENT)
package dev.mayaqq.estrogen.client.cosmetics

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.mayaqq.cynosure.client.events.ClientTickEvent
import dev.mayaqq.cynosure.client.models.animations.Animatable
import dev.mayaqq.cynosure.client.models.animations.AnimationDefinition
import dev.mayaqq.cynosure.client.models.animations.animate
import dev.mayaqq.cynosure.client.models.baked.CustomBakedModel
import dev.mayaqq.cynosure.core.codecs.fieldOf
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.utils.file.GlobalStorage
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.client.cosmetics.assets.CosmeticAsset
import dev.mayaqq.estrogen.client.cosmetics.assets.CosmeticReaders
import invoke.kitty.kritter.platform.Side
import invoke.kitty.kritter.utils.color.Color
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import java.nio.file.Path
import java.util.*
import kotlin.io.path.div
import kotlin.jvm.optionals.getOrNull


val CACHE: Path = GlobalStorage.getCache(MOD_ID).resolve("cosmetics")

data class Cosmetic(
    val id: String,
    val name: String,
    val texture: CosmeticAsset<ResourceLocation>,
    val model: CosmeticAsset<CustomBakedModel>,
    val animation: CosmeticAsset<AnimationDefinition>?
) {
    /**
     * Use this for rendering cosmetics
     * @param renderType Render type function, provides a RenderType for the texture, e.g. RenderType::entityCutout
     * @param source MultiBufferSource to render this cosmetic into
     * @param stack PoseStack with transformations
     * @param light lighting
     * @param overlay UV Overlay
     */
    fun render(
        bufferSource: MultiBufferSource,
        renderType: (ResourceLocation) -> RenderType,
        stack: PoseStack,
        color: Color,
        light: Int,
        overlay: Int
    ) {
        animation?.get()?.let {
            (model.get() as? Animatable.Provider)?.animate(it, animationTime)
        }
        val model = model.get() ?: return
        val texture = texture.get() ?: return
        val buffer = bufferSource.getBuffer(renderType(texture))
        model.render(buffer, stack, color, light, overlay)
    }

    companion object {
        fun codec(id: String): Codec<Cosmetic> = RecordCodecBuilder.create { instance -> instance.group(
            RecordCodecBuilder.point(id),
            Codec.STRING fieldOf Cosmetic::name,
            CosmeticAsset.codec(CACHE/"textures", CosmeticReaders.TEXTURE) fieldOf Cosmetic::texture,
            CosmeticAsset.codec(CACHE/"models", CosmeticReaders.MODEL) fieldOf Cosmetic::model,
            CosmeticAsset.codec(CACHE/"animations", CosmeticReaders.ANIMATION).optionalFieldOf("animation")
                .forGetter{Optional.ofNullable(it.animation)}
        ).apply(instance) { id, name, texture, model, animation ->
            Cosmetic(id, name, texture, model, animation.getOrNull())
        }}

        var animationTicks = 0

        // TODO: check if frameTimeNs is same as previously just frameTime
        val animationTime: Long get() = (Mth.lerp(Minecraft.getInstance().frameTimeNs.toDouble(), animationTicks.toDouble(), animationTicks + 1.0) * 50L).toLong()
    }
}

object CosmeticEvents {
    @Subscription
    fun onTick(event: ClientTickEvent.End) {
        Cosmetic.animationTicks++
        if (Cosmetic.animationTicks > 30000000) Cosmetic.animationTicks = 0
    }
}