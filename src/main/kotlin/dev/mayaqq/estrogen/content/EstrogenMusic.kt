package dev.mayaqq.estrogen.content

import dev.mayaqq.cynosure.music.register
import dev.mayaqq.estrogen.config.EstrogenClientConfig
import dev.mayaqq.estrogen.content.EstrogenEffects.Estrogen
import dev.mayaqq.estrogen.utils.holder
import invoke.kitty.kritter.registry.api.entry.holder
import net.minecraft.sounds.Music
import net.minecraft.sounds.Musics

object EstrogenMusic : Musics() {
    @JvmField
    val EstrogenAmbient: Music = Music(
        EstrogenSounds.ESTROGEN_AMBIENT.holder,
        EstrogenClientConfig.Music.minDelayBetweenSongs,
        EstrogenClientConfig.Music.maxDelayBetweenSongs,
        EstrogenClientConfig.Music.replacesCurrentMusic
    ).register { player, manager, biome ->
            EstrogenClientConfig.Music.enabled && player.hasEffect(Estrogen.holder)
        }
}