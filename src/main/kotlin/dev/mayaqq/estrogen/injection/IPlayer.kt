package dev.mayaqq.estrogen.injection

import net.minecraft.world.entity.player.Player

fun Player.flap() {
    (this as IPlayer).`estrogen$flap`()
}

fun Player.getLastFlap(): Long = (this as IPlayer).`estrogen$getLastFlap`()

interface IPlayer : IPlayerInfo {

    fun `estrogen$flap`()

    fun `estrogen$getLastFlap`(): Long
}