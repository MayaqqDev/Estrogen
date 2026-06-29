package dev.mayaqq.estrogen.content.features

import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.blocks.MemorialBlock.Companion.PART
import net.minecraft.core.Direction
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration

class MemorialFeature() : Feature<NoneFeatureConfiguration>(NoneFeatureConfiguration.CODEC) {
    override fun place(ctx: FeaturePlaceContext<NoneFeatureConfiguration>): Boolean {
        val level = ctx.level()
        val pos = ctx.origin()

        if (!level.isClientSide && pos.y >= 128) {
            val state = EstrogenBlocks.Memorial.value!!.defaultBlockState()

            for (y in 0..2) {
                for (x in 0..1) {
                    level.setBlock(
                        pos
                            .relative(Direction.WEST, x)
                            .relative(Direction.UP, y),
                        state.setValue(PART, x + (y + y) + 1),
                        3
                    )
                }
            }
            val floor2 = pos.relative(Direction.DOWN).relative(Direction.WEST)
            if (level.getBlockState(floor2).isAir) {
                level.setBlock(floor2, level.getBlockState(pos.relative(Direction.DOWN)), 3)
            }
        }
        return true
    }
}