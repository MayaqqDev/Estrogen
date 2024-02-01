package dev.mayaqq.estrogen.platformSpecific.forge;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.mayaqq.estrogen.forge.registry.item.EstrogenPatchesItem;
import dev.mayaqq.estrogen.registry.common.EstrogenItems;

import static dev.mayaqq.estrogen.Estrogen.REGISTRATE;

public class PlatformItemRegistryImpl {
    @org.jetbrains.annotations.Contract
    public static void register() {
        RegistryEntry<EstrogenPatchesItem> ESTROGEN_PATCHES = REGISTRATE.item("estrogen_patches", EstrogenPatchesItem::new).properties(p -> new EstrogenItems.EstrogenProperties().stacksTo(4)).register();
    }
}