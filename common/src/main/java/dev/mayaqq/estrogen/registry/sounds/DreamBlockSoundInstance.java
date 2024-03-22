package dev.mayaqq.estrogen.registry.sounds;

import dev.mayaqq.estrogen.registry.EstrogenSounds;
import dev.mayaqq.estrogen.registry.blocks.DreamBlock;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;

public class DreamBlockSoundInstance extends AbstractTickableSoundInstance {

    private final LocalPlayer player;

    public DreamBlockSoundInstance(LocalPlayer player) {
        super(EstrogenSounds.DREAM_BLOCK_LOOP.get(), SoundSource.BLOCKS, SoundInstance.createUnseededRandom());
        this.attenuation = Attenuation.NONE;
        this.looping = true;
        this.delay = 0;
        this.volume = 1.0F;
        this.player = player;
    }

    private float f = 0.0F;
    @Override
    public void tick() {
        f++;
        if (DreamBlock.isInDreamBlock(player)) {
            this.pitch = 0.5F + f * 0.01F;
            this.volume = 1.0F + f * 0.1F;
        } else {
            f = 0.0F;
            this.stop();
        }
    }
}
