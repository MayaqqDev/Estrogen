package dev.mayaqq.estrogen.features.minigame

import com.google.common.eventbus.Subscribe
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.entity.player.PlayerTickEvent
import dev.mayaqq.estrogen.config.EstrogenServerConfig
import dev.mayaqq.estrogen.content.EstrogenEffects
import net.minecraft.world.effect.MobEffectInstance



@EventSubscriber
object Minigame {
    @Subscribe
    fun onTick(event: PlayerTickEvent.End) {
        if (EstrogenServerConfig.Minigame.enabled && EstrogenServerConfig.Minigame.permaDash) event.player.addEffect(
            MobEffectInstance(
                EstrogenEffects.Estrogen,
                20,
                EstrogenServerConfig.Minigame.girlPowerLevel,
                false,
                false,
                false
            )
        )
    }
}