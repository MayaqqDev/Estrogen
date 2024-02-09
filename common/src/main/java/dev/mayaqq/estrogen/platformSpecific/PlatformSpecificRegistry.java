package dev.mayaqq.estrogen.platformSpecific;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.utils.PlatformExpectedError;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Contract;

public class PlatformSpecificRegistry {
    private PlatformSpecificRegistry() {
    }

    @Contract
    @ExpectPlatform
    public static ItemEntry<? extends Item> getRegisteredPatchesItem() {
        throw new PlatformExpectedError();
    }
}
