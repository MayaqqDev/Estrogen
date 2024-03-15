package dev.mayaqq.estrogen.registry.sounds;

import dev.mayaqq.estrogen.registry.EstrogenSounds;
import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;

public class EstrogenEffectSoundInstance extends AbstractSoundInstance {
    public EstrogenEffectSoundInstance() {
        super(EstrogenSounds.TRUST_YOURSELF.get(), SoundSource.MUSIC, SoundInstance.createUnseededRandom());
    }
}
