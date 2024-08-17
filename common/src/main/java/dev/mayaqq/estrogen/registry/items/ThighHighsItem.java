package dev.mayaqq.estrogen.registry.items;

import com.google.common.collect.Multimap;
import dev.mayaqq.estrogen.registry.EstrogenAttributes;
import earth.terrarium.baubly.Baubly;
import earth.terrarium.baubly.common.Bauble;
import earth.terrarium.baubly.common.SlotInfo;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class ThighHighsItem extends Item implements Bauble {
    private final int primaryColorDefault;
    private final int secondaryColorDefault;
    public static final String TAG_PRIMARY = "primaryColor";
    public static final String TAG_SECONDARY = "secondaryColor";
    public ThighHighsItem(Properties properties, int primaryColor, int secondaryColor) {
        super(properties);
        this.primaryColorDefault = primaryColor;
        this.secondaryColorDefault = secondaryColor;
        Baubly.registerBauble(this);
    }

    public int getDefaultColor(int tintIndex) {
        return tintIndex == 0 ? this.primaryColorDefault : this.secondaryColorDefault;
    }

    public boolean hasCustomColor(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        return compoundTag != null && compoundTag.contains(TAG_PRIMARY) && compoundTag.contains(TAG_SECONDARY);
    }

    public int getColor(ItemStack stack, int tintIndex) {
        CompoundTag compoundTag = stack.getTag();
        if (compoundTag != null) {
            if (tintIndex == 0 && compoundTag.contains(TAG_PRIMARY)) {
                return compoundTag.getInt(TAG_PRIMARY);
            } else if (tintIndex == 1 && compoundTag.contains(TAG_SECONDARY)) {
                return compoundTag.getInt(TAG_SECONDARY);
            }
        }
        return getDefaultColor(tintIndex);
    }

    public void clearColor(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        if (compoundTag != null) {
            compoundTag.remove(TAG_PRIMARY);
            compoundTag.remove(TAG_SECONDARY);
        }
    }

    public void setColor(ItemStack stack, int primaryColor, int secondaryColor) {
        stack.getOrCreateTag().putInt(TAG_PRIMARY, primaryColor);
        stack.getOrCreateTag().putInt(TAG_SECONDARY, secondaryColor);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(Multimap<Attribute, AttributeModifier> defaultModifiers, ItemStack stack, SlotInfo slot, UUID uuid) {
        defaultModifiers.put(EstrogenAttributes.FALL_DAMAGE_RESISTANCE.get(), new AttributeModifier(uuid, "ThighHighsFallDamageResistance", 100, AttributeModifier.Operation.ADDITION));
        return defaultModifiers;
    }
}
