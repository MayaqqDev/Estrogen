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
    val DASH: RegistryEntry<SoundEvent> = soundEvent.doBuild("dash")

    // Music disc
    val G03C: RegistryEntry<SoundEvent> = soundEvent.doBuild("g03c")

    // Ambient music
    val TRUST_YOURSELF: RegistryEntry<SoundEvent> = soundEvent.doBuild("trust_yourself")
    val AMPHITRITE: RegistryEntry<SoundEvent> = soundEvent.doBuild("amphitrite")
    val AURUM_BERRY: RegistryEntry<SoundEvent> = soundEvent.doBuild("aurum_berry")
    val ESTROGEN_AMBIENT: RegistryEntry<SoundEvent> = soundEvent.doBuild("estrogen_ambient")
    val INFERRED_DREAMS: RegistryEntry<SoundEvent> = soundEvent.doBuild("inferred_dream")
    val SLEEPING: RegistryEntry<SoundEvent> = soundEvent.doBuild("sleeping")
    val INNER_SELF_REALIZATION: RegistryEntry<SoundEvent> = soundEvent.doBuild("inner_self_realization")

    // Dream block
    val DREAM_BLOCK_ENTER: RegistryEntry<SoundEvent> = soundEvent.doBuild("dream_block_enter")
    val DREAM_BLOCK_EXIT: RegistryEntry<SoundEvent> = soundEvent.doBuild("dream_block_exit")
    val DREAM_BLOCK_LOOP: RegistryEntry<SoundEvent> = soundEvent.doBuild("dream_block_loop")
    val DREAM_BLOCK_PLACE: RegistryEntry<SoundEvent> = soundEvent.doBuild("dream_block_place")
    val DREAM_BLOCK_BREAK: RegistryEntry<SoundEvent> = soundEvent.doBuild("dream_block_break")
    val DREAM_BLOCK_STEP: RegistryEntry<SoundEvent> = soundEvent.doBuild("dream_block_step")
    val DREAM_BLOCK_HIT: RegistryEntry<SoundEvent> = soundEvent.doBuild("dream_block_hit")
    val DREAM_BLOCK_FALL: RegistryEntry<SoundEvent> = soundEvent.doBuild("dream_block_fall")

    // Dormant dream block
    val DREAM_BLOCK_DORMANT_PLACE: RegistryEntry<SoundEvent> = soundEvent.doBuild("dream_block_dormant_place")
    val DREAM_BLOCK_DORMANT_BREAK: RegistryEntry<SoundEvent> = soundEvent.doBuild("dream_block_dormant_break")
    val DREAM_BLOCK_DORMANT_STEP: RegistryEntry<SoundEvent> = soundEvent.doBuild("dream_block_dormant_step")
    val DREAM_BLOCK_DORMANT_HIT: RegistryEntry<SoundEvent> = soundEvent.doBuild("dream_block_dormant_hit")
    val DREAM_BLOCK_DORMANT_FALL: RegistryEntry<SoundEvent> = soundEvent.doBuild("dream_block_dormant_fall")

    // Cookie jar
    val JAR_FULL: RegistryEntry<SoundEvent> = soundEvent.doBuild("jar_full")
    val JAR_INSERT: RegistryEntry<SoundEvent> = soundEvent.doBuild("jar_insert")
    val JAR_PLACE: RegistryEntry<SoundEvent> = soundEvent.doBuild("jar_place")
    val JAR_BREAK: RegistryEntry<SoundEvent> = soundEvent.doBuild("jar_break")
    val JAR_STEP: RegistryEntry<SoundEvent> = soundEvent.doBuild("jar_step")
    val JAR_HIT: RegistryEntry<SoundEvent> = soundEvent.doBuild("jar_hit")
    val JAR_FALL: RegistryEntry<SoundEvent> = soundEvent.doBuild("jar_fall")

    // Estrogen Pill Box
    val PILL_BOX_PLACE: RegistryEntry<SoundEvent> = soundEvent.doBuild("pill_box_place")
    val PILL_BOX_STEP: RegistryEntry<SoundEvent> = soundEvent.doBuild("pill_box_step")
    val PILL_BOX_BREAK: RegistryEntry<SoundEvent> = soundEvent.doBuild("pill_box_break")
    val PILL_BOX_HIT: RegistryEntry<SoundEvent> = soundEvent.doBuild("pill_box_hit")
    val PILL_BOX_FALL: RegistryEntry<SoundEvent> = soundEvent.doBuild("pill_box_fall")

    val MOTH_DEATH: RegistryEntry<SoundEvent> = soundEvent.doBuild("moth_death")
    val MOTH_HURT: RegistryEntry<SoundEvent> = soundEvent.doBuild("moth_hurt")
    val MOTH_LOOP: RegistryEntry<SoundEvent> = soundEvent.doBuild("moth_loop")
    val MOTH_FUZZ_UP: RegistryEntry<SoundEvent> = soundEvent.doBuild("moth_fuzz_up")
}

object EstrogenSoundTypes {

    val DREAM_BLOCK_ACTIVE: SoundType = LazySoundType(1.0f, 1.0f,
        breakSound = { EstrogenSounds.DREAM_BLOCK_BREAK.value!! },
        stepSound = { EstrogenSounds.DREAM_BLOCK_STEP.value!! },
        placeSound = { EstrogenSounds.DREAM_BLOCK_PLACE.value!! },
        hitSound = { EstrogenSounds.DREAM_BLOCK_HIT.value!! },
        fallSound = { EstrogenSounds.DREAM_BLOCK_FALL.value!! }
    )

    val DREAM_BLOCK_DORMANT: SoundType = LazySoundType(1.0f, 1.0f,
        breakSound = { EstrogenSounds.DREAM_BLOCK_DORMANT_BREAK.value!! },
        stepSound = { EstrogenSounds.DREAM_BLOCK_DORMANT_STEP.value!! },
        placeSound = { EstrogenSounds.DREAM_BLOCK_DORMANT_PLACE.value!! },
        hitSound = { EstrogenSounds.DREAM_BLOCK_DORMANT_HIT.value!! },
        fallSound = { EstrogenSounds.DREAM_BLOCK_DORMANT_FALL.value!! }
    )

    val COOKIE_JAR: SoundType = LazySoundType(1.0f, 1.0f,
        breakSound = { EstrogenSounds.JAR_BREAK.value!! },
        stepSound = { EstrogenSounds.JAR_STEP.value!! },
        placeSound = { EstrogenSounds.JAR_PLACE.value!! },
        hitSound = { EstrogenSounds.JAR_HIT.value!! },
        fallSound = { EstrogenSounds.JAR_FALL.value!! }
    )

    val PILL_BOX: SoundType = LazySoundType(1.0f, 1.0f,
        breakSound = { EstrogenSounds.PILL_BOX_BREAK.value!! },
        stepSound = { EstrogenSounds.PILL_BOX_STEP.value!! },
        placeSound = { EstrogenSounds.PILL_BOX_PLACE.value!! },
        hitSound = { EstrogenSounds.PILL_BOX_HIT.value!! },
        fallSound = { EstrogenSounds.PILL_BOX_FALL.value!! }
    )
}