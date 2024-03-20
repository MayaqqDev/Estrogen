package dev.mayaqq.estrogen.registry.sounds;

import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class EstrogenEffectSoundInstance extends AbstractSoundInstance {
    public EstrogenEffectSoundInstance(SoundEvent event) {
        super(event, SoundSource.MUSIC, SoundInstance.createUnseededRandom());
    }
}
