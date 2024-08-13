package dev.mayaqq.estrogen.registry.items;

import com.google.common.collect.Multimap;
import dev.mayaqq.estrogen.registry.EstrogenAttributes;
import earth.terrarium.baubly.Baubly;
import earth.terrarium.baubly.common.Bauble;
import earth.terrarium.baubly.common.SlotInfo;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class ThighHighsItem extends Item implements Bauble {
    public ThighHighsItem(Properties properties) {
        super(properties);
        Baubly.registerBauble(this);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(Multimap<Attribute, AttributeModifier> defaultModifiers, ItemStack stack, SlotInfo slot, UUID uuid) {
        defaultModifiers.put(EstrogenAttributes.FALL_DAMAGE_RESISTANCE.get(), new AttributeModifier(uuid, "ThighHighsFallDamageResistance", 100, AttributeModifier.Operation.ADDITION));
        return defaultModifiers;
    }
}
