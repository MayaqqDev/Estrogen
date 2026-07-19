package dev.mayaqq.estrogen.client.cosmetics.assets

import com.google.gson.JsonParser
import com.mojang.serialization.Codec
import com.mojang.serialization.JsonOps
import dev.mayaqq.cynosure.core.VersionHooks.Impl.toKtResult
import dev.mayaqq.cynosure.utils.coroutines.Background
import dev.mayaqq.cynosure.utils.result.UNIT
import dev.mayaqq.cynosure.utils.result.failure
import dev.mayaqq.cynosure.utils.result.success
import dev.mayaqq.estrogen.client.cosmetics.CosmeticAPI
import dev.mayaqq.estrogen.client.cosmetics.getUrlHash
import invoke.kitty.kritter.utils.io.readBytesAsync
import invoke.kitty.kritter.utils.io.writeBytesAsync
import invoke.kitty.kritter.utils.result.getOr
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.future.asDeferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import java.io.IOException
import java.lang.ref.Cleaner
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Path
import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.suspendCoroutine
import kotlin.io.path.createParentDirectories
import kotlin.io.path.deleteIfExists
import kotlin.io.path.div
import kotlin.io.path.exists
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
class CosmeticAsset<T>(
    val url: String,
    val reader: Reader<T>,
    val cacheDir: Path,
    val id: String = url.getUrlHash()
) : AutoCloseable {

    private val downloadScope: CoroutineScope = CoroutineScope(Dispatchers.Background)
    private val cleanable: Cleaner.Cleanable = CLEANER.register(this, CleanAction(downloadScope))

    private val state: AtomicInteger = AtomicInteger(-1)
    private lateinit var deferred: Deferred<T?>

    fun get(): T? {
        if (!state.compareAndSet(INITIAL, DOWNLOADING)) {
            val deferred = this.deferred
            return if (deferred.isCompleted) deferred.getCompleted() else null
        }

        try {
            val deferred = downloadScope.async {
                loadOrDownload()
                    .onSuccess { state.set(COMPLETED) }
                    .onFailure {
                        CosmeticAPI.error("Failed to load cosmetic asset '{}' from '{}'", id, url, it)
                        state.set(FAILED)
                    }
                    .getOrNull()
            }

            this.deferred = deferred
            return if (deferred.isCompleted) deferred.getCompleted() else null
        } catch (ex: Exception) {
            CosmeticAPI.error("Error caught loading cosmetic asset '{}' from '{}'", id, url, ex)
            deferred = CompletableDeferred(null)
            state.set(FAILED)
            return null
        }
    }

    private suspend fun loadOrDownload(): Result<T> {
        val file = cacheDir / id
        if (file.exists())
            try {
                CosmeticAPI.debug("Loading asset '{}' from cache", id)
                return reader.decode(file.readBytesAsync())
            } catch (ex: IOException) {
                CosmeticAPI.warn("Failed to load asset '{} from file '{}', redownloading", id, file, ex)
                try { file.deleteIfExists() } catch (_: IOException) {}
            }

        CosmeticAPI.debug("Downloading asset '{}' from '{}'...", id, url)
        val bytes = runDownload(url).getOr { return it.failure() }

        downloadScope.launch {
            try {
                file.createParentDirectories()
                file.writeBytesAsync(bytes)
            } catch (ex: Exception) {
                CosmeticAPI.error("Failed to write to cache file, continuing anyways", ex)
            }
        }

        return reader.decode(bytes)
    }

    private suspend fun runDownload(uri: String): Result<ByteArray> {
        val request = HttpRequest.newBuilder(uri.createURI().getOr { return it.failure() })
            .GET()
            .build()

        for (i in 1..3) {
            val result: Result<ByteArray>? =
                withTimeoutOrNull(2.minutes) {
                    val response = CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray())
                        .asDeferred()
                        .await()

                    if (response.statusCode() / 100 == 2)
                        response.body().success()
                    else
                        Result.failure("Failed to download asset from '$uri'. Status Code: ${response.statusCode()}")
                }

            if (result != null && result.isSuccess) return result

            CosmeticAPI.warn("Failed to download cosmetic asset (attempt {}), retrying in 10 seconds. Error: {}", i, result ?: "Timeout reached")
            delay(10.seconds)
        }

        return Result.failure("All download attempts failed, see log for messages")
    }

    override fun close() {
        cleanable.clean()
    }

    fun interface Reader<T> {

        suspend fun decode(bytes: ByteArray): Result<T>

        data class Json<T>(val codec: Codec<T>) : Reader<T> {
            override suspend fun decode(bytes: ByteArray): Result<T> =
                codec.parse(JsonOps.INSTANCE, JsonParser.parseString(bytes.toString(Charsets.UTF_8)))
                    .toKtResult()
        }
    }

    private class CleanAction(val scopeToClose: CoroutineScope) : Runnable {
        override fun run() {
            scopeToClose.cancel()
        }
    }

    companion object {
        private val CLEANER: Cleaner = Cleaner.create()
        private const val INITIAL = -1
        private const val DOWNLOADING = 0
        private const val COMPLETED = 1
        private const val FAILED = 2

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

        private fun String.createURI(): Result<URI> {
            val url: URI = URI.create(this)
            return if (!ALLOWED_DOMAINS.contains(url.host)) {
                CosmeticAPI.warn("Tried to load texture from disallowed domain: {}", url.host)
                Result.failure("Cannot load from disallowed domain '$url'")
            } else {
                if (!url.scheme.equals("https"))
                    Result.failure("Only https urls are allowed")
                else
                    url.success()
            }
        }

        fun <T> codec(cacheDir: Path, reader: Reader<T>): Codec<CosmeticAsset<T>> =
            Codec.STRING.xmap(fun(url) = CosmeticAsset(url, reader, cacheDir), CosmeticAsset<*>::url)

    }
}