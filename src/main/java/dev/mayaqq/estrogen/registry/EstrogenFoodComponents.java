package dev.mayaqq.estrogen.registry;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class EstrogenFoodComponents {
    public static final FoodComponent ESTROGEN_PILL = (new FoodComponent.Builder()).statusEffect(new StatusEffectInstance(EstrogenEffects.ESTROGEN_EFFECT, 6000, 0), 1).snack().alwaysEdible().build();
    public static final FoodComponent CRYTAL_ESTROGEN_PILL = (new FoodComponent.Builder()).statusEffect(new StatusEffectInstance(EstrogenEffects.ESTROGEN_EFFECT, 6000, 1), 1).snack().alwaysEdible().build();
    public static final FoodComponent HORSE_URINE_BOTTLE = (new FoodComponent.Builder()).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 0), 1).hunger(1).saturationModifier(0.1F).build();
    public static final FoodComponent ESTROGEN_CHIP_COOKIE = (new FoodComponent.Builder()).statusEffect(new StatusEffectInstance(EstrogenEffects.ESTROGEN_EFFECT, 6000, 0), 1).hunger(6).saturationModifier(1.0F).snack().alwaysEdible().build();
    public static void register() {}
}