package dev.mayaqq.estrogen.registry;

import net.minecraft.world.level.block.SoundType;

import static dev.mayaqq.estrogen.registry.EstrogenSounds.*;

public class EstrogenSoundTypes {
    // BREAK, STEP, PLACE, HIT, FALL
    public static final SoundType DREAM_BLOCK = new SoundType(1.0F, 1.0F,
            DREAM_BLOCK_BREAK.get(),
            DREAM_BLOCK_STEP.get(),
            DREAM_BLOCK_PLACE.get(),
            DREAM_BLOCK_HIT.get(),
            DREAM_BLOCK_FALL.get()
    );
    public static final SoundType DORMANT_DREAM_BLOCK = new SoundType(1.0F, 1.0F,
            DREAM_BLOCK_DORMANT_BREAK.get(),
            DREAM_BLOCK_DORMANT_STEP.get(),
            DREAM_BLOCK_DORMANT_PLACE.get(),
            DREAM_BLOCK_HIT.get(),
            DREAM_BLOCK_DORMANT_FALL.get()
    );
    public static final SoundType COOKIE_JAR = new SoundType(1.0F, 1.0F,
            JAR_BREAK.get(),
            JAR_STEP.get(),
            JAR_PLACE.get(),
            JAR_HIT.get(),
            JAR_FALL.get()
    );
    public static final SoundType PILL_BOX = new SoundType(1.0F, 1.0F,
            PILL_BOX_BREAK.get(),
            PILL_BOX_STEP.get(),
            PILL_BOX_PLACE.get(),
            PILL_BOX_HIT.get(),
            PILL_BOX_FALL.get()
    );
}

