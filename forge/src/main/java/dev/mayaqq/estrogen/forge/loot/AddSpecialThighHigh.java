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
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import static dev.mayaqq.estrogen.Estrogen.MOD_ID;

public class AddSpecialThighHigh extends LootModifier {

    private static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
        DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MOD_ID);


    public static final RegistryObject<Codec<AddSpecialThighHigh>> CODEC = LOOT_MODIFIERS.register("special_thigh_highs", () ->
        RecordCodecBuilder.create(instance -> codecStart(instance).apply(instance, AddSpecialThighHigh::new))
    );

    protected AddSpecialThighHigh(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    public static void register(IEventBus bus) {
        LOOT_MODIFIERS.register(bus);
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
        return CODEC.get();
    }
}
