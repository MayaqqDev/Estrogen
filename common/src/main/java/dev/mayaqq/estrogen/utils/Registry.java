package dev.mayaqq.estrogen.utils;

import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.resources.ResourceLocation;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

/**
 * Simple registry class for serialization purposes
 * @param <T>
 */
public final class Registry<T> {

    private final HashBiMap<ResourceLocation, T> backingMap = HashBiMap.create();
    private final @Nullable T defaultValue;
    private Codec<T> codec;

    public Registry(@Nullable T defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Registry() {
        this.defaultValue = null;
    }

    public T register(ResourceLocation key, T value) {
        Objects.requireNonNull(key, "Key cannot be null");
        Objects.requireNonNull(value, "Value cannot be null");
        if(backingMap.containsKey(key)) {
            throw new IllegalStateException("Key already present: " + key);
        }
        backingMap.put(key, value);
        return value;
    }

    public T get(ResourceLocation key) {
        return backingMap.getOrDefault(key, defaultValue);
    }

    public ResourceLocation getKey(T value) {
        return backingMap.inverse().get(value);
    }

    public Codec<T> codec() {
        // Lazily initialize the codec if its used
        return (codec == null) ? codec = ResourceLocation.CODEC.flatXmap(
            location -> failIfNull(this.get(location), () -> "Registry entry not present: %s".formatted(location)),
            object -> failIfNull(this.getKey(object), () -> "No key present for object: %s".formatted(object))
        ) : codec;
    }

    private static <R> DataResult<R> failIfNull(R object, Supplier<String> errorMessage) {
        return object != null ? DataResult.success(object) : DataResult.error(errorMessage);
    }
}
