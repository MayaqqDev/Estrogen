package dev.mayaqq.estrogen.utils

import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState

object Never: BlockBehaviour.StatePredicate {
    override fun test(state: BlockState, block: BlockGetter, pos: BlockPos): Boolean = false

    fun <T> withArgument(): BlockBehaviour.StateArgumentPredicate<T> =
        BlockBehaviour.StateArgumentPredicate<T> { state, block, pos, arg -> false }
}

object Always: BlockBehaviour.StatePredicate {
    override fun test(state: BlockState, block: BlockGetter, pos: BlockPos): Boolean = true

    fun <T> withArgument(): BlockBehaviour.StateArgumentPredicate<T> =
        BlockBehaviour.StateArgumentPredicate<T> { state, block, pos, arg -> true }
}

