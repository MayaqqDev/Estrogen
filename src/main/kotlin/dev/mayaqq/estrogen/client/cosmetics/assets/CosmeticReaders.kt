package dev.mayaqq.estrogen.client.cosmetics.assets

import com.mojang.blaze3d.platform.NativeImage
import dev.mayaqq.cynosure.client.models.ModelData
import dev.mayaqq.cynosure.client.models.animations.AnimationDefinition
import dev.mayaqq.cynosure.client.models.bake
import dev.mayaqq.cynosure.client.models.baked.CustomBakedModel
import dev.mayaqq.cynosure.utils.Couple
import dev.mayaqq.cynosure.utils.coroutines.MinecraftClient
import dev.mayaqq.estrogen.client.cosmetics.ModelOutlineGenerator
import dev.mayaqq.estrogen.utils.render.MeshBuilder
import invoke.kitty.kritter.utils.result.and
import invoke.kitty.kritter.utils.result.flatMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.DynamicTexture
import net.minecraft.resources.ResourceLocation
import org.joml.Vector2f
import org.joml.Vector3f

object CosmeticReaders {

    private val MODEL_DATA: CosmeticAsset.Reader<ModelData> = CosmeticAsset.Reader.Json(ModelData.CODEC)

    val TEXTURE: CosmeticAsset.Reader<ResourceLocation> = CosmeticAsset.Reader { bytes ->
        withContext(Dispatchers.MinecraftClient) {
            runCatching {
                val image = NativeImage.read(bytes)
                Minecraft.getInstance().textureManager.register("estrogen_cosmetic", DynamicTexture(image))
            }
        }
    }

    val MODEL: CosmeticAsset.Reader<Couple<CustomBakedModel>> = CosmeticAsset.Reader { bytes ->
        MODEL_DATA.decode(bytes).flatMap { it.bake() and ModelOutlineGenerator.process(it).bake() } //.map(::smoothNormals)
    }

    val ANIMATION: CosmeticAsset.Reader<AnimationDefinition> = CosmeticAsset.Reader.Json(AnimationDefinition.CODEC)
}

fun smoothNormals(model: CustomBakedModel): CustomBakedModel {
    val mesh = model.mesh
    val builder = MeshBuilder(mesh.vertexCount)
    val normalVec = Vector3f()

    infix fun Float.eq(other: Float) =
        this == other || (this - other) < 0.001

    for (v in 0..<mesh.vertexCount) {
        normalVec.set(0f)

        for (v2 in 0..<mesh.vertexCount)
            if (mesh.x(v) eq mesh.x(v2) && mesh.y(v) eq mesh.y(v2) && mesh.z(v) eq mesh.z(v2))
                normalVec.add(mesh.normalX(v2), mesh.normalY(v2), mesh.normalZ(v2))

        normalVec.normalize()

        builder.addVertex(mesh.x(v), mesh.y(v), mesh.z(v))
            .setUv(mesh.u(v), mesh.v(v))
            .setNormal(normalVec.x, normalVec.y, normalVec.z)
    }

    return CustomBakedModel(builder.build(), model.renderType, model.minBound, model.maxBound)
}

fun generateOutlineModel(model: CustomBakedModel): CustomBakedModel {
    val mesh = model.mesh
    val builder = MeshBuilder(mesh.vertexCount)

    val center = model.minBound.add(model.maxBound, Vector3f()).div(2f)

    for (i in 0..<mesh.vertexCount step 4) {
        val centeruv = Vector2f()

        for (vi in 0..<4)
            centeruv.add(mesh.u(vi + i), mesh.v(vi + i))
        centeruv.div(4f)

        val v = Vector3f()
        val uv = Vector2f()
        for (vi in 0..<4) {
            val vii = vi + i
            v.set(mesh.x(vii), mesh.y(vii), mesh.z(vii)).sub(center).mul(1.25f).add(center)
            uv.set(mesh.u(vii), mesh.v(vii)).sub(centeruv).mul(1.25f).add(centeruv)
            builder.addVertex(v.x, v.y, v.z)
                .setUv(mesh.u(vii), mesh.v(vii))
                .setNormal(mesh.normalX(vii), mesh.normalY(vii), mesh.normalZ(vii))
        }
    }

    return CustomBakedModel(builder.build(), model.renderType, model.minBound, model.maxBound)
}