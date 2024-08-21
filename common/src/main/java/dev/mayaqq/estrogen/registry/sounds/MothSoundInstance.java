package dev.mayaqq.estrogen.registry.sounds;

import dev.mayaqq.estrogen.registry.entities.MothEntity;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;

public abstract class MothSoundInstance extends AbstractTickableSoundInstance {
    private static final float VOLUME_MIN = 0.0f;
    private static final float VOLUME_MAX = 1.2f;
    private static final float PITCH_MIN = 0.0f;
    protected final MothEntity moth;

    public MothSoundInstance(MothEntity moth, SoundEvent soundEvent, SoundSource source) {
        super(soundEvent, source, SoundInstance.createUnseededRandom());
        this.moth = moth;
        this.x = (float)moth.getX();
        this.y = (float)moth.getY();
        this.z = (float)moth.getZ();
        this.looping = true;
        this.delay = 0;
        this.volume = 0.0f;
    }

    @Override
    public void tick() {
        if (this.moth.isRemoved()) {
            this.stop();
            return;
        }
        this.x = (float)this.moth.getX();
        this.y = (float)this.moth.getY();
        this.z = (float)this.moth.getZ();
        float f = (float)this.moth.getDeltaMovement().horizontalDistance();
        if (f >= 0.01f) {
            this.pitch = Mth.lerp(Mth.clamp(f, this.getMinPitch(), this.getMaxPitch()), this.getMinPitch(), this.getMaxPitch());
            this.volume = Mth.lerp(Mth.clamp(f, 0.0f, 0.5f), 0.0f, 1.2f);
        } else {
            this.pitch = 0.0f;
            this.volume = 0.0f;
        }
    }

    private float getMinPitch() {
        if (this.moth.isBaby()) {
            return 1.1f;
        }
        return 0.7f;
    }

    private float getMaxPitch() {
        if (this.moth.isBaby()) {
            return 1.5f;
        }
        return 1.1f;
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }

    @Override
    public boolean canPlaySound() {
        return !this.moth.isSilent();
    }
}
