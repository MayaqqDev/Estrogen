package dev.mayaqq.estrogen.client.content.sounds

import dev.mayaqq.estrogen.content.EstrogenSounds
import dev.mayaqq.estrogen.content.entities.MothEntity
import net.minecraft.sounds.SoundSource

class MothFlyingSoundInstance(moth: MothEntity) : MothSoundInstance(moth, EstrogenSounds.MOTH_LOOP.value!!, SoundSource.NEUTRAL)