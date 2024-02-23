package dev.mayaqq.estrogen.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

public class EstrogenPotions {
    public static final ResourcefulRegistry<Potion> POTIONS = ResourcefulRegistries.create(BuiltInRegistries.POTION, Estrogen.MOD_ID);

    public static final RegistryEntry<Potion> ESTROGEN_POTION = POTIONS.register("estrogen", () ->
            new Potion("estrogen", new MobEffectInstance(EstrogenEffects.ESTROGEN_EFFECT.get(), 12000))
    );
}
