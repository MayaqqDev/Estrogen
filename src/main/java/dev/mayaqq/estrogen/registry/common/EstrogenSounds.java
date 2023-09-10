package dev.mayaqq.estrogen.registry.common;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenSounds {
    public static final SoundEvent DASH = Registry.register(Registries.SOUND_EVENT, id("dash"), SoundEvent.createVariableRangeEvent((id("dash"))));
    public static void register() {}
}
