package dev.mayaqq.estrogen.content

import dev.mayaqq.cynosure.text.Text
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.utils.holder
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.api.builder.entry
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.JukeboxSong

object EstrogenRecordSongs : Registrar<JukeboxSong> by Registrar(MOD_ID, Registries.JUKEBOX_SONG) {
    val G03C = entry("g03c", {
        JukeboxSong(EstrogenSounds.G03C.holder(), Text.translatable("jukebox_song.estrogen.g03c"), 303F, 3)
    }) {}
}