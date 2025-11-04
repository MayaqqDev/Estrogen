package dev.mayaqq.estrogen.client.cosmetics.assets

import com.google.common.hash.Hashing
import dev.mayaqq.cynosure.core.TriState
import dev.mayaqq.estrogen.client.cosmetics.CosmeticAPI
import net.minecraft.Util
import org.apache.commons.io.FileUtils
import org.apache.commons.io.FilenameUtils
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Path
import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicReference


abstract class DownloadAsset<T, R>(
    val cache: Path,
    val url: String,
    val hash: String = url.getUrlHash()
) {
    private var downloadState = AtomicReference<TriState>()
    private var future: CompletableFuture<Void>? = null

    @Volatile
    var result: R? = null
        get() = run {
            checkOrDownload()
            field
        }
        protected set

    protected fun checkOrDownload() {
        if (downloadState.get() != TriState.INTERMEDIATE) return
        downloadState.setRelease(TriState.FALSE)
        Util.backgroundExecutor().execute { this.load(cache.resolve(hash).toFile(), url) }
    }


    fun load(file: File?, url: String) {
        if (this.future == null) {

            val type = if (file != null && file.isFile()) read { FileReader(file) } else null

            type?.consumeResult() ?: run {
                future = runDownload(url, file!!) { stream ->
                    read { InputStreamReader(stream) }?.consumeResult()
                }
            }
        }
    }

    private fun T.consumeResult() {
        downloadState.set(TriState.TRUE)
        this.onLoad()
    }

    protected abstract fun T.onLoad()
    protected abstract fun read(reader: () -> Reader): T?

    companion object {
        private val CLIENT: HttpClient = HttpClient
            .newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build()

        val ALLOWED_DOMAINS = setOf(
            "teamresourceful.com",
            "files.teamresourceful.com",
            "raw.githubusercontent.com",
            "femboy-hooters.net",
            "images.teamresourceful.com"
        )

        fun runDownload(uri: String?, file: File, callback: (InputStream) -> Unit): CompletableFuture<Void> {
            return CompletableFuture.runAsync( {
                uri.createUrl()?.let { url ->
                    try {
                        val request = HttpRequest.newBuilder(url)
                            .GET()
                            .build()

                        val stream = CLIENT.send(request, HttpResponse.BodyHandlers.ofInputStream())
                        if (stream.statusCode() / 100 != 2) {
                            CosmeticAPI.error(
                                "Failed to download asset: {} Status Code: {}",
                                uri,
                                stream.statusCode()
                            )
                            return@runAsync
                        }

                        FileUtils.copyInputStreamToFile(stream.body(), file)
                        try {
                            FileUtils.openInputStream(file).use { fileStream ->
                                callback.invoke(fileStream)
                            }
                        } catch (ex: IOException) {
                            CosmeticAPI.error("Failed to process asset: {}", uri, ex)
                        }
                    } catch (ex: IOException) {
                        CosmeticAPI.error("Failed to download asset: {}", uri, ex)
                    } catch (ex: InterruptedException) {
                        CosmeticAPI.error("Failed to download asset: {}", uri, ex)
                    }
                }
            }, Util.backgroundExecutor())
        }

        @Suppress("DEPRECATION")
        fun String.getUrlHash(): String {
            val hashedUrl = FilenameUtils.getBaseName(this)
            return Hashing.sha1().hashUnencodedChars(hashedUrl).toString()
        }

        private fun String?.createUrl(): URI? {
            if (this == null) return null
            return try {
                val url: URI = URI.create(this)
                if (!ALLOWED_DOMAINS.contains(url.host)) {
                    CosmeticAPI.warn("Tried to load texture from disallowed domain: {}", url.host)
                    null
                } else {
                    if (!url.scheme.equals("https")) null else url
                }
            } catch (_: Exception) {
                null
            }
        }
    }
}