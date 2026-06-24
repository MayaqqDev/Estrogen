package dev.mayaqq.estrogen.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.stream.Stream;

@Mixin(ItemParser.class)
public class ItemParserMixin {
    @ModifyExpressionValue(
            method = "suggestItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/core/HolderLookup;listElementIds()Ljava/util/stream/Stream;")
    )
    private Stream<ResourceKey<Item>> suggestItem(Stream<ResourceKey<Item>> original) {
        return original.filter(item -> !item.location().equals(new ResourceLocation("estrogen", "colon_three")));
    }
}
