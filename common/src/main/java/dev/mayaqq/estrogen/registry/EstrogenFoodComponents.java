package dev.mayaqq.estrogen.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class EstrogenFoodComponents {
    public static final FoodProperties ESTROGEN_PILL = (new FoodProperties.Builder()).effect(new MobEffectInstance(EstrogenEffects.ESTROGEN_EFFECT.get(), 6000, 0), 1).fast().alwaysEat().build();
    public static final FoodProperties CRYTAL_ESTROGEN_PILL = (new FoodProperties.Builder()).effect(new MobEffectInstance(EstrogenEffects.ESTROGEN_EFFECT.get(), 6000, 1), 1).fast().alwaysEat().build();
    public static final FoodProperties HORSE_URINE_BOTTLE = (new FoodProperties.Builder()).effect(new MobEffectInstance(MobEffects.POISON, 100, 0), 1).nutrition(1).saturationMod(0.1F).build();
    public static final FoodProperties ESTROGEN_CHIP_COOKIE = (new FoodProperties.Builder()).effect(new MobEffectInstance(EstrogenEffects.ESTROGEN_EFFECT.get(), 3000, 0), 1).nutrition(8).saturationMod(1.5F).fast().alwaysEat().build();

    public static void register() {
    }
}