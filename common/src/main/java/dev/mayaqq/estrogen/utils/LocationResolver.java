package dev.mayaqq.estrogen.utils;

import com.google.common.collect.ImmutableList;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.List;

public record LocationResolver(List<ResourceLocation> locations, FileToIdConverter converter) {
    public static LocationResolver load(ResourceManager manager, String directory, String prefix, String suffix) {
        ImmutableList.Builder<ResourceLocation> loaded = ImmutableList.builder();
        FileToIdConverter converter = new FileToIdConverter(prefix, suffix);
        manager.listResources(directory, id -> id.getPath().endsWith(suffix))
            .forEach((id, $) -> loaded.add(converter.fileToId(id)));

        return new LocationResolver(loaded.build(), converter);
    }
}
