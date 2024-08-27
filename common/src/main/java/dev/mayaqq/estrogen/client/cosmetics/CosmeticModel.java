package dev.mayaqq.estrogen.client.cosmetics;

import com.teamresourceful.resourcefullib.common.utils.files.GlobalStorage;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.client.renderer.block.model.BlockModel;
import org.jetbrains.annotations.Nullable;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class CosmeticModel {
    public static final String NAMESPACE = "estrogen_cosmetic";
    public static final Path CACHE = GlobalStorage.getCacheDirectory(Estrogen.MOD_ID).resolve("cosmetics").resolve("models");

    private final String url;
    private final String hash;
    private DownloadableModel model;

    public static CosmeticModel fromFile(File file) {
        if(!file.isFile()) Estrogen.LOGGER.error("Invalid file");
        CosmeticModel model = new CosmeticModel("");
        model.model = new DownloadableModel(file, "");
        CompletableFuture.runAsync(model.model::load);
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

    private void checkOrDownload() {
        if(this.model != null || this.url == null) return;
        this.model = new DownloadableModel(CACHE.resolve(hash).toFile(), this.url);
        CompletableFuture.runAsync(model::load);
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
                Optional<BlockModel> optional1 = (file != null && file.isFile()) ? load(file) : Optional.empty();

                if(optional1.isPresent()) {
                    loadCallback(optional1.get());
                } else {
                    future = DownloadedAsset.runDownload(
                        url,
                        file,
                        stream -> this.load(file).ifPresent(this::loadCallback)
                    );
                }
            }
        }

        private void loadCallback(BlockModel base) {
            this.result = CosmeticModelBakery.bake(base.getElements());
        }

        private Optional<BlockModel> load(File file) {
            try(FileReader reader = new FileReader(file)) {
                return Optional.of(BlockModel.fromStream(reader));
            } catch (Exception e) {
                Estrogen.LOGGER.error("Failed to load cosmetic model: {}", file.getName(), e);
                return Optional.empty();
            }
        }
    }


}
