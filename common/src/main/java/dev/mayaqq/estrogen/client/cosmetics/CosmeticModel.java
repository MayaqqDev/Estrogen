package dev.mayaqq.estrogen.client.cosmetics;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import com.teamresourceful.resourcefullib.common.utils.files.GlobalStorage;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.cosmetics.model.BakedCosmeticModel;
import dev.mayaqq.estrogen.client.cosmetics.model.CosmeticModelBakery;
import dev.mayaqq.estrogen.client.cosmetics.model.PreparedModel;
import net.minecraft.Optionull;
import net.minecraft.Util;
import net.minecraft.client.renderer.block.model.BlockModel;
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
    private DownloadableModel model;
    private Vector3f modelSize = DEFAULT_SIZE;

    public static CosmeticModel fromLocalFile(File file) {
        if(!file.isFile()) Estrogen.LOGGER.error("Invalid file");
        CosmeticModel model = new CosmeticModel("");
        model.model = new DownloadableModel(file, "");
        Util.backgroundExecutor().execute(model.model::load);
        return model;
    }

    public CosmeticModel(String url) {
        this.url = url;
        this.hash = DownloadedAsset.getUrlHash(url);
    }

    public Optional<BakedCosmeticModel> get() {
        checkOrDownload();
        return Optional.ofNullable(model.result);
    }

    public Vector3fc getModelSize() {
        if(modelSize == DEFAULT_SIZE) {
            modelSize = Optionull.mapOrDefault(model.result, BakedCosmeticModel::computeModelSize, DEFAULT_SIZE);
        }
        return modelSize;
    }


    private void checkOrDownload() {
        if(this.model != null || this.url == null) return;
        this.model = new DownloadableModel(CACHE.resolve(hash).toFile(), this.url);
        Util.backgroundExecutor().execute(model::load);
    }

    private static class DownloadableModel {

        @Nullable
        private final File file;
        @Nullable
        private final String url;
        @Nullable
        private CompletableFuture<Void> future;

        private volatile BakedCosmeticModel result;

        private DownloadableModel(@Nullable File file, @Nullable String url) {
            this.file = file;
            this.url = url;
        }

        public void load() {
            if(this.future == null) {
                Optional<PreparedModel> optional1 = (file != null && file.isFile()) ? load(() -> new FileReader(file)) : Optional.empty();

                if(optional1.isPresent()) {
                    bake(optional1.get());
                } else {
                    future = DownloadedAsset.runDownload(
                        url,
                        file,
                        stream -> this.load(() -> new InputStreamReader(stream)).ifPresent(this::bake)
                    );
                }
            }
        }

        private void bake(PreparedModel base) {
            try {
                this.result = CosmeticModelBakery.bake(base);
            } catch (Exception e) {
                Estrogen.LOGGER.error("Failed to bake cosmetic model: {}", url, e);
            }

        }

        private Optional<PreparedModel> load(Callable<Reader> supplier) {
            try(Reader reader = supplier.call()) {
                return PreparedModel.CODEC.decode(JsonOps.INSTANCE, JsonParser.parseReader(reader))
                    .resultOrPartial(Estrogen.LOGGER::error)
                    .map(Pair::getFirst);
            } catch (Exception ex) {
                Estrogen.LOGGER.error("Failed to load cosmetic model: {}", url, ex);
                return Optional.empty();
            }
        }
    }


}
