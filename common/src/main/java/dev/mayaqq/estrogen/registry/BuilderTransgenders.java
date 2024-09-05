package dev.mayaqq.estrogen.registry;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import net.minecraft.world.level.block.Block;
import uwu.serenity.critter.stdlib.blocks.BlockBuilder;

import java.util.function.UnaryOperator;

// :333333
public class BuilderTransgenders {
    static <B extends Block, P> UnaryOperator<BlockBuilder<B, P>> stressImpact(double impact) {
        return b -> {
            BlockStressDefaults.setDefaultImpact(b.getId(), impact);
            return b;
        };
    }
}
