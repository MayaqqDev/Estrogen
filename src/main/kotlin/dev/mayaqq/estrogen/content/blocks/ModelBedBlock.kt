package dev.mayaqq.estrogen.content.blocks

import net.minecraft.core.Direction
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.BedBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.state.BlockState

/*
 * Code and models are taken and modified from the BetterBeds mod by TeamMidnightDust
 * The code falls under the MIT license, thanks TeamMidnightDust!
 */
@Suppress("OVERRIDE_DEPRECATION")
class ModelBedBlock(color: DyeColor?, properties: Properties) : BedBlock(color, properties) {
    constructor(properties: Properties) : this(null, properties)

    override fun getRenderShape(state: BlockState): RenderShape = RenderShape.MODEL

    override fun skipRendering(state: BlockState, neigborState: BlockState, offset: Direction): Boolean = neigborState.block is BedBlock
}