@file:EventSubscriber
package dev.mayaqq.estrogen.content.advancements.triggers

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.events.entity.LivingEntityEvent
import dev.mayaqq.estrogen.content.AdvancementTriggers
import net.minecraft.advancements.critereon.*
import net.minecraft.core.Holder
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.entity.Entity
import java.util.Optional

class KilledWithEffectTrigger : SimpleCriterionTrigger<KilledWithEffectTrigger.TriggerInstance>() {
    override fun codec(): Codec<TriggerInstance> = TriggerInstance.CODEC

    fun trigger(player: ServerPlayer, entityType: Entity) {
        this.trigger(
            player
        ) { instance: TriggerInstance ->
            instance.matches(
                player,
                entityType
            )
        }
    }

    @JvmRecord
    data class TriggerInstance(val entity: ContextAwarePredicate, val mobEffect: Holder<MobEffect>, val player: ContextAwarePredicate) : SimpleInstance {
        fun matches(player: ServerPlayer, entity: Entity): Boolean {
            if (this.entity.matches(EntityPredicate.createContext(player, entity))) {
                if (player.hasEffect(mobEffect)) {
                    return true
                }
            }
            return false
        }

        override fun player(): Optional<ContextAwarePredicate> = Optional.of(player)


        companion object {
            val CODEC: Codec<TriggerInstance> = RecordCodecBuilder.create { instance -> instance.group(
                    ContextAwarePredicate.CODEC.fieldOf("entity").forGetter(TriggerInstance::entity),
                    MobEffect.CODEC.fieldOf("mobEffect").forGetter(TriggerInstance::mobEffect),
                    EntityPredicate.ADVANCEMENT_CODEC.fieldOf("player").forGetter(TriggerInstance::player)
                ).apply(instance, ::TriggerInstance)
            }

            fun killedWithEffect(entity: ContextAwarePredicate, effect: Holder<MobEffect>, player: ContextAwarePredicate) : TriggerInstance {
                return TriggerInstance(entity, effect, player)
            }
        }
    }
}

@Subscription
fun onEntityDeath(event: LivingEntityEvent.Death) {
    if (event.source.entity is ServerPlayer) AdvancementTriggers.KilledWithEffect.trigger(event.source.entity as ServerPlayer, event.entity)
}