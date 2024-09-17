package dev.mayaqq.estrogen.platform;

import com.teamresourceful.resourcefullib.common.exceptions.NotImplementedException;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.mayaqq.estrogen.registry.tooltip.ThighHighsToolTipModifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class CommonPlatform {

    @ExpectPlatform
    public static TagKey<Item> getShearsTag() {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static ThighHighsToolTipModifier createThighHighTooltip(Item item) {
        throw  new NotImplementedException();
    }
}
