package dev.mayaqq.estrogen.content

import dev.mayaqq.cynosure.music.register
import dev.mayaqq.estrogen.config.EstrogenClientConfig
import dev.mayaqq.estrogen.content.EstrogenEffects.Estrogen
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.sounds.Music
import net.minecraft.sounds.Musics

object EstrogenMusic : Musics() {
    @JvmField
    val EstrogenAmbient: Music = Music(
        BuiltInRegistries.SOUND_EVENT.wrapAsHolder(EstrogenSounds.ESTROGEN_AMBIENT),
        EstrogenClientConfig.Music.minDelayBetweenSongs,
        EstrogenClientConfig.Music.maxDelayBetweenSongs,
        true).register { player, manager, biome ->
            EstrogenClientConfig.Music.enabled && player.hasEffect(Estrogen)
        }
}