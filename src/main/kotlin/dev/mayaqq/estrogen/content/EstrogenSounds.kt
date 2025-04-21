@file:Suppress("unused")

package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.Estrogen
import net.minecraft.sounds.SoundEvent
import uwu.serenity.kritter.api.Registrar
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.SoundType
import uwu.serenity.kritter.stdlib.sound
import uwu.serenity.kritter.utils.LazySoundType

object EstrogenSounds : Registrar<SoundEvent> by Estrogen..Registries.SOUND_EVENT {

    // Dash
    val DASH: SoundEvent by sound

    // Music disc
    val G03C: SoundEvent by sound

    // Ambient music
    val TRUST_YOURSELF: SoundEvent by sound
    val AMPHITRITE: SoundEvent by sound
    val AURUM_BERRY: SoundEvent by sound
    val ESTROGEN_AMBIENT: SoundEvent by sound
    val INFERRED_DREAMS: SoundEvent by sound
    val SLEEPING: SoundEvent by sound
    val INNER_SELF_REALIZATION: SoundEvent by sound

    // Dream block
    val DREAM_BLOCK_ENTER: SoundEvent by sound
    val DREAM_BLOCK_EXIT: SoundEvent by sound
    val DREAM_BLOCK_LOOP: SoundEvent by sound
    val DREAM_BLOCK_PLACE: SoundEvent by sound
    val DREAM_BLOCK_BREAK: SoundEvent by sound
    val DREAM_BLOCK_STEP: SoundEvent by sound
    val DREAM_BLOCK_HIT: SoundEvent by sound
    val DREAM_BLOCK_FALL: SoundEvent by sound

    // Dormant dream block
    val DREAM_BLOCK_DORMANT_PLACE: SoundEvent by sound
    val DREAM_BLOCK_DORMANT_BREAK: SoundEvent by sound
    val DREAM_BLOCK_DORMANT_STEP: SoundEvent by sound
    val DREAM_BLOCK_DORMANT_HIT: SoundEvent by sound
    val DREAM_BLOCK_DORMANT_FALL: SoundEvent by sound

    // Cookie jar
    val JAR_FULL: SoundEvent by sound
    val JAR_INSERT: SoundEvent by sound
    val JAR_PLACE: SoundEvent by sound
    val JAR_BREAK: SoundEvent by sound
    val JAR_STEP: SoundEvent by sound
    val JAR_HIT: SoundEvent by sound
    val JAR_FALL: SoundEvent by sound

    // Estrogen Pill Box
    val PILL_BOX_PLACE: SoundEvent by sound
    val PILL_BOX_STEP: SoundEvent by sound
    val PILL_BOX_BREAK: SoundEvent by sound
    val PILL_BOX_HIT: SoundEvent by sound
    val PILL_BOX_FALL: SoundEvent by sound

    val MOTH_DEATH: SoundEvent by sound
    val MOTH_HURT: SoundEvent by sound
    val MOTH_LOOP: SoundEvent by sound
    val MOTH_FUZZ_UP: SoundEvent by sound
}

object EstrogenSoundTypes {

    val DREAM_BLOCK: SoundType = LazySoundType(1.0f, 1.0f,
        breakSound = EstrogenSounds::DREAM_BLOCK_BREAK,
        stepSound = EstrogenSounds::DREAM_BLOCK_STEP,
        placeSound = EstrogenSounds::DREAM_BLOCK_PLACE,
        hitSound = EstrogenSounds::DREAM_BLOCK_HIT,
        fallSound = EstrogenSounds::DREAM_BLOCK_FALL
    )

    val DORMANT_DREAM_BLOCK: SoundType = LazySoundType(1.0f, 1.0f,
        breakSound = EstrogenSounds::DREAM_BLOCK_DORMANT_BREAK,
        stepSound = EstrogenSounds::DREAM_BLOCK_DORMANT_STEP,
        placeSound = EstrogenSounds::DREAM_BLOCK_DORMANT_PLACE,
        hitSound = EstrogenSounds::DREAM_BLOCK_DORMANT_HIT,
        fallSound = EstrogenSounds::DREAM_BLOCK_DORMANT_FALL
    )

    val COOKIE_JAR: SoundType = LazySoundType(1.0f, 1.0f,
        breakSound = EstrogenSounds::JAR_BREAK,
        stepSound = EstrogenSounds::JAR_STEP,
        placeSound = EstrogenSounds::JAR_PLACE,
        hitSound = EstrogenSounds::JAR_HIT,
        fallSound = EstrogenSounds::JAR_FALL
    )

    val PILL_BOX: SoundType = LazySoundType(1.0f, 1.0f,
        breakSound = EstrogenSounds::PILL_BOX_BREAK,
        stepSound = EstrogenSounds::PILL_BOX_STEP,
        placeSound = EstrogenSounds::PILL_BOX_PLACE,
        hitSound = EstrogenSounds::PILL_BOX_HIT,
        fallSound = EstrogenSounds::PILL_BOX_FALL
    )
}