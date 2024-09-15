package dev.mayaqq.estrogen.registry;

import com.simibubi.create.content.equipment.potatoCannon.PotatoCannonProjectileType;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class EstrogenPotatoProjectiles {

    // Made this map incase u need to access the projectiles in the future :3
    public static final Map<ResourceLocation, PotatoCannonProjectileType> PROJECTILES = new HashMap<>();

    public static Predicate<EntityHitResult> potion(MobEffect effect, int level, int ticks, boolean recoverable) {
        return ray -> {
            Entity entity = ray.getEntity();
            if (entity.level().isClientSide)
                return true;
            if (entity instanceof LivingEntity)
                applyEffect((LivingEntity) entity, new MobEffectInstance(effect, ticks, level - 1));
            return !recoverable;
        };
    }

    private static void applyEffect(LivingEntity entity, MobEffectInstance effect) {
        if (effect.getEffect()
                .isInstantenous())
            effect.getEffect()
                    .applyInstantenousEffect(null, null, entity, effect.getDuration(), 1.0);
        else
            entity.addEffect(effect);
    }
}
