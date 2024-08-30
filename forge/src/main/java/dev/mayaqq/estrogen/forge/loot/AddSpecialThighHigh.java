package dev.mayaqq.estrogen.forge.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import dev.mayaqq.estrogen.registry.items.ThighHighsItem;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class AddSpecialThighHigh extends LootModifier {

    public static final Codec<AddSpecialThighHigh> CODEC = RecordCodecBuilder.create(instance ->
        codecStart(instance).apply(instance, AddSpecialThighHigh::new)
    );

    protected AddSpecialThighHigh(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> loot, LootContext context) {
        ThighHighsItem item = EstrogenItems.THIGH_HIGHS.get();
        ItemStack itemStack = item.getDefaultInstance();
        item.setRandomStyle(itemStack, context.getRandom());
        loot.add(itemStack);
        return loot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
