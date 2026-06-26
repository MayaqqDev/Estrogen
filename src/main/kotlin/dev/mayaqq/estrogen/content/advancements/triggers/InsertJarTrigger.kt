package dev.mayaqq.estrogen.content.advancements.triggers

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.mayaqq.estrogen.content.advancements.triggers.InsertJarTrigger.TriggerInstance
import net.minecraft.advancements.critereon.*
import net.minecraft.server.level.ServerPlayer
import java.util.Optional

class InsertJarTrigger : SimpleCriterionTrigger<TriggerInstance>() {
    override fun codec(): Codec<TriggerInstance> = TriggerInstance.CODEC

    fun trigger(player: ServerPlayer) {
        this.trigger(player) { instance: TriggerInstance ->
            true
        }
    }

    @JvmRecord
    data class TriggerInstance(val player: ContextAwarePredicate) : SimpleInstance {
        override fun player(): Optional<ContextAwarePredicate> = Optional.of(player)

        companion object {
            val CODEC: Codec<TriggerInstance> = RecordCodecBuilder.create { instance -> instance.group(
                    EntityPredicate.ADVANCEMENT_CODEC.fieldOf("player").forGetter(TriggerInstance::player)
                ).apply(instance, ::TriggerInstance)
            }

            fun insertJar() : TriggerInstance {
                return TriggerInstance(ContextAwarePredicate.create())
            }
        }
    }
}