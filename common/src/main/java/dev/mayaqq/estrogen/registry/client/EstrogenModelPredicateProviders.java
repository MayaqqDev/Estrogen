package dev.mayaqq.estrogen.registry.client;

import dev.architectury.registry.item.ItemPropertiesRegistry;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.common.EstrogenItems;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class EstrogenModelPredicateProviders {
    public static void register() {
        ItemPropertiesRegistry.register(Registry.ITEM.get(Estrogen.id("estrogen_patches")), new ResourceLocation("stacked"), (stack, world, entity, seed) -> {
            return stack.getCount() > 1 ? 1 : 0;
        });
    }
}