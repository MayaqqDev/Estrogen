package dev.mayaqq.estrogen.content.advancements.triggers

import com.google.gson.JsonObject
import dev.mayaqq.estrogen.id
import net.minecraft.advancements.critereon.*
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.entity.Entity

class KilledWithEffectTrigger : SimpleCriterionTrigger<KilledWithEffectTrigger.TriggerInstance>() {
    override fun createInstance(json: JsonObject, predicate: ContextAwarePredicate, context: DeserializationContext): TriggerInstance {
        return TriggerInstance(EntityPredicate.fromJson(json, "entity", context), BuiltInRegistries.MOB_EFFECT.getHolder(json.get("mobeffect").asInt).get().value(), predicate)
    }

    override fun getId(): ResourceLocation = ID

    class TriggerInstance(val entity: ContextAwarePredicate, val mobEffect: MobEffect, player: ContextAwarePredicate) : AbstractCriterionTriggerInstance(ID, player) {
        fun matches(player: ServerPlayer, entity: Entity): Boolean {
            if (this.entity.matches(EntityPredicate.createContext(player, entity))) {
                if (player.hasEffect(mobEffect)) {
                    return true
                }
            }
            return false
        }

        override fun serializeToJson(context: SerializationContext): JsonObject {
            val json = super.serializeToJson(context)
            json.add("entity", entity.toJson(context))
            json.addProperty("mobeffect", BuiltInRegistries.MOB_EFFECT.getId(mobEffect))
            return json
        }

        companion object {
            fun killedWithEffect(entity: ContextAwarePredicate, effect: MobEffect, player: ContextAwarePredicate) : TriggerInstance {
                return TriggerInstance(entity, effect, player)
            }
        }
    }

    companion object {
        val ID = id("killed_with_effect")
    }
}