package dev.mayaqq.estrogen.client.cosmetics;

import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import com.teamresourceful.resourcefullib.common.utils.files.GlobalStorage;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.cosmetics.model.BakedCosmeticModel;
import dev.mayaqq.estrogen.client.cosmetics.model.CosmeticModelBakery;
import dev.mayaqq.estrogen.client.cosmetics.model.PreparedModel;
import net.minecraft.Optionull;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.io.*;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.Callable;

public class CosmeticModel extends DownloadedAsset<PreparedModel> {

    public static final Path CACHE = GlobalStorage.getCacheDirectory(Estrogen.MOD_ID).resolve("cosmetics").resolve("models");
    private static final Vector3f DEFAULT_SIZE = new Vector3f(16f, 16f, 16f);

    private volatile BakedCosmeticModel result;

    private Vector3f modelSize = DEFAULT_SIZE;

    public static CosmeticModel fromLocalFile(File file) {
        if(!file.isFile()) EstrogenCosmetics.LOGGER.error("Invalid file");
        CosmeticModel model = new CosmeticModel("");
        model.load(file, "");
        return model;
    }

    public CosmeticModel(String url) {
        super(CACHE, url);
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


    @Override
    protected void onLoad(PreparedModel object) {
        try {
            this.result = CosmeticModelBakery.bake(object);
        } catch (Exception e) {
            EstrogenCosmetics.LOGGER.error("Failed to bake cosmetic model: {}", url, e);
        }
    }

    protected Optional<PreparedModel> read(Callable<Reader> supplier) {
        try(Reader reader = supplier.call()) {
            return PreparedModel.CODEC.parse(JsonOps.INSTANCE, JsonParser.parseReader(reader))
                .resultOrPartial(EstrogenCosmetics.LOGGER::error);
        } catch (Exception ex) {
            EstrogenCosmetics.LOGGER.error("Failed to load cosmetic model: {}", url, ex);
            return Optional.empty();
        }
    }


}
