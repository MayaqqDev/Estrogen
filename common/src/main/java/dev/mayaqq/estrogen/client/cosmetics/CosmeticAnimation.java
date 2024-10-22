package dev.mayaqq.estrogen.client.cosmetics;

import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import com.teamresourceful.resourcefullib.common.utils.files.GlobalStorage;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.cosmetics.model.animation.AnimationDefinition;
import io.netty.buffer.ByteBuf;

import java.io.File;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.Callable;

public class CosmeticAnimation extends DownloadedAsset<AnimationDefinition> {

    public static final Path CACHE = GlobalStorage.getCacheDirectory(Estrogen.MOD_ID).resolve("cosmetics").resolve("animations");

    private volatile AnimationDefinition result;

    public CosmeticAnimation(String url) {
        super(CACHE, url);
    }

    public static CosmeticAnimation fromLocalFile(File file) {
        if(!file.isFile()) throw new IllegalArgumentException("File is not a file");
        CosmeticAnimation animation = new CosmeticAnimation("");
        animation.load(file, "");
        return animation;
    }

    public Optional<AnimationDefinition> getResult() {
        checkOrDownload();
        return Optional.ofNullable(result);
    }

    @Override
    protected void onLoad(AnimationDefinition definition) {
        this.result = definition;
    }

    @Override
    protected Optional<AnimationDefinition> read(Callable<Reader> readerSource) {
        try(Reader reader = readerSource.call()) {
            return AnimationDefinition.CODEC.parse(JsonOps.INSTANCE, JsonParser.parseReader(reader))
                .resultOrPartial(err -> EstrogenCosmetics.LOGGER.error("Failed to read cosmetic from url [{}]: {}", url, err));
        } catch (Exception e) {
            EstrogenCosmetics.LOGGER.error("Failed to load cosmetic from url [{}]", url, e);
            return Optional.empty();
        }
    }
}
