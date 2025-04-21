package dev.mayaqq.estrogen.content.advancements.triggers

import com.google.gson.JsonObject
import dev.mayaqq.estrogen.id
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance
import net.minecraft.advancements.critereon.ContextAwarePredicate
import net.minecraft.advancements.critereon.DeserializationContext
import net.minecraft.advancements.critereon.SerializationContext
import net.minecraft.advancements.critereon.SimpleCriterionTrigger
import net.minecraft.server.level.ServerPlayer

open class InsertJarTrigger : SimpleCriterionTrigger<InsertJarTrigger.TriggerInstance>() {
    protected override fun createInstance(json: JsonObject, predicate: ContextAwarePredicate, deserializationContext: DeserializationContext) : TriggerInstance = TriggerInstance(predicate)

    override fun getId() = ID

    fun trigger(player: ServerPlayer) {
        this.trigger(player)
    }

    class TriggerInstance(player: ContextAwarePredicate) : AbstractCriterionTriggerInstance(ID, player) {
        override fun serializeToJson(context: SerializationContext): JsonObject = super.serializeToJson(context)

        companion object {
            fun insertJar() : TriggerInstance {
                return TriggerInstance(ContextAwarePredicate.ANY)
            }
        }
    }

    companion object {
        protected val ID = id("insert_jar")
    }
}