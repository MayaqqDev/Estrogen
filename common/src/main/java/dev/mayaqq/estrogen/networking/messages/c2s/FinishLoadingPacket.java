package dev.mayaqq.estrogen.networking.messages.c2s;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.config.ChestConfig;
import dev.mayaqq.estrogen.config.PlayerEntityExtension;
import dev.mayaqq.estrogen.networking.EstrogenNetworkManager;
import dev.mayaqq.estrogen.networking.messages.s2c.ChestConfigPacket;
import dev.mayaqq.estrogen.registry.effects.EstrogenEffect;
import dev.mayaqq.estrogen.utils.PlayerLookup;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import static dev.mayaqq.estrogen.registry.EstrogenEffects.ESTROGEN_EFFECT;

@SuppressWarnings("ClassEscapesDefinedScope")
public record FinishLoadingPacket() implements Packet<FinishLoadingPacket> {
    public static Handler HANDLER = new Handler();
    public static final ResourceLocation ID = Estrogen.id("finish_loading");

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<FinishLoadingPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<FinishLoadingPacket> {

        @Override
        public void encode(FinishLoadingPacket message, FriendlyByteBuf buffer) {
        }

        @Override
        public FinishLoadingPacket decode(FriendlyByteBuf buffer) {
            return new FinishLoadingPacket();
        }

        @Override
        public PacketContext handle(FinishLoadingPacket message) {
            return (entity, level) -> {
                if (entity instanceof ServerPlayer targetPlayer) {
                    for (ServerPlayer player: PlayerLookup.tracking(entity)) {
                        EstrogenEffect.sendPlayerStatusEffect(player, ESTROGEN_EFFECT.get(), targetPlayer);

                        ChestConfig chestConfig = ((PlayerEntityExtension) player).estrogen$getChestConfig();
                        if (chestConfig != null) {
                            EstrogenNetworkManager.CHANNEL.sendToPlayer(new ChestConfigPacket(player.getUUID(), chestConfig.enabled(), chestConfig.armorEnabled(), chestConfig.physicsEnabled(), chestConfig.bounciness(), chestConfig.damping()), targetPlayer);
                        }
                    }
                }
            };
        }
    }
}
