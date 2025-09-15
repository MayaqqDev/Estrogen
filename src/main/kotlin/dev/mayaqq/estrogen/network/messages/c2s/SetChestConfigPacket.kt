package dev.mayaqq.estrogen.network.messages.c2s

import dev.mayaqq.cynosure.entities.PlayerLookup
import dev.mayaqq.cynosure.network.Packet
import dev.mayaqq.cynosure.network.SerializablePacket
import dev.mayaqq.cynosure.network.ServerNetworkContext
import dev.mayaqq.estrogen.config.types.ChestConfig
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.content.effects.EstrogenEffect
import dev.mayaqq.estrogen.injection.chestConfig
import dev.mayaqq.estrogen.network.EstrogenNetwork
import dev.mayaqq.estrogen.network.messages.s2c.ChestConfigPacket

@SerializablePacket("set_chest_config")
data class SetChestConfigPacket(val config: ChestConfig) : Packet.Serverbound {
    override fun ServerNetworkContext.handle() = execute {
        sender.chestConfig = config

        for (player in PlayerLookup.tracking(sender)) {
            EstrogenEffect.sendPlayerStatusEffect(player, EstrogenEffects.Estrogen, sender)
            player.chestConfig?.let {
                EstrogenNetwork.sendToPlayer(ChestConfigPacket(player.uuid, it), sender)
            }
        }
    }
}