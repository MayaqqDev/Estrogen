package dev.mayaqq.estrogen.client.cosmetics;

import com.teamresourceful.resourcefullib.common.utils.files.GlobalStorage;
import dev.mayaqq.estrogen.Estrogen;
import it.unimi.dsi.fastutil.Pair;
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
    public static final Pair<Vector3f, Vector3f> ZERO_BOUNDS = Pair.of(new Vector3f(), new Vector3f());

    private final String url;
    private final String hash;
    private DownloadableModel model;

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

    public Vector3fc minBound() {
        checkOrDownload();
        return Optionull.mapOrElse(model.result, BakedCosmeticModel::getMaxBound, Vector3f::new);
    }

    public Vector3fc maxBound() {
        checkOrDownload();
        return Optionull.mapOrElse(model.result, BakedCosmeticModel::getMaxBound, Vector3f::new);
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
                Optional<BlockModel> optional1 = (file != null && file.isFile()) ? load(() -> new FileReader(file)) : Optional.empty();

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

        private void bake(BlockModel base) {
            try {
                this.result = CosmeticModelBakery.bake(base.getElements());
            } catch (Exception e) {
                Estrogen.LOGGER.error("Failed to bake cosmetic model: {}", url, e);
            }

        }

        private Optional<BlockModel> load(Callable<Reader> supplier) {
            try(Reader reader = supplier.call()) {
                return Optional.of(BlockModel.fromStream(reader));
            } catch (Exception ex) {
                Estrogen.LOGGER.error("Failed to load cosmetic model: {}", url, ex);
                return Optional.empty();
            }
        }
    }


}
