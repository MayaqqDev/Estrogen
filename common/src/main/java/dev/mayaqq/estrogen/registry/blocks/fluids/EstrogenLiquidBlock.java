package dev.mayaqq.estrogen.registry.blocks.fluids;

import dev.mayaqq.estrogen.registry.EstrogenEffects;
import earth.terrarium.botarium.common.registry.fluid.BotariumLiquidBlock;
import earth.terrarium.botarium.common.registry.fluid.FluidData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class EstrogenLiquidBlock extends BotariumLiquidBlock {
    public EstrogenLiquidBlock(FluidData data, Properties properties) {
        super(data, properties);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof Player player) {
            player.addEffect(new MobEffectInstance(EstrogenEffects.ESTROGEN_EFFECT.get(), 20, 0));
        }
    }
}