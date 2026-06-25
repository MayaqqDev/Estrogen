@file:Suppress("unused")

package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.MOD_ID
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.misc.soundEvent
import invoke.kitty.kritter.utils.ExperimentalRegistryApi
import invoke.kitty.kritter.utils.sounds.LazySoundType
import net.minecraft.core.registries.Registries
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.level.block.SoundType

@OptIn(ExperimentalRegistryApi::class)
object EstrogenSounds : Registrar<SoundEvent> by Registrar(MOD_ID, Registries.SOUND_EVENT) {

    // Dash
    val DASH: SoundEvent by soundEvent

    // Music disc
    val G03C: SoundEvent by soundEvent

    // Ambient music
    val TRUST_YOURSELF: SoundEvent by soundEvent
    val AMPHITRITE: SoundEvent by soundEvent
    val AURUM_BERRY: SoundEvent by soundEvent
    val ESTROGEN_AMBIENT: SoundEvent by soundEvent
    val INFERRED_DREAMS: SoundEvent by soundEvent
    val SLEEPING: SoundEvent by soundEvent
    val INNER_SELF_REALIZATION: SoundEvent by soundEvent

    // Dream block
    val DREAM_BLOCK_ENTER: SoundEvent by soundEvent
    val DREAM_BLOCK_EXIT: SoundEvent by soundEvent
    val DREAM_BLOCK_LOOP: SoundEvent by soundEvent
    val DREAM_BLOCK_PLACE: SoundEvent by soundEvent
    val DREAM_BLOCK_BREAK: SoundEvent by soundEvent
    val DREAM_BLOCK_STEP: SoundEvent by soundEvent
    val DREAM_BLOCK_HIT: SoundEvent by soundEvent
    val DREAM_BLOCK_FALL: SoundEvent by soundEvent

    // Dormant dream block
    val DREAM_BLOCK_DORMANT_PLACE: SoundEvent by soundEvent
    val DREAM_BLOCK_DORMANT_BREAK: SoundEvent by soundEvent
    val DREAM_BLOCK_DORMANT_STEP: SoundEvent by soundEvent
    val DREAM_BLOCK_DORMANT_HIT: SoundEvent by soundEvent
    val DREAM_BLOCK_DORMANT_FALL: SoundEvent by soundEvent

    // Cookie jar
    val JAR_FULL: SoundEvent by soundEvent
    val JAR_INSERT: SoundEvent by soundEvent
    val JAR_PLACE: SoundEvent by soundEvent
    val JAR_BREAK: SoundEvent by soundEvent
    val JAR_STEP: SoundEvent by soundEvent
    val JAR_HIT: SoundEvent by soundEvent
    val JAR_FALL: SoundEvent by soundEvent

    // Estrogen Pill Box
    val PILL_BOX_PLACE: SoundEvent by soundEvent
    val PILL_BOX_STEP: SoundEvent by soundEvent
    val PILL_BOX_BREAK: SoundEvent by soundEvent
    val PILL_BOX_HIT: SoundEvent by soundEvent
    val PILL_BOX_FALL: SoundEvent by soundEvent

    val MOTH_DEATH: SoundEvent by soundEvent
    val MOTH_HURT: SoundEvent by soundEvent
    val MOTH_LOOP: SoundEvent by soundEvent
    val MOTH_FUZZ_UP: SoundEvent by soundEvent
}

object EstrogenSoundTypes {

    val DREAM_BLOCK_ACTIVE: SoundType = LazySoundType(1.0f, 1.0f,
        breakSound = EstrogenSounds::DREAM_BLOCK_BREAK,
        stepSound = EstrogenSounds::DREAM_BLOCK_STEP,
        placeSound = EstrogenSounds::DREAM_BLOCK_PLACE,
        hitSound = EstrogenSounds::DREAM_BLOCK_HIT,
        fallSound = EstrogenSounds::DREAM_BLOCK_FALL
    )

    val DREAM_BLOCK_DORMANT: SoundType = LazySoundType(1.0f, 1.0f,
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