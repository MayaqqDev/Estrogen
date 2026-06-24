package dev.mayaqq.estrogen.injection

import dev.mayaqq.estrogen.config.types.ChestConfig
import net.minecraft.world.entity.player.Player

var Player.chestConfig: ChestConfig?
    get() = (this as IPlayer).`estrogen$getChestConfig`()
    set(value) { (this as IPlayer).`estrogen$setChestConfig`(value) }

interface IPlayerInfo {

    fun `estrogen$getChestConfig`(): ChestConfig?

    fun `estrogen$setChestConfig`(config: ChestConfig?)
}