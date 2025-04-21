package dev.mayaqq.estrogen.client.content.sounds

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance
import net.minecraft.client.resources.sounds.SoundInstance
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundSource
import net.minecraft.util.Mth

abstract class MothSoundInstance(protected val moth: MothEntity, soundEvent: SoundEvent, source: SoundSource) :
    AbstractTickableSoundInstance(soundEvent, source, SoundInstance.createUnseededRandom()) {

    init {
        this.moth = moth
        this.x = (moth.x as Float).toDouble()
        this.y = (moth.y as Float).toDouble()
        this.z = (moth.z as Float).toDouble()
        this.looping = true
        this.delay = 0
        this.volume = 0.0f
    }

    override fun tick() {
        if (this.moth.isRemoved()) {
            this.stop()
            return
        }
        this.x = (this.moth.x as Float).toDouble()
        this.y = (this.moth.y as Float).toDouble()
        this.z = (this.moth.z as Float).toDouble()
        val f = this.moth.getDeltaMovement().horizontalDistance() as Float
        if (f >= 0.01f) {
            this.pitch = Mth.lerp(
                Mth.clamp(f, this.minPitch, this.maxPitch),
                this.minPitch,
                this.maxPitch
            )
            this.volume = Mth.lerp(Mth.clamp(f, 0.0f, 0.5f), 0.0f, 1.2f)
        } else {
            this.pitch = 0.0f
            this.volume = 0.0f
        }
    }

    private val minPitch: Float
        get() {
            if (this.moth.isBaby()) {
                return 1.1f
            }
            return 0.7f
        }

    private val maxPitch: Float
        get() {
            if (this.moth.isBaby()) {
                return 1.5f
            }
            return 1.1f
        }

    override fun canStartSilent(): Boolean {
        return true
    }

    override fun canPlaySound(): Boolean {
        return !this.moth.isSilent()
    }

    companion object {
        private const val VOLUME_MIN = 0.0f
        private const val VOLUME_MAX = 1.2f
        private const val PITCH_MIN = 0.0f
    }
}