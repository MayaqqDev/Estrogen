package dev.mayaqq.estrogen.registry.common;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenSounds {
    public static final SoundEvent DASH = new SoundEvent(id("dash"));
    public static void register() {
        Registry.register(Registry.SOUND_EVENT, id("dash"), DASH);
    }
}
