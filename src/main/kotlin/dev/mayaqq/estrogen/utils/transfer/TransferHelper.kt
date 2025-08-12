package dev.mayaqq.estrogen.utils.transfer

import dev.mayaqq.cynosure.helpers.McFont
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.ClickEvent
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.level.Level

object TransferHelper {
    val messageParts = listOf<Component>(
        Component.literal("The Estrogen mod has updated and has been split into ").withStyle(ChatFormatting.RED),
        Component.literal("")
            .append(Component.literal("[Estrogen]")
                .withStyle { it.withClickEvent(
            ClickEvent(ClickEvent.Action.OPEN_URL, "https://modrinth.com/mod/estrogen"))
            .withColor(ChatFormatting.BLUE).withUnderlined(true)
        })
        .append(Component.literal(" and ").withStyle(ChatFormatting.RED))
        .append(
            Component.literal("[Create: Estrogen]").withStyle { it.withClickEvent(
                ClickEvent(ClickEvent.Action.OPEN_URL, "https://modrinth.com/mod/create-estrogen"))
                .withColor(ChatFormatting.BLUE).withUnderlined(true)
            }
        )
        .append(Component.literal(", please install ").withStyle(ChatFormatting.RED)),
        Component.literal("")
            .append(Component.literal("Create: Estrogen")
                .withStyle { it.withClickEvent(
            ClickEvent(ClickEvent.Action.OPEN_URL, "https://modrinth.com/mod/create-estrogen"))
            .withColor(ChatFormatting.BLUE).withUnderlined(true)
        })
        .append(Component.literal(" to fix.").withStyle(ChatFormatting.RED))
    )
    val message: MutableComponent = Component.literal("").apply { messageParts.forEach { this.append(it) } }

    fun message(level: Level) {
        level.server?.playerList?.players?.forEach { player ->
            player.displayClientMessage(message, false)
        }
    }
}