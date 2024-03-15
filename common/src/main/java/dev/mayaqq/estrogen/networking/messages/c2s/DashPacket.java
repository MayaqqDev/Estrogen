package dev.mayaqq.estrogen.networking.messages.c2s;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
import dev.mayaqq.estrogen.registry.EstrogenSounds;
import dev.mayaqq.estrogen.registry.effects.EstrogenEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;

@SuppressWarnings("ClassEscapesDefinedScope")
public record DashPacket(Boolean sound) implements Packet<DashPacket> {
    public static Handler HANDLER = new Handler();
    public static final ResourceLocation ID = Estrogen.id("dash");

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<DashPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<DashPacket> {

        @Override
        public void encode(DashPacket message, FriendlyByteBuf buffer) {
            buffer.writeBoolean(message.sound);
        }

        @Override
        public DashPacket decode(FriendlyByteBuf buffer) {
            return new DashPacket(buffer.readBoolean());
        }

        @Override
        public PacketContext handle(DashPacket message) {
            return (player, level) -> {
                if (player.hasEffect(EstrogenEffects.ESTROGEN_EFFECT.get()) && level instanceof ServerLevel serverLevel) {
                    if (message.sound) serverLevel.playSound(null, player.blockPosition(), EstrogenSounds.DASH.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    // dash cooldown
                    if (message.sound) EstrogenEffect.dashing.put(player.getUUID(), 20);
                    // summon particles around player
                    serverLevel.sendParticles(ParticleTypes.CLOUD, player.getX(), player.getY(), player.getZ(), 10, 0.5, 0.5, 0.5, 0.5);
                }
            };
        }
    }
}
