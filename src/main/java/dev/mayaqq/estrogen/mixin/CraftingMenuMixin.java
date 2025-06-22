package dev.mayaqq.estrogen.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.crafting.CraftingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(CraftingMenu.class)
public class CraftingMenuMixin {
    @WrapOperation(
            method = "slotChangedCraftingGrid",
            at = @At(value = "INVOKE", target = "Ljava/util/Optional;isPresent()Z")
    )
    private static boolean creativelessCheck(Optional<CraftingRecipe> instance, Operation<Boolean> original, @Local(argsOnly = true) Player player) {
        if (player.isCreative()) return false;
        return original.call(instance);
    }
}
