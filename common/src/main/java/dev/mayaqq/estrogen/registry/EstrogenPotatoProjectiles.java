package dev.mayaqq.estrogen.registry;

import com.simibubi.create.content.equipment.potatoCannon.PotatoCannonProjectileType;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;

import java.util.function.Predicate;

public class EstrogenPotatoProjectiles {
    public static final PotatoCannonProjectileType
            ESTROGEN_PILL = create("estrogen_pill")
                    .damage(3)
                    .reloadTicks(8)
                    .velocity(1.5f)
                    .knockback(0.3f)
                    .renderTumbling()
                    .onEntityHit(potion(EstrogenEffects.ESTROGEN_EFFECT.get(), 1, 200, true))
                    .registerAndAssign(EstrogenItems.ESTROGEN_PILL.get()),
            CRYSTAL_ESTROGEN_PILL = create("crystal_estrogen_pill")
                    .damage(3)
                    .reloadTicks(8)
                    .velocity(2.0f)
                    .knockback(0.4f)
                    .renderTumbling()
                    .onEntityHit(potion(EstrogenEffects.ESTROGEN_EFFECT.get(), 2, 300, true))
                    .registerAndAssign(EstrogenItems.CRYSTAL_ESTROGEN_PILL.get()),
            BALLS = create("balls")
                    .damage(5)
                    .reloadTicks(15)
                    .knockback(0.05f)
                    .velocity(1.25f)
                    .renderTumbling()
                    .onEntityHit(potion(MobEffects.CONFUSION, 1, 160, true))
                    .registerAndAssign(EstrogenItems.BALLS.get());

    private static PotatoCannonProjectileType.Builder create(String name) {
        return new PotatoCannonProjectileType.Builder(Estrogen.id(name));
    }

    private static Predicate<EntityHitResult> potion(MobEffect effect, int level, int ticks, boolean recoverable) {
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

    public static void register() {}
}
