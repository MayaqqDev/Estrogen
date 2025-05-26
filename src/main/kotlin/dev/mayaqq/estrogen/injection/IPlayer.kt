package dev.mayaqq.estrogen.injection

import dev.mayaqq.estrogen.config.types.ChestConfig
import net.minecraft.world.entity.player.Player

var Player.chestConfig: ChestConfig?
    get() = (this as IPlayer).`estrogen$getChestConfig`()
    set(value) { (this as IPlayer).`estrogen$setChestConfig`(value) }

fun Player.flap() {
    (this as IPlayer).`estrogen$flap`()
}

fun Player.getLastFlap(): Long = (this as IPlayer).`estrogen$getLastFlap`()

interface IPlayer {

    fun `estrogen$getChestConfig`(): ChestConfig?

    fun `estrogen$setChestConfig`(config: ChestConfig?)

    fun `estrogen$flap`()

    fun `estrogen$getLastFlap`(): Long
}