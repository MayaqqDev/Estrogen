package dev.mayaqq.estrogen.utils.transfer

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.ClickEvent
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.OutgoingChatMessage
import net.minecraft.world.level.Level

object TransferHelper {
    fun message(level: Level) {
        val message = Component.literal("The Estrogen mod has updated and has been split into ")
            .withStyle(ChatFormatting.RED)
            .append(
                Component.literal("[Estrogen]").withStyle { it.withClickEvent(
                    ClickEvent(ClickEvent.Action.OPEN_URL, "https://modrinth.com/mod/estrogen"))
                    .withColor(ChatFormatting.BLUE).withUnderlined(true)
                }
            )
            .append(Component.literal(" and ").withStyle(ChatFormatting.RED))
            .append(
                Component.literal("[Create: Estrogen]").withStyle { it.withClickEvent(
                    ClickEvent(ClickEvent.Action.OPEN_URL, "https://modrinth.com/mod/create-estrogen"))
                    .withColor(ChatFormatting.BLUE).withUnderlined(true)
                }
            )
            .append(Component.literal(", please install ").withStyle(ChatFormatting.RED))
            .append(
                Component.literal("Create: Estrogen").withStyle { it.withClickEvent(
                    ClickEvent(ClickEvent.Action.OPEN_URL, "https://modrinth.com/mod/create-estrogen"))
                    .withColor(ChatFormatting.BLUE).withUnderlined(true)
                }
            )
            .append(Component.literal(" to fix this message and your world.").withStyle(ChatFormatting.RED))
        level.server?.playerList?.players?.forEach { player ->
            player.displayClientMessage(message, false)
        }
    }
}