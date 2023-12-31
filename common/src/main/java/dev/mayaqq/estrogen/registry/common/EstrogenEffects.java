package dev.mayaqq.estrogen.registry.common;

import dev.mayaqq.estrogen.registry.common.effects.EstrogenEffect;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenEffects {
    public static final MobEffect ESTROGEN_EFFECT = new EstrogenEffect(MobEffectCategory.BENEFICIAL, 104164161).addAttributeModifier(EstrogenAttributes.DASH_LEVEL.get(), "c2c16ccb-9acc-4f57-88e1-7b3e0c2ffe16", 1, AttributeModifier.Operation.ADDITION);

    public static void register() {
        Registry.register(BuiltInRegistries.MOB_EFFECT, id("estrogen"), ESTROGEN_EFFECT);
    }
}