package dev.mayaqq.estrogen.mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.JukeboxBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(JukeboxBlock.class)
public class JukeboxBlockMixin {
    @ModifyVariable(
            method = "setRecord",
            at = @At(value = "HEAD"),
            index = 4,
            argsOnly = true
    )
    private ItemStack modifyRecordItem(ItemStack value) {
        ItemStack copy = value.copy();
        copy.setCount(1);
        return copy;
    }
}