@file:Suppress("unused")

package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.MOD_ID
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.api.entry.RegistryEntry
import invoke.kitty.kritter.registry.misc.soundEvent
import invoke.kitty.kritter.utils.ExperimentalRegistryApi
import invoke.kitty.kritter.utils.sounds.LazySoundType
import net.minecraft.core.registries.Registries
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.level.block.SoundType

@OptIn(ExperimentalRegistryApi::class)
object EstrogenSounds : Registrar<SoundEvent> by Registrar(MOD_ID, Registries.SOUND_EVENT) {

    // Dash
    val DASH: RegistryEntry<SoundEvent> = soundEvent("dash")

    // Music disc
    val G03C: RegistryEntry<SoundEvent> = soundEvent("g03c")

    // Ambient music
    val TRUST_YOURSELF: RegistryEntry<SoundEvent> = soundEvent("trust_yourself")
    val AMPHITRITE: RegistryEntry<SoundEvent> = soundEvent("amphitrite")
    val AURUM_BERRY: RegistryEntry<SoundEvent> = soundEvent("aurum_berry")
    val ESTROGEN_AMBIENT: RegistryEntry<SoundEvent> = soundEvent("estrogen_ambient")
    val INFERRED_DREAMS: RegistryEntry<SoundEvent> = soundEvent("inferred_dream")
    val SLEEPING: RegistryEntry<SoundEvent> = soundEvent("sleeping")
    val INNER_SELF_REALIZATION: RegistryEntry<SoundEvent> = soundEvent("inner_self_realization")

    // Dream block
    val DREAM_BLOCK_ENTER: RegistryEntry<SoundEvent> = soundEvent("dream_block_enter")
    val DREAM_BLOCK_EXIT: RegistryEntry<SoundEvent> = soundEvent("dream_block_exit")
    val DREAM_BLOCK_LOOP: RegistryEntry<SoundEvent> = soundEvent("dream_block_loop")
    val DREAM_BLOCK_PLACE: RegistryEntry<SoundEvent> = soundEvent("dream_block_place")
    val DREAM_BLOCK_BREAK: RegistryEntry<SoundEvent> = soundEvent("dream_block_break")
    val DREAM_BLOCK_STEP: RegistryEntry<SoundEvent> = soundEvent("dream_block_step")
    val DREAM_BLOCK_HIT: RegistryEntry<SoundEvent> = soundEvent("dream_block_hit")
    val DREAM_BLOCK_FALL: RegistryEntry<SoundEvent> = soundEvent("dream_block_fall")

    // Dormant dream block
    val DREAM_BLOCK_DORMANT_PLACE: RegistryEntry<SoundEvent> = soundEvent("dream_block_dormant_place")
    val DREAM_BLOCK_DORMANT_BREAK: RegistryEntry<SoundEvent> = soundEvent("dream_block_dormant_break")
    val DREAM_BLOCK_DORMANT_STEP: RegistryEntry<SoundEvent> = soundEvent("dream_block_dormant_step")
    val DREAM_BLOCK_DORMANT_HIT: RegistryEntry<SoundEvent> = soundEvent("dream_block_dormant_hit")
    val DREAM_BLOCK_DORMANT_FALL: RegistryEntry<SoundEvent> = soundEvent("dream_block_dormant_fall")

    // Cookie jar
    val JAR_FULL: RegistryEntry<SoundEvent> = soundEvent("jar_full")
    val JAR_INSERT: RegistryEntry<SoundEvent> = soundEvent("jar_insert")
    val JAR_PLACE: RegistryEntry<SoundEvent> = soundEvent("jar_place")
    val JAR_BREAK: RegistryEntry<SoundEvent> = soundEvent("jar_break")
    val JAR_STEP: RegistryEntry<SoundEvent> = soundEvent("jar_step")
    val JAR_HIT: RegistryEntry<SoundEvent> = soundEvent("jar_hit")
    val JAR_FALL: RegistryEntry<SoundEvent> = soundEvent("jar_fall")

    // Estrogen Pill Box
    val PILL_BOX_PLACE: RegistryEntry<SoundEvent> = soundEvent("pill_box_place")
    val PILL_BOX_STEP: RegistryEntry<SoundEvent> = soundEvent("pill_box_step")
    val PILL_BOX_BREAK: RegistryEntry<SoundEvent> = soundEvent("pill_box_break")
    val PILL_BOX_HIT: RegistryEntry<SoundEvent> = soundEvent("pill_box_hit")
    val PILL_BOX_FALL: RegistryEntry<SoundEvent> = soundEvent("pill_box_fall")

    val MOTH_DEATH: RegistryEntry<SoundEvent> = soundEvent("moth_death")
    val MOTH_HURT: RegistryEntry<SoundEvent> = soundEvent("moth_hurt")
    val MOTH_LOOP: RegistryEntry<SoundEvent> = soundEvent("moth_loop")
    val MOTH_FUZZ_UP: RegistryEntry<SoundEvent> = soundEvent("moth_fuzz_up")
}

object EstrogenSoundTypes {

    val DREAM_BLOCK_ACTIVE: SoundType = LazySoundType(1.0f, 1.0f,
        breakSound = EstrogenSounds.DREAM_BLOCK_BREAK,
        stepSound = EstrogenSounds.DREAM_BLOCK_STEP,
        placeSound = EstrogenSounds.DREAM_BLOCK_PLACE,
        hitSound = EstrogenSounds.DREAM_BLOCK_HIT,
        fallSound = EstrogenSounds.DREAM_BLOCK_FALL
    )

    val DREAM_BLOCK_DORMANT: SoundType = LazySoundType(1.0f, 1.0f,
        breakSound = EstrogenSounds.DREAM_BLOCK_BREAK,
        stepSound = EstrogenSounds.DREAM_BLOCK_STEP,
        placeSound = EstrogenSounds.DREAM_BLOCK_PLACE,
        hitSound = EstrogenSounds.DREAM_BLOCK_DORMANT_HIT,
        fallSound = EstrogenSounds.DREAM_BLOCK_DORMANT_FALL
    )

    val COOKIE_JAR: SoundType = LazySoundType(1.0f, 1.0f,
        breakSound = EstrogenSounds.JAR_BREAK,
        stepSound = EstrogenSounds.JAR_STEP,
        placeSound = EstrogenSounds.JAR_PLACE,
        hitSound = EstrogenSounds.JAR_HIT,
        fallSound = EstrogenSounds.JAR_FALL
    )

    val PILL_BOX: SoundType = LazySoundType(1.0f, 1.0f,
        breakSound = EstrogenSounds.PILL_BOX_BREAK,
        stepSound = EstrogenSounds.PILL_BOX_STEP,
        placeSound = EstrogenSounds.PILL_BOX_PLACE,
        hitSound = EstrogenSounds.PILL_BOX_HIT,
        fallSound = EstrogenSounds.PILL_BOX_FALL
    )
}