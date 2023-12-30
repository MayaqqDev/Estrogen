package dev.mayaqq.estrogen.registry.client;

import dev.mayaqq.estrogen.registry.common.EstrogenItems;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class EstrogenModelPredicateProviders {
    public static void register() {
        ModelPredicateProviderRegistry.register(EstrogenItems.ESTROGEN_PATCHES, new Identifier("stacked"), (stack, world, entity, seed) -> {
            return stack.getCount() > 1 ? 1 : 0;
        });
    }
}