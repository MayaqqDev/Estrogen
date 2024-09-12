package dev.mayaqq.estrogen.platform.forge;

import dev.mayaqq.estrogen.forge.tooltip.ForgeThighHighsTooltip;
import dev.mayaqq.estrogen.registry.items.ThighHighsItem;
import dev.mayaqq.estrogen.registry.tooltip.ThighHighsToolTipModifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;

import java.util.function.Supplier;

public class CommonPlatformImpl {
    public static TagKey<Item> getShearsTag() {
        return TagKey.create(BuiltInRegistries.ITEM.key(), new ResourceLocation("forge", "shears"));
    }

    public static ThighHighsToolTipModifier createThighHighTooltip(Item item) {
        return new ForgeThighHighsTooltip((ThighHighsItem) item);
    }
}
