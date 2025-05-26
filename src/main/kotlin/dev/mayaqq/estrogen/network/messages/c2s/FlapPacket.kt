package dev.mayaqq.estrogen.network.messages.c2s

import dev.mayaqq.cynosure.network.Packet
import dev.mayaqq.cynosure.network.SerializablePacket
import dev.mayaqq.cynosure.network.ServerNetworkContext
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource

@SerializablePacket("flap")
data class FlapPacket(val flap: Int) : Packet.Serverbound {
    override fun ServerNetworkContext.handle() = execute {
        sender.level().playSound(null, sender, SoundEvents.ENDER_DRAGON_FLAP, SoundSource.PLAYERS, 1.0F, 1.4F)
    }
}