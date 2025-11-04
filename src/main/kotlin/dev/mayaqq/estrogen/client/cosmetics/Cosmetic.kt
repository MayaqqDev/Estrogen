package dev.mayaqq.estrogen.client.cosmetics

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.mayaqq.cynosure.core.codecs.fieldOf
import dev.mayaqq.cynosure.utils.colors.White
import dev.mayaqq.cynosure.utils.file.GlobalStorage
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.client.cosmetics.assets.CosmeticAnimation
import dev.mayaqq.estrogen.client.cosmetics.assets.CosmeticModel
import dev.mayaqq.estrogen.client.cosmetics.assets.CosmeticTexture
import dev.mayaqq.estrogen.utils.render.render
import net.minecraft.client.model.PlayerModel
import net.minecraft.client.player.AbstractClientPlayer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.layers.RenderLayer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import org.joml.Vector2d
import java.nio.file.Path
import java.util.*
import kotlin.jvm.optionals.getOrNull
import kotlin.math.max


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
     * @param matrices PoseStack with transformations
     * @param light lighting
     */
    fun render(
        renderType: (ResourceLocation) -> RenderType,
        source: MultiBufferSource,
        stack: PoseStack,
        light: Int,
        overlay: Int
    ) {
        // animations
        model.result?.mesh?.render(
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
    }
}

class CosmeticRenderLayer(renderer: RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>) : RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>(renderer) {
    override fun render(
        stack: PoseStack,
        buffer: MultiBufferSource,
        packedLight: Int,
        player: AbstractClientPlayer,
        limbSwing: Float,
        limbSwingAmount: Float,
        partialTick: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float
    ) {
        val cosmetic: Cosmetic = player.getUUID().getCosmetic() ?: return

        stack.pushPose()
        stack.mulPose(Axis.XP.rotationDegrees(180F))
        stack.scale(0.75f, 0.75f, 0.75f)

        val hDiff = Vector2d.distance(player.xOld, player.zOld, player.x, player.z)
        val yDiff = player.yOld - player.position().y

        val y = max(Mth.lerp(0.3, yDiff, 0.0), 0.0) * 1.5f
        val z = -Mth.lerp(0.3, hDiff, 0.0) * 1.25f

        stack.translate(0.0, 0.0, z - 1)

        val bl: Boolean = cosmetic.animation == null

        stack.translate(0.0, if (bl) (Mth.sin(ageInTicks / 10) / 4) - yDiff + y else .125 - yDiff + y, 0.0)

        if (bl) {
            stack.translate(0.5f, 0.5f, 0.5f)
            stack.mulPose(Axis.YP.rotationDegrees((ageInTicks * 1) % 360f))
            stack.translate(-0.5f, -0.5f, -0.5f)
        }

        cosmetic.render(
            RenderType::entityCutout,
            buffer,
            stack,
            packedLight,
            OverlayTexture.NO_OVERLAY
        )

        stack.popPose()
    }
}