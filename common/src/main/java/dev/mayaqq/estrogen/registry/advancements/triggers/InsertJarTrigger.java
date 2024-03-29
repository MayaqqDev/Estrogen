package dev.mayaqq.estrogen.registry.advancements.triggers;

import com.google.gson.JsonObject;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class InsertJarTrigger extends SimpleCriterionTrigger<InsertJarTrigger.TriggerInstance> {
    static final ResourceLocation ID = Estrogen.id("insert_jar");

    @Override
    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext deserializationContext) {
        return new TriggerInstance(predicate);
    }

    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, (instance) -> true);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(ContextAwarePredicate player) {
            super(InsertJarTrigger.ID, player);
        }

        public static TriggerInstance insertJar() {
            return new TriggerInstance(ContextAwarePredicate.ANY);
        }

        public JsonObject serializeToJson(SerializationContext context) {
            return super.serializeToJson(context);
        }
    }
}
