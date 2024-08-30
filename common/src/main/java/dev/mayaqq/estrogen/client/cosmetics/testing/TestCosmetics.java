package dev.mayaqq.estrogen.client.cosmetics.testing;

import dev.mayaqq.estrogen.client.cosmetics.Cosmetic;
import dev.mayaqq.estrogen.client.cosmetics.CosmeticModel;
import dev.mayaqq.estrogen.client.cosmetics.CosmeticTexture;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class TestCosmetics {
    private static final File TEXTURE_FILE = FabricLoader.getInstance().getGameDir().resolve("test_cosmetic.png").toFile();
    private static final File MODEL_FILE = FabricLoader.getInstance().getGameDir().resolve("test_cosmetic.json").toFile();
    public static final Cosmetic TEST_COSMETIC =
            new Cosmetic("test", "test", CosmeticTexture.fromLocalFile("test", TEXTURE_FILE), CosmeticModel.fromLocalFile(MODEL_FILE));
}
