package dev.mayaqq.estrogen.client.cosmetics;

import com.google.common.hash.Hashing;
import com.teamresourceful.resourcefullib.common.lib.Constants;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class DownloadedAsset {
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
}
