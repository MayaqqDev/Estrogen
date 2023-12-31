package dev.mayaqq.estrogen.networking;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;

import java.lang.reflect.UndeclaredThrowableException;

public class EstrogenStatusEffectSender { //idk if this'll work tbh im an idiot ngl -bagel

    public EstrogenStatusEffectSender() {
    }

    public void sendPlayerStatusEffect(ServerPlayer player, MobEffect effect, ServerPlayer... targetPlayers) {
        throw new UndeclaredThrowableException(new Throwable(), "should be overriden");
    }

    public void sendRemovePlayerStatusEffect(ServerPlayer player, MobEffect effect, ServerPlayer... targetPlayers) {
        throw new UndeclaredThrowableException(new Throwable(), "should be overriden");
    }
}
