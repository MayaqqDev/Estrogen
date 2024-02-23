package dev.mayaqq.estrogen.platformSpecific;

import dev.architectury.injectables.annotations.ExpectPlatform;
import org.jetbrains.annotations.Contract;

import java.util.Optional;

public record Mod(String modid, String version, String name, String description) {
    @Contract
    @ExpectPlatform
    public static Optional<Mod> getOptionalMod(String modid) {
        throw new AssertionError();
    }
}
