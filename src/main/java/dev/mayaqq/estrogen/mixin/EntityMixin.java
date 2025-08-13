package dev.mayaqq.estrogen.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.mayaqq.estrogen.content.blocks.DreamBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @WrapMethod(method = "setSharedFlag")
    private void stayGlidingDreamBlock(int flag, boolean set, Operation<Void> original) {
        if (flag == 7 && (Object) this instanceof Player && DreamBlock.isInDreamBlock((Player) (Object) this)) return;
        original.call(flag, set);
    }
}
