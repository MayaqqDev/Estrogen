package dev.mayaqq.estrogen.networking.messages.c2s;

import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.DatalessPacketType;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.config.ChestConfig;
import dev.mayaqq.estrogen.config.PlayerEntityExtension;
import dev.mayaqq.estrogen.networking.EstrogenNetworkManager;
import dev.mayaqq.estrogen.networking.messages.s2c.ChestConfigPacket;
import dev.mayaqq.estrogen.registry.effects.EstrogenEffect;
import dev.mayaqq.estrogen.utils.PlayerLookup;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.function.Consumer;

import static dev.mayaqq.estrogen.registry.EstrogenEffects.ESTROGEN_EFFECT;

public record FinishLoadingPacket() implements Packet<FinishLoadingPacket> {

    public static ServerboundPacketType<FinishLoadingPacket> TYPE = new Type();

    @Override
    public PacketType<FinishLoadingPacket> type() {
        return TYPE;
    }

    private static class Type extends DatalessPacketType.Server<FinishLoadingPacket> {

        public Type() {
            super(
                    FinishLoadingPacket.class,
                    Estrogen.id("finish_loading"),
                    FinishLoadingPacket::new
            );
        }

        @Override
        public Consumer<Player> handle(FinishLoadingPacket message) {
            return entity -> {
                if (entity instanceof ServerPlayer targetPlayer) {
                    for (ServerPlayer player: PlayerLookup.tracking(entity)) {
                        EstrogenEffect.sendPlayerStatusEffect(player, ESTROGEN_EFFECT.get(), targetPlayer);

                        ChestConfig chestConfig = ((PlayerEntityExtension) player).estrogen$getChestConfig();
                        if (chestConfig != null) {
                            EstrogenNetworkManager.CHANNEL.sendToPlayer(new ChestConfigPacket(player.getUUID(), chestConfig), targetPlayer);
                        }
                    }
                }
            };
        }
    }
}
