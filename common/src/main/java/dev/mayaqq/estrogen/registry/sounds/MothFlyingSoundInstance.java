package dev.mayaqq.estrogen.registry.sounds;

import dev.mayaqq.estrogen.registry.EstrogenSounds;
import dev.mayaqq.estrogen.registry.entities.MothEntity;
import net.minecraft.sounds.SoundSource;

public class MothFlyingSoundInstance extends MothSoundInstance {

    public MothFlyingSoundInstance(MothEntity moth) {
        super(moth, EstrogenSounds.MOTH_LOOP.get(), SoundSource.NEUTRAL);
    }
}
