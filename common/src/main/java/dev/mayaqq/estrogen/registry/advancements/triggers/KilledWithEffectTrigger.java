package dev.mayaqq.estrogen.registry.advancements.triggers;

import com.google.gson.JsonObject;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;

public class KilledWithEffectTrigger extends SimpleCriterionTrigger<KilledWithEffectTrigger.TriggerInstance> {
    static final ResourceLocation ID = Estrogen.id("killed_with_effect");

    @Override
    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext deserializationContext) {
        return new TriggerInstance(EntityPredicate.fromJson(json, "entity", deserializationContext), BuiltInRegistries.MOB_EFFECT.getHolder(json.get("mobeffect").getAsInt()).get().value(), predicate);
    }

    public void trigger(ServerPlayer player, Entity entityType) {
        this.trigger(player, (instance) -> instance.matches(player, entityType));
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        private final ContextAwarePredicate entity;
        private final MobEffect effect;

        public TriggerInstance(ContextAwarePredicate entity, MobEffect effect, ContextAwarePredicate player) {
            super(ID, player);
            this.entity = entity;
            this.effect = effect;
        }

        public boolean matches(ServerPlayer player, Entity entity) {
            if (this.entity.matches(EntityPredicate.createContext(player, entity))) {
                if (player.hasEffect(effect)) {
                    return true;
                }
            }
            return false;
        }

        public static KilledWithEffectTrigger.TriggerInstance killedWithEffect(ContextAwarePredicate entity, MobEffect effect, ContextAwarePredicate player) {
            return new KilledWithEffectTrigger.TriggerInstance(entity, effect, player);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext context) {
            JsonObject jsonObject = super.serializeToJson(context);
            jsonObject.add("entity", this.entity.toJson(context));
            jsonObject.addProperty("mobeffect", BuiltInRegistries.MOB_EFFECT.getId(effect));
            return jsonObject;
        }
    }
}
