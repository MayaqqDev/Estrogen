package dev.mayaqq.estrogen.utils.resources

import net.minecraft.resources.FileToIdConverter
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager

fun ResourceManager.listResourceIds(path: String, prefix: String, suffix: String): Pair<Collection<ResourceLocation>, FileToIdConverter> {
    val fileToIdConverter = FileToIdConverter(prefix, suffix)
    return listResources(path, fun(id) = id.path.endsWith(suffix))
        .keys.map(fileToIdConverter::fileToId) to fileToIdConverter
}