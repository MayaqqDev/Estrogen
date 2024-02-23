package dev.mayaqq.estrogen.client.registry;

import dev.mayaqq.estrogen.registry.EstrogenItems;
import earth.terrarium.botarium.client.ClientHooks;
import net.minecraft.resources.ResourceLocation;

public class EstrogenModelPredicateProviders {
    public static void register() {
        ClientHooks.registerItemProperty(EstrogenItems.ESTROGEN_PATCHES.get(), new ResourceLocation("stacked"), (stack, world, entity, seed) -> {
            return stack.getCount() > 1 ? 1 : 0;
        });
    }
}