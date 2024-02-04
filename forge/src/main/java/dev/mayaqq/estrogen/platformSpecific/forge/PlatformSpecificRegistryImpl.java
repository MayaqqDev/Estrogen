package dev.mayaqq.estrogen.platformSpecific.forge;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;
import dev.mayaqq.estrogen.registry.common.EstrogenItems;
import dev.mayaqq.estrogen.forge.items.EstrogenPatchesItem;

import static dev.mayaqq.estrogen.Estrogen.REGISTRATE;

public class PlatformSpecificRegistryImpl {
    @org.jetbrains.annotations.Contract
    public static ItemEntry<? extends Item> getRegisteredPatchesItem() {
        return REGISTRATE.item("estrogen_patches", EstrogenPatchesItem::new).properties(p -> new EstrogenItems.EstrogenProperties().stacksTo(4)).register();
    }
}
