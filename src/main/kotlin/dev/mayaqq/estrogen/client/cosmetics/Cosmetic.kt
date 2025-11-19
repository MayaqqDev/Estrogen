@file:EventSubscriber(env = [Environment.CLIENT])
package dev.mayaqq.estrogen.client.cosmetics

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.mayaqq.cynosure.client.events.ClientTickEvent
import dev.mayaqq.cynosure.client.models.animations.Animatable
import dev.mayaqq.cynosure.client.models.animations.animate
import dev.mayaqq.cynosure.client.models.baked.BakedModelTree
import dev.mayaqq.cynosure.client.models.baked.Mesh
import dev.mayaqq.cynosure.core.Environment
import dev.mayaqq.cynosure.core.codecs.fieldOf
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.helpers.McPlayer
import dev.mayaqq.cynosure.utils.colors.White
import dev.mayaqq.cynosure.utils.file.GlobalStorage
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.client.cosmetics.assets.CosmeticAnimation
import dev.mayaqq.estrogen.client.cosmetics.assets.CosmeticModel
import dev.mayaqq.estrogen.client.cosmetics.assets.CosmeticTexture
import dev.mayaqq.estrogen.utils.render.render
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import org.joml.Vector3fc
import java.nio.file.Path
import java.util.*
import kotlin.jvm.optionals.getOrNull


val CACHE: Path = GlobalStorage.getCache(MOD_ID).resolve("cosmetics")

data class Cosmetic(
    val id: String,
    val name: String,
    val texture: CosmeticTexture,
    val model: CosmeticModel,
    val animation: CosmeticAnimation?
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
        renderType: (ResourceLocation) -> RenderType,
        source: MultiBufferSource,
        stack: PoseStack,
        light: Int,
        overlay: Int
    ) {
        animation?.result?.let {
            (model.result as? Animatable.Provider)?.animate(it, animationTime)
        }
        val model = model.result ?: return
        model.render(
            source.getBuffer(renderType.invoke(texture.getResourceLocation())),
            stack,
            White,
            light,
            overlay
        )
    }

    companion object {
        fun codec(id: String): Codec<Cosmetic> = RecordCodecBuilder.create { instance -> instance.group(
            RecordCodecBuilder.point(id),
            Codec.STRING fieldOf Cosmetic::name,
            CosmeticTexture.CODEC fieldOf Cosmetic::texture,
            CosmeticModel.CODEC fieldOf Cosmetic::model,
            CosmeticAnimation.CODEC.optionalFieldOf("animation").forGetter{Optional.ofNullable(it.animation)}
        ).apply(instance) { id, name, texture, model, animation ->
            Cosmetic(id, name, texture, model, animation.getOrNull())
        }}

        var animationTicks = 0

        val animationTime: Long get() = (Mth.lerp(Minecraft.getInstance().frameTime, animationTicks.toFloat(), animationTicks + 1F) * 50L).toLong()
    }
}

@Subscription
fun onTick(event: ClientTickEvent.End) {
    Cosmetic.animationTicks++
    if (Cosmetic.animationTicks > 30000000) Cosmetic.animationTicks = 0
}