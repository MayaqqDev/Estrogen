package dev.mayaqq.estrogen.client.cosmetics.assets

import com.mojang.blaze3d.platform.NativeImage
import com.mojang.blaze3d.platform.TextureUtil
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.serialization.Codec
import dev.mayaqq.estrogen.client.cosmetics.CACHE
import dev.mayaqq.estrogen.client.cosmetics.CosmeticAPI
import dev.mayaqq.estrogen.client.cosmetics.assets.DownloadAsset.Companion.getUrlHash
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.SimpleTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.InputStream
import java.util.concurrent.CompletableFuture


class CosmeticTexture(
    val texture: String,
    val id: String = texture.getUrlHash(),
    val location: ResourceLocation = CosmeticAPI.id("textures/cosmetics/$id")
) {

    private var img: SimpleTexture? = null

    fun getResourceLocation(): ResourceLocation {
        checkOrDownload()
        return location
    }

    fun checkOrDownload() {
        if (this.img != null) return

        this.img = DownloadableTexture(CACHE.resolve("textures").resolve(this.id).toFile(), this.texture, this.location)
        Minecraft.getInstance().textureManager.register(this.location, this.img)
    }

    companion object {
        val CODEC: Codec<CosmeticTexture> = Codec.STRING.xmap(::CosmeticTexture, CosmeticTexture::texture)
    }

    class DownloadableTexture(val file: File?, val url: String?, location: ResourceLocation) : SimpleTexture(location) {

        private var future: CompletableFuture<Void>? = null
        private var uploaded = false

        private fun loadCallback(image: NativeImage) {
            Minecraft.getInstance().execute {
                this.uploaded = true
                if (!RenderSystem.isOnRenderThread()) {
                    RenderSystem.recordRenderCall { this.upload(image) }
                } else {
                    this.upload(image)
                }
            }
        }

        private fun upload(image: NativeImage) {
            TextureUtil.prepareImage(this.getId(), image.width, image.height)
            image.upload(0, 0, 0, true)
        }

        override fun load(manager: ResourceManager) {
            Minecraft.getInstance().execute {
                if (!this.uploaded) {
                    try {
                        super.load(manager)
                    } catch (_: Exception) {
                        // Do nothing as an error will almost always be thrown while loading.
                    }
                    this.uploaded = true
                }
            }
            if (this.future == null) {
                val nativeimage: NativeImage? = if (this.file != null && this.file.isFile()) this.load { FileUtils.openInputStream(file) } else null

                if (nativeimage != null) {
                    this.loadCallback(nativeimage)
                } else {
                    this.future = DownloadAsset.runDownload(
                        this.url,
                        this.file!!
                    ) { stream ->
                        this.load { stream }?.let { image: NativeImage -> this.loadCallback(image) }
                    }
                }
            }
        }

        private fun load(stream: () -> InputStream): NativeImage? {
            try {
                return NativeImage.read(stream.invoke())
            } catch (ex: Exception) {
                CosmeticAPI.error("Failed to load cosmetic texture: {}", url, ex)
                return null
            }
        }
    }
}