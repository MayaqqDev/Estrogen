package dev.mayaqq.estrogen.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.stream.Stream;

@Mixin(BlockStateParser.class)
public class BlockStateParserMixin {
    @ModifyExpressionValue(
            method = "suggestItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/core/HolderLookup;listElementIds()Ljava/util/stream/Stream;")
    )
    private Stream<ResourceKey<Item>> suggestItem(Stream<ResourceKey<Item>> original) {
        return original.filter(item -> !item.location().equals(ResourceLocation.fromNamespaceAndPath("estrogen", "colon_three_block")));
    }
}
