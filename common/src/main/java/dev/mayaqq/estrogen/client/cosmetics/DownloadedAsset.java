package dev.mayaqq.estrogen.client.cosmetics;

import com.google.common.hash.Hashing;
import com.teamresourceful.resourcefullib.common.lib.Constants;
import com.teamresourceful.resourcefullib.common.utils.TriState;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.Util;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public abstract class DownloadedAsset<T> {

    public static final Set<String> ALLOWED_DOMAINS = Set.of(
        "teamresourceful.com",
        "files.teamresourceful.com",
        "raw.githubusercontent.com",
        "femboy-hooters.net",
        "images.teamresourceful.com"
    );

    private static final HttpClient CLIENT = HttpClient
        .newBuilder()
        .followRedirects(HttpClient.Redirect.NORMAL)
        .build();

    protected final String url;
    protected final String hash;

    private final Path cache;
    private final AtomicReference<TriState> downloadState = new AtomicReference<>(TriState.UNDEFINED);
    private CompletableFuture<Void> future;

    protected DownloadedAsset(Path cachePath, String url) {
        this.cache = cachePath;
        this.url = url;
        this.hash = getUrlHash(url);
    }

    public static CompletableFuture<Void> runDownload(String uri, File file, Consumer<InputStream> callback) {
        return CompletableFuture.runAsync(() ->
            createUrl(uri).ifPresent(url -> {

                try {
                    HttpRequest request = HttpRequest.newBuilder(url)
                        .GET()
                        .build();

                    HttpResponse<InputStream> stream = CLIENT.send(request, HttpResponse.BodyHandlers.ofInputStream());
                    if (stream.statusCode() / 100 != 2) {
                        Estrogen.LOGGER.error("Failed to download asset: {} Status Code: {}", uri, stream.statusCode());
                        return;
                    }

                    FileUtils.copyInputStreamToFile(stream.body(), file);
                    try(InputStream fileStream = FileUtils.openInputStream(file)) {
                        callback.accept(fileStream);
                    } catch (IOException ex) {
                        Estrogen.LOGGER.error("Failed to process asset: {}", uri, ex);
                    }

                } catch (IOException | InterruptedException ex) {
                    Estrogen.LOGGER.error("Failed to download asset: {}", uri, ex);
                }
            }), Util.backgroundExecutor());
    }

    @SuppressWarnings({"deprecation"})
    public static String getUrlHash(String url) {
        String hashedUrl = FilenameUtils.getBaseName(url);
        return Hashing.sha1().hashUnencodedChars(hashedUrl).toString();
    }

    private static Optional<URI> createUrl(@Nullable String string) {
        if (string == null) return Optional.empty();
        try {
            URI url = URI.create(string);
            if (!ALLOWED_DOMAINS.contains(url.getHost())) {
                Constants.LOGGER.warn("Tried to load texture from disallowed domain: {}", url.getHost());
                return Optional.empty();
            }
            if (!url.getScheme().equals("https")) return Optional.empty();
            return Optional.of(url);
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }

    protected void checkOrDownload() {
        if(downloadState.get() != TriState.UNDEFINED || this.url == null) return;
        downloadState.setRelease(TriState.FALSE);
        Util.backgroundExecutor().execute(() -> this.load(cache.resolve(hash).toFile(), url));
    }

    protected void load(File file, String url) {
        if(this.future == null) {
            Optional<T> optional = (file != null && file.isFile())
                ? read(() -> new FileReader(file))
                : Optional.empty();

            optional.ifPresentOrElse(this::consumeResult, () ->
                future = runDownload(
                    url, file,
                    stream -> read(() -> new InputStreamReader(stream))
                        .ifPresent(this::consumeResult)
                )
            );
        }
    }

    private void consumeResult(T object) {
        downloadState.set(TriState.TRUE);
        this.onLoad(object);
    }

    protected abstract void onLoad(T object);

    protected abstract Optional<T> read(Callable<Reader> readerSource);

}
