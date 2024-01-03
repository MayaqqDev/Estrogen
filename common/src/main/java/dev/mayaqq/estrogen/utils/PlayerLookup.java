package dev.mayaqq.estrogen.utils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Collection;
import java.util.Objects;

public class PlayerLookup {
    public static Collection<ServerPlayer> tracking(Entity entity) {
        //TODO This has to be redone once I find a better way without fapi.
        return Objects.requireNonNull(entity.getServer()).getPlayerList().getPlayers();
    }
}
