package dev.mayaqq.estrogen.registry;

import net.minecraft.world.level.block.SoundType;

public class EstrogenSoundTypes {
    // float volume, float pitch, SoundEvent breakSound, SoundEvent stepSound, SoundEvent placeSound, SoundEvent hitSound, SoundEvent fallSound
    public static final SoundType DREAM_BLOCK = new SoundType(1.0F, 1.0F, EstrogenSounds.DREAM_BLOCK_PLACE.get(), EstrogenSounds.DREAM_BLOCK_PLACE.get(), EstrogenSounds.DREAM_BLOCK_PLACE.get(), EstrogenSounds.DREAM_BLOCK_PLACE.get(), EstrogenSounds.DREAM_BLOCK_PLACE.get());
    public static final SoundType DORMANT_DREAM_BLOCK = new SoundType(1.0F, 1.0F, EstrogenSounds.DREAM_BLOCK_DORMANT_BREAK.get(), EstrogenSounds.DREAM_BLOCK_DORMANT_STEP.get(), EstrogenSounds.DREAM_BLOCK_DORMANT_PLACE.get(), EstrogenSounds.DREAM_BLOCK_DORMANT_BREAK.get(), EstrogenSounds.DREAM_BLOCK_DORMANT_FALL.get());

}
