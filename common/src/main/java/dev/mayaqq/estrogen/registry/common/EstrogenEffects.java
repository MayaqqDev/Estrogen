package dev.mayaqq.estrogen.registry.common;

import dev.architectury.registry.registries.Registrar;
import dev.mayaqq.estrogen.registry.common.effects.EstrogenEffect;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import static dev.mayaqq.estrogen.Estrogen.MANAGER;
import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenEffects {

    public static final Registrar<MobEffect> MOB_EFFECTS = MANAGER.get().get(Registry.MOB_EFFECT);

    public static final MobEffect ESTROGEN_EFFECT = new EstrogenEffect(MobEffectCategory.BENEFICIAL, 104164161);

    public static void register() {
        MOB_EFFECTS.register(id("estrogen"), () -> ESTROGEN_EFFECT);
    }
}