package dev.mayaqq.estrogen.client.cosmetics;

import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import com.teamresourceful.resourcefullib.common.utils.TriState;
import com.teamresourceful.resourcefullib.common.utils.files.GlobalStorage;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.cosmetics.model.BakedCosmeticModel;
import dev.mayaqq.estrogen.client.cosmetics.model.CosmeticModelBakery;
import dev.mayaqq.estrogen.client.cosmetics.model.PreparedModel;
import net.minecraft.Optionull;
import net.minecraft.Util;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.io.*;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

public class CosmeticModel {
    public static final String NAMESPACE = "estrogen_cosmetic";
    public static final Path CACHE = GlobalStorage.getCacheDirectory(Estrogen.MOD_ID).resolve("cosmetics").resolve("models");
    private static final Vector3f DEFAULT_SIZE = new Vector3f(16f, 16f, 16f);


    private final String url;
    private final String hash;

    private volatile BakedCosmeticModel result;
    private volatile TriState downloadState = TriState.UNDEFINED;
    private volatile CompletableFuture<Void> future;

    private Vector3f modelSize = DEFAULT_SIZE;

    public static CosmeticModel fromLocalFile(File file) {
        if(!file.isFile()) Cosmetics.LOGGER.error("Invalid file");
        CosmeticModel model = new CosmeticModel("");
        model.load(file, "");
        return model;
    }

    public CosmeticModel(String url) {
        this.url = url;
        this.hash = DownloadedAsset.getUrlHash(url);
    }

    public Optional<BakedCosmeticModel> get() {
        checkOrDownload();
        return Optional.ofNullable(result);
    }

    public Vector3fc getModelSize() {
        checkOrDownload();
        if(modelSize == DEFAULT_SIZE) {
            modelSize = Optionull.mapOrDefault(result, BakedCosmeticModel::computeModelSize, DEFAULT_SIZE);
        }
        return modelSize;
    }


    private void checkOrDownload() {
        if(downloadState != TriState.UNDEFINED || this.url == null) return;
        downloadState = TriState.FALSE;
        Util.backgroundExecutor().execute(() -> this.load(CACHE.resolve(hash).toFile(), url));
    }

    private void load(File file, String url) {
        if(this.future == null) {
            Optional<PreparedModel> optional = (file != null && file.isFile())
                ? read(() -> new FileReader(file))
                : Optional.empty();

            optional.ifPresentOrElse(this::bake,
                () -> future = DownloadedAsset.runDownload(url, file, stream ->
                    read(() -> new InputStreamReader(stream)).ifPresent(this::bake))
            );
        }
    }

    private void bake(PreparedModel base) {
        try {
            this.result = CosmeticModelBakery.bake(base);
        } catch (Exception e) {
            Cosmetics.LOGGER.error("Failed to bake cosmetic model: {}", url, e);
        }

    }

    private Optional<PreparedModel> read(Callable<Reader> supplier) {
        try(Reader reader = supplier.call()) {
            return PreparedModel.CODEC.decode(JsonOps.INSTANCE, JsonParser.parseReader(reader))
                .resultOrPartial(Cosmetics.LOGGER::error)
                .map(Pair::getFirst);
        } catch (Exception ex) {
            Cosmetics.LOGGER.error("Failed to load cosmetic model: {}", url, ex);
            return Optional.empty();
        }
    }


}
