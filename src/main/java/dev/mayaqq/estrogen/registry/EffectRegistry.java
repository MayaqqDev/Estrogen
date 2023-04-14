package dev.mayaqq.estrogen.registry;

import dev.mayaqq.estrogen.effects.WomanEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.registry.Registry;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EffectRegistry {
    public static final StatusEffect WOMAN_EFFECT = new WomanEffect(StatusEffectCategory.BENEFICIAL, 104164161);

    public static void register() {
        Registry.register(Registry.STATUS_EFFECT, id("woman"), WOMAN_EFFECT);
    }
}