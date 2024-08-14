package dev.mayaqq.estrogen.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

@SuppressWarnings("unused")
public class EstrogenSounds {
    public static final ResourcefulRegistry<SoundEvent> SOUNDS = ResourcefulRegistries.create(BuiltInRegistries.SOUND_EVENT, Estrogen.MOD_ID);

    // Dash
    public static final RegistryEntry<SoundEvent> DASH =  SOUNDS.register("dash", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dash")));

    // Music Discs
    public static final RegistryEntry<SoundEvent> G03C = SOUNDS.register("g03c", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("g03c")));

    // Ambient Music
    public static final RegistryEntry<SoundEvent> TRUST_YOURSELF = SOUNDS.register("trust_yourself", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("trust_yourself")));
    public static final RegistryEntry<SoundEvent> AMPHITRITE = SOUNDS.register("amphitrite", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("amphitrite")));
    public static final RegistryEntry<SoundEvent> AURUM_BERRY = SOUNDS.register("aurum_berry", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("aurum_berry")));
    public static final RegistryEntry<SoundEvent> ESTROGEN_AMBIENT = SOUNDS.register("estrogen_ambient", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("estrogen_ambient")));
    public static final RegistryEntry<SoundEvent> INFERRED_DREAMS = SOUNDS.register("inferred_dreams", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("inferred_dreams")));
    public static final RegistryEntry<SoundEvent> SLEEPING = SOUNDS.register("sleeping", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("sleeping")));
    public static final RegistryEntry<SoundEvent> INNER_SELF_REALIZATION = SOUNDS.register("inner_selfrealization", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("inner_selfrealization")));

    // Dream Blocks
    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_ENTER = SOUNDS.register("dream_block_enter", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_enter")));
    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_LOOP = SOUNDS.register("dream_block_loop", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_loop")));
    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_EXIT = SOUNDS.register("dream_block_exit", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_exit")));
    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_PLACE = SOUNDS.register("dream_block_place", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_place")));

    // Dormant Dream Block
    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_DORMANT_BREAK = SOUNDS.register("dream_block_dormant_break", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_dormant_break")));
    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_DORMANT_PLACE = SOUNDS.register("dream_block_dormant_place", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_dormant_place")));
    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_DORMANT_STEP = SOUNDS.register("dream_block_dormant_step", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_dormant_step")));
    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_DORMANT_FALL = SOUNDS.register("dream_block_dormant_fall", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_dormant_fall")));

    // Cookie Jar
    public static final RegistryEntry<SoundEvent> JAR_FULL = SOUNDS.register("jar_full", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("jar_full")));
    public static final RegistryEntry<SoundEvent> JAR_PLACE = SOUNDS.register("jar_place", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("jar_place")));
    public static final RegistryEntry<SoundEvent> JAR_BREAK = SOUNDS.register("jar_break", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("jar_break")));
    public static final RegistryEntry<SoundEvent> JAR_STEP = SOUNDS.register("jar_step", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("jar_step")));
    public static final RegistryEntry<SoundEvent> JAR_INSERT = SOUNDS.register("jar_insert", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("jar_insert")));

    // Pill Box
    public static final RegistryEntry<SoundEvent> PILL_BOX_PLACE = SOUNDS.register("pill_box_place", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("pill_box_place")));
    public static final RegistryEntry<SoundEvent> PILL_BOX_BREAK = SOUNDS.register("pill_box_break", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("pill_box_break")));
    public static final RegistryEntry<SoundEvent> PILL_BOX_STEP = SOUNDS.register("pill_box_step", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("pill_box_step")));
}
