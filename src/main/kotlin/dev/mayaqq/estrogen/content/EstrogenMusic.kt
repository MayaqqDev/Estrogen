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
        0,
        0,
        true).register { player, manager, biome ->
            EstrogenClientConfig.ambientMusic && player.hasEffect(Estrogen)
        }
}