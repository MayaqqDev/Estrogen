package dev.mayaqq.estrogen.content.blocks.fluid

import com.teamresourceful.resourcefullib.common.fluid.data.FluidData
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

class LavaLikeLiquidBlock(data: FluidData, properties: Properties) : BaseEstrogenLiquidBlock(data, properties) {
    override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
        entity.lavaHurt()
        super.entityInside(state, level, pos, entity)
    }
}