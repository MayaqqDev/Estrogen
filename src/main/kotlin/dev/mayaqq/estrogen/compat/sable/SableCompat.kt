@file:Suppress("UnstableApiUsage")

package dev.mayaqq.estrogen.compat.sable

import dev.mayaqq.cynosure.utils.toBlockPos
import dev.mayaqq.cynosure.utils.toVec3
import dev.ryanhcode.sable.Sable
import dev.ryanhcode.sable.companion.math.BoundingBox3d
import net.minecraft.core.BlockPos
import net.minecraft.util.Mth
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.block.state.BlockState

object SableCompat {
    fun inBlockCheck(entity: LivingEntity, check: (BlockState) -> Boolean): Boolean {
        val playerAABB = entity.boundingBox
        val minPos = BlockPos.containing(playerAABB.minX, playerAABB.minY, playerAABB.minZ)
        val maxPos = BlockPos(
            Mth.ceil(playerAABB.maxX) - 1,
            Mth.ceil(playerAABB.maxY) - 1,
            Mth.ceil(playerAABB.maxZ) - 1
        )
        val boundCheck = Sable.HELPER.getAllIntersecting(entity.level(), BoundingBox3d(entity.boundingBox)).any { subLevel ->
            BlockPos.betweenClosedStream(minPos, maxPos).anyMatch { pos: BlockPos ->
                check(entity.level().getBlockState(BlockPos.containing(subLevel.logicalPose().transformPositionInverse(pos.toVec3()))))
            }
        }

        return boundCheck || BlockPos.betweenClosedStream(minPos, maxPos).anyMatch { pos: BlockPos ->
            check(entity.level().getBlockState(pos))
        }
    }

    fun eyesIn(entity: LivingEntity, check: (BlockState) -> Boolean): Boolean {
        val boundCheck = Sable.HELPER.getAllIntersecting(entity.level(), BoundingBox3d(entity.boundingBox))?.any {
            return check(entity.level().getBlockState(BlockPos.containing(it.logicalPose().transformPositionInverse(entity.eyePosition.toVec3()))))
        }?: false

        return boundCheck || check(entity.level().getBlockState(entity.eyePosition.toBlockPos()))
    }
}