package dev.mayaqq.estrogen.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

public class EstrogenSounds {
    public static final ResourcefulRegistry<SoundEvent> SOUNDS = ResourcefulRegistries.create(BuiltInRegistries.SOUND_EVENT, Estrogen.MOD_ID);

    public static final RegistryEntry<SoundEvent> DASH =  SOUNDS.register("dash", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dash")));
    public static final RegistryEntry<SoundEvent> G03C = SOUNDS.register("g03c", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("g03c")));
    public static final RegistryEntry<SoundEvent> TRUST_YOURSELF = SOUNDS.register("trust_yourself", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("trust_yourself")));

    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_ENTER = SOUNDS.register("dream_block_enter", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_enter")));
    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_LOOP = SOUNDS.register("dream_block_loop", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_loop")));
    public static final RegistryEntry<SoundEvent> DREAM_BLOCK_EXIT = SOUNDS.register("dream_block_exit", () -> SoundEvent.createVariableRangeEvent(Estrogen.id("dream_block_exit")));
}
