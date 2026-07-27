package dev.mayaqq.estrogen.content.blocks.fluid

import com.teamresourceful.resourcefullib.common.fluid.data.FluidData
import dev.mayaqq.estrogen.content.EstrogenEffects
import invoke.kitty.kritter.registry.api.entry.holder
import net.minecraft.core.BlockPos
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

class EstrogenLiquidBlock(data: FluidData, properties: Properties, interactions: Array<FluidInteraction>) : BaseEstrogenLiquidBlock(data, properties, interactions) {
    override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
        if (entity is LivingEntity) entity.addEffect(MobEffectInstance(EstrogenEffects.Estrogen.holder, 20, 0))
        super.entityInside(state, level, pos, entity)
    }
}