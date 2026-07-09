package dev.mayaqq.estrogen.features.minigame

import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.events.entity.player.PlayerTickEvent
import dev.mayaqq.estrogen.config.EstrogenServerConfig
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.utils.holder
import invoke.kitty.kritter.registry.api.entry.holder
import net.minecraft.world.effect.MobEffectInstance



@EventSubscriber
object Minigame {
    @Subscription
    fun onTick(event: PlayerTickEvent.End) {
        if (EstrogenServerConfig.Minigame.enabled && EstrogenServerConfig.Minigame.permaDash) {
            if (event.player.level().gameTime % TRIGGER_EVERY_X_TICKS == 0L) {
                event.player.addEffect(
                    MobEffectInstance(
                        EstrogenEffects.Estrogen.holder,
                        EFFECT_DURATION,
                        EstrogenServerConfig.Minigame.girlPowerLevel,
                        false,
                        false,
                        false
                    )
                )
            }
        }
    }

    private const val TRIGGER_EVERY_X_TICKS: Int = 300
    private const val EFFECT_DURATION: Int = TRIGGER_EVERY_X_TICKS + 220
}