package dev.mayaqq.estrogen.networking.messages.c2s;

import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.CodecPacketType;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.config.ChestConfig;
import dev.mayaqq.estrogen.config.PlayerEntityExtension;
import dev.mayaqq.estrogen.networking.EstrogenNetworkManager;
import dev.mayaqq.estrogen.networking.messages.s2c.ChestConfigPacket;
import net.minecraft.world.entity.player.Player;

import java.util.function.Consumer;

public record SetChestConfigPacket(ChestConfig config) implements Packet<SetChestConfigPacket> {

    public static ServerboundPacketType<SetChestConfigPacket> TYPE = new Type();

    @Override
    public PacketType<SetChestConfigPacket> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType.Server<SetChestConfigPacket> {

        public Type() {
            super(
                    SetChestConfigPacket.class,
                    Estrogen.id("chest_feature_config"),
                    ChestConfig.BYTE_CODEC.map(SetChestConfigPacket::new, SetChestConfigPacket::config)
            );
        }

        @Override
        public Consumer<Player> handle(SetChestConfigPacket message) {
            return (player) -> {
                ((PlayerEntityExtension) player).estrogen$setChestConfig(message.config);
                EstrogenNetworkManager.CHANNEL.sendToPlayersInLevel(new ChestConfigPacket(player.getUUID(), message.config), player.level());
            };
        }
    }
}
