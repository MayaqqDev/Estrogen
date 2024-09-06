package dev.mayaqq.estrogen.registry;


import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import uwu.serenity.critter.api.entry.RegistryEntry;
import uwu.serenity.critter.stdlib.Registrar;

@SuppressWarnings("unused")
public class EstrogenSounds {
    public static final Registrar<SoundEvent> SOUNDS = Registrar.create(Estrogen.MOD_ID, Registries.SOUND_EVENT);

    // Dash
    public static final RegistryEntry<SoundEvent> DASH =  SOUNDS.entry("dash", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dash"))).register();

    // Music Discs
    public static final RegistryEntry<SoundEvent> G03C = SOUNDS.entry("g03c", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("g03c"))).register();

    // Ambient Music
    public static final RegistryEntry<SoundEvent> TRUST_YOURSELF = SOUNDS.entry("trust_yourself", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("trust_yourself"))).register();
    public static final RegistryEntry<SoundEvent> AMPHITRITE = SOUNDS.entry("amphitrite", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("amphitrite"))).register();
    public static final RegistryEntry<SoundEvent> AURUM_BERRY = SOUNDS.entry("aurum_berry", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("aurum_berry"))).register();
    public static final RegistryEntry<SoundEvent> ESTROGEN_AMBIENT = SOUNDS.entry("estrogen_ambient", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("estrogen_ambient"))).register();
    public static final RegistryEntry<SoundEvent> INFERRED_DREAMS = SOUNDS.entry("inferred_dreams", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("inferred_dreams"))).register();
    public static final RegistryEntry<SoundEvent> SLEEPING = SOUNDS.entry("sleeping", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("sleeping"))).register();
    public static final RegistryEntry<SoundEvent> INNER_SELF_REALIZATION = SOUNDS.entry("inner_selfrealization", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("inner_selfrealization"))).register();

    // Dream Blocks
    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_ENTER = SOUNDS.entry("dream_block_enter", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_enter"))).register();
    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_LOOP = SOUNDS.entry("dream_block_loop", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_loop"))).register();
    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_EXIT = SOUNDS.entry("dream_block_exit", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_exit"))).register();
    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_PLACE = SOUNDS.entry("dream_block_place", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_place"))).register();
    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_BREAK = SOUNDS.entry("dream_block_break", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_break"))).register();
    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_STEP = SOUNDS.entry("dream_block_step", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_step"))).register();
    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_FALL = SOUNDS.entry("dream_block_fall", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_fall"))).register();
    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_HIT = SOUNDS.entry("dream_block_hit", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_hit"))).register();

    // Dormant Dream Block
    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_DORMANT_BREAK = SOUNDS.entry("dream_block_dormant_break", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_dormant_break"))).register();
    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_DORMANT_PLACE = SOUNDS.entry("dream_block_dormant_place", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_dormant_place"))).register();
    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_DORMANT_STEP = SOUNDS.entry("dream_block_dormant_step", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_dormant_step"))).register();
    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_DORMANT_FALL = SOUNDS.entry("dream_block_dormant_fall", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_dormant_fall"))).register();
    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_DORMANT_HIT = SOUNDS.entry("dream_block_dormant_hit", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_dormant_hit"))).register();

    // Cookie Jar
    public static final RegistryEntry<SoundEvent> JAR_FULL = SOUNDS.entry("jar_full", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("jar_full"))).register();
    public static final RegistryEntry<SoundEvent> JAR_PLACE = SOUNDS.entry("jar_place", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("jar_place"))).register();
    public static final RegistryEntry<SoundEvent> JAR_BREAK = SOUNDS.entry("jar_break", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("jar_break"))).register();
    public static final RegistryEntry<SoundEvent> JAR_STEP = SOUNDS.entry("jar_step", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("jar_step"))).register();
    public static final RegistryEntry<SoundEvent> JAR_INSERT = SOUNDS.entry("jar_insert", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("jar_insert"))).register();
    public static final RegistryEntry<SoundEvent> JAR_HIT = SOUNDS.entry("jar_hit", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("jar_hit"))).register();
    public static final RegistryEntry<SoundEvent> JAR_FALL = SOUNDS.entry("jar_fall", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("jar_fall"))).register();

    // Pill Box
    public static final RegistryEntry<SoundEvent> PILL_BOX_PLACE = SOUNDS.entry("pill_box_place", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("pill_box_place"))).register();
    public static final RegistryEntry<SoundEvent> PILL_BOX_BREAK = SOUNDS.entry("pill_box_break", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("pill_box_break"))).register();
    public static final RegistryEntry<SoundEvent> PILL_BOX_STEP = SOUNDS.entry("pill_box_step", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("pill_box_step"))).register();
    public static final RegistryEntry<SoundEvent> PILL_BOX_HIT = SOUNDS.entry("pill_box_hit", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("pill_box_hit"))).register();
    public static final RegistryEntry<SoundEvent> PILL_BOX_FALL = SOUNDS.entry("pill_box_fall", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("pill_box_fall"))).register();

    // Moth
    public static final RegistryEntry<SoundEvent> MOTH_DEATH = SOUNDS.entry("moth_death", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("moth_death"))).register();
    public static final RegistryEntry<SoundEvent> MOTH_HURT = SOUNDS.entry("moth_hurt", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("moth_hurt"))).register();
    public static final RegistryEntry<SoundEvent> MOTH_LOOP = SOUNDS.entry("moth_loop", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("moth_loop"))).register();
    public static final RegistryEntry<SoundEvent> MOTH_FUZZ_UP = SOUNDS.entry("moth_fuzz_up", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("moth_fuzz_up"))).register();
}
