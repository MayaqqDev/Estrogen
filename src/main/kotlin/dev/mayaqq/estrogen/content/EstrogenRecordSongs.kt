package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.id
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.JukeboxSong

object EstrogenRecordSongs {
    val G03C: ResourceKey<JukeboxSong> = ResourceKey.create(Registries.JUKEBOX_SONG, id("g03c"))
}