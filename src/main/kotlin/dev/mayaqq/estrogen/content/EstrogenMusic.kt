package dev.mayaqq.estrogen.content

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.sounds.Music
import net.minecraft.sounds.Musics

object EstrogenMusic : Musics() {
    @JvmField
    val EstrogenAmbient: Music = Music(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(EstrogenSounds.ESTROGEN_AMBIENT), 0, 0, true)
}