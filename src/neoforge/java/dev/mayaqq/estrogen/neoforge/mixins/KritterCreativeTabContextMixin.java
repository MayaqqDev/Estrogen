package dev.mayaqq.estrogen.neoforge.mixins;

import invoke.kitty.kritter.creativeTabs.CreativeTabModificationContext;
import invoke.kitty.kritter.creativeTabs.TabPlacement;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(
        targets = "invoke.kitty.kritter.creativeTabs.impl.platform.CreativeTabContextImpl",
        remap = false
)
public abstract class KritterCreativeTabContextMixin {
    @Inject(
            method = "accept(Linvoke/kitty/kritter/creativeTabs/TabPlacement;Ljava/util/Collection;Lnet/minecraft/world/item/CreativeModeTab$TabVisibility;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void estrogen$splitCombinedVisibility(
            TabPlacement placement,
            Collection<ItemStack> stacks,
            CreativeModeTab.TabVisibility visibility,
            CallbackInfo callback
    ) {
        if (visibility != CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS) {
            return;
        }

        CreativeTabModificationContext context = (CreativeTabModificationContext) (Object) this;
        context.accept(placement, stacks, CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
        context.accept(placement, stacks, CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY);
        callback.cancel();
    }
}
