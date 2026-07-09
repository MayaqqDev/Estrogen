package dev.mayaqq.estrogen.network.messages.c2s

import dev.mayaqq.cynosure.entities.PlayerLookup
import dev.mayaqq.estrogen.config.types.ChestConfig
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.content.effects.EstrogenEffect
import dev.mayaqq.estrogen.injection.chestConfig
import dev.mayaqq.estrogen.network.EstrogenNetwork
import dev.mayaqq.estrogen.network.messages.s2c.ChestConfigPacket
import dev.mayaqq.estrogen.utils.holder
import invoke.kitty.kritter.registry.api.entry.holder
import kotlinx.serialization.Serializable
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.toKotlinUuid

@Serializable @JvmRecord
data class SetChestConfigPacket(val config: ChestConfig) {
    fun handle(server: MinecraftServer, sender: ServerPlayer) {
        sender.chestConfig = config

        for (player in PlayerLookup.tracking(sender)) {
            EstrogenEffect.sendPlayerStatusEffect(player, EstrogenEffects.Estrogen.holder, sender)
            player.chestConfig?.let {
                EstrogenNetwork.sendToPlayer(sender, ChestConfigPacket(player.uuid.toKotlinUuid(), it))
            }
        }
    }
}