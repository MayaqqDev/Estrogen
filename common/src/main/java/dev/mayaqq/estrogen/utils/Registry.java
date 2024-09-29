package dev.mayaqq.estrogen.utils;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.resources.ResourceLocation;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

public final class Registry<T> {

    private final ConcurrentMap<ResourceLocation, T> backingMap = new ConcurrentHashMap<>();
    private final @Nullable T defaultValue;

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
        for(Map.Entry<ResourceLocation, T> entry : backingMap.entrySet()) {
            if (entry.getValue() == value) return entry.getKey();
        }
        return null;
    }

    public Codec<T> createCodec() {
        return ResourceLocation.CODEC.flatXmap(
            location -> failIfNull(this.get(location), () -> "Registry entry not present: %s".formatted(location)),
            object -> failIfNull(this.getKey(object), () -> "No key present for object: %s".formatted(object))
        );
    }

    private static <R> DataResult<R> failIfNull(R object, Supplier<String> errorMessage) {
        return object != null ? DataResult.success(object) : DataResult.error(errorMessage);
    }
}
