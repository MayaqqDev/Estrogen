package dev.mayaqq.estrogen.registry;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.effects.EstrogenEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import uwu.serenity.critter.api.entry.RegistryEntry;
import uwu.serenity.critter.stdlib.Registrar;


public class EstrogenEffects {

    public static final Registrar<MobEffect> MOB_EFFECTS = Registrar.create(Estrogen.MOD_ID, Registries.MOB_EFFECT);

    public static final RegistryEntry<EstrogenEffect> ESTROGEN_EFFECT = MOB_EFFECTS.entry("estrogen", () -> new EstrogenEffect(MobEffectCategory.BENEFICIAL, 104164161)).register();
}