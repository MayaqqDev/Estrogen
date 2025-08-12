package dev.mayaqq.estrogen.utils.transfer

import net.minecraft.network.chat.ClickEvent
import net.minecraft.network.chat.Component
import net.minecraft.world.level.Level

object TransferHelper {
    fun message(level: Level) {
        val message = Component.literal("The Estrogen mod has updated and has been split into \n")
            .append(
                Component.literal("[Estrogen]").withStyle { it.withClickEvent(
                    ClickEvent(ClickEvent.Action.OPEN_URL, "https://modrinth.com/mod/estrogen"))}
            )
            .append(" and ")
            .append(
                Component.literal("[Create: Estrogen]").withStyle { it.withClickEvent(
                    ClickEvent(ClickEvent.Action.OPEN_URL, "https://modrinth.com/mod/create-estrogen"))}
            )
            .append(", please install Create: Estrogen\nto fix this message and your world.")
        level.server?.sendSystemMessage(message)
    }
}