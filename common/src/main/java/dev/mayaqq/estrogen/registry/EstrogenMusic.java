package dev.mayaqq.estrogen.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;

public class EstrogenMusic extends Musics {
    public static final Music ESTROGEN_AMBIENT = new Music(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(EstrogenSounds.ESTROGEN_AMBIENT.get()), 0, 0, true);
}