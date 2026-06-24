package dev.mayaqq.estrogen.client.content.sounds

import dev.mayaqq.estrogen.content.EstrogenSounds
import dev.mayaqq.estrogen.content.blocks.DreamBlock
import net.minecraft.client.player.LocalPlayer
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance
import net.minecraft.client.resources.sounds.SoundInstance
import net.minecraft.sounds.SoundSource

class DreamBlockSoundInstance(val player: LocalPlayer) : AbstractTickableSoundInstance(
    EstrogenSounds.DREAM_BLOCK_LOOP,
    SoundSource.BLOCKS,
    SoundInstance.createUnseededRandom()
) {
    init {
        this.attenuation = SoundInstance.Attenuation.NONE
        this.looping = true
        this.delay = 0
        this.volume = 1.0F
    }

    var f = 0.0F

    override fun tick() {
        if (f < 50) f++
        if (DreamBlock.isInDreamBlock(player)) {
            this.pitch = 0.5F + f * 0.01F
            this.volume = 1.0F + f * 0.01F
        } else {
            f = 0.0F
            this.stop()
        }
    }
}