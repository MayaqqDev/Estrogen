package dev.mayaqq.estrogen.client.cosmetics;

import com.teamresourceful.resourcefullib.common.utils.files.GlobalStorage;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.cosmetics.model.animation.AnimationDefinition;
import org.joml.Vector3f;

import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class CosmeticAnimation {

    public static final Path CACHE = GlobalStorage.getCacheDirectory(Estrogen.MOD_ID).resolve("cosmetics").resolve("animations");
    private static final Vector3f DEFAULT_SIZE = new Vector3f(16f, 16f, 16f);

    private CompletableFuture<Void> animationFuture;
    private AnimationDefinition cache;

    public CosmeticAnimation(String url) {

    }


    private void checkOrDownload() {

    }
}
