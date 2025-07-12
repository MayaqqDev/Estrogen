package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.content.EstrogenBlocks;
import dev.mayaqq.estrogen.content.items.DreamCatcherItem;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemProperties.class)
public class ItemPropertiesMixin {
    @Shadow private static void register(Item item, ResourceLocation name, ClampedItemPropertyFunction property) {}

    static {
        register(EstrogenBlocks.INSTANCE.getDreamCatcher().asItem(), new ResourceLocation("estrogen", "colored"), ((itemStack, clientLevel, livingEntity, i) -> {
            if (itemStack.getItem() instanceof DreamCatcherItem dreamcatcher) {
                if (dreamcatcher.isBlank(itemStack)) return 0.0F;
            }
            return 1.0F;
        }));
    }
}
