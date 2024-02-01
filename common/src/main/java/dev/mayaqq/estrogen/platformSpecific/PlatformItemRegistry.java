package dev.mayaqq.estrogen.platformSpecific;

import dev.architectury.injectables.annotations.ExpectPlatform;
import org.jetbrains.annotations.Contract;

public class PlatformItemRegistry {
    private PlatformItemRegistry() {}

    @Contract
    @ExpectPlatform
    public static void register() {
        throw new UnsupportedOperationException();
    }
}
