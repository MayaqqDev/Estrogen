package dev.mayaqq.estrogen.registry.common;

import com.simibubi.create.AllSoundEvents;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.sounds.SoundSource;

public class EstrogenSounds {
    public static final AllSoundEvents.SoundEntry DASH = AllSoundEvents.create(Estrogen.id("dash")).category(SoundSource.PLAYERS).build();
    public static void register() {}
}
