package dev.mayaqq.estrogen.registry.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.architectury.injectables.annotations.PlatformOnly;
import dev.mayaqq.estrogen.platform.ForgeHook;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ElytraItem;

public class MothElytraItem extends ElytraItem {

    private Multimap<Attribute, AttributeModifier> defaultModifiers = null;

    public MothElytraItem(Properties properties) {
        super(properties);
    }

    @Override
    @PlatformOnly(value = "FORGE")
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        if (this.defaultModifiers == null) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(ForgeHook.getCaelusAttribute(), new AttributeModifier("Elytra Flight", 1.0, AttributeModifier.Operation.ADDITION));
            this.defaultModifiers = builder.build();
        }
        if (this.getEquipmentSlot() == EquipmentSlot.CHEST) {
            return this.defaultModifiers;
        }
        return super.getDefaultAttributeModifiers(slot);
    }
}
