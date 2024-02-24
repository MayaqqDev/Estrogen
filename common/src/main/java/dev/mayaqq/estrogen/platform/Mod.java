package dev.mayaqq.estrogen.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

import java.util.Optional;

public record Mod(String modid, String version, String name, String description) {
    @ExpectPlatform
    public static Optional<Mod> getOptionalMod(String modid) {
        throw new AssertionError();
    }
}
