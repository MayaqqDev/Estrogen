package dev.mayaqq.estrogen.registry;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;

public class FoodCompontentRegistry {
    public static final FoodComponent ESTROGEN_PILL = (new FoodComponent.Builder()).statusEffect(new StatusEffectInstance(EffectRegistry.WOMAN_EFFECT, 6000, 0), 1).snack().alwaysEdible().build();
    public static final FoodComponent CRYTAL_ESTROGEN_PILL = (new FoodComponent.Builder()).statusEffect(new StatusEffectInstance(EffectRegistry.WOMAN_EFFECT, 6000, 1), 1).snack().alwaysEdible().build();
    public static void register() {}
}