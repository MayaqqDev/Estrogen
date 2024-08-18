package dev.mayaqq.estrogen.registry.items;

import dev.mayaqq.estrogen.registry.EstrogenItems;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;

public class MothElytraItem extends ElytraItem {
    public MothElytraItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(EstrogenItems.MOTH_FUZZ.get());
    }
}
