package dev.mayaqq.estrogen.fabric.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import dev.mayaqq.estrogen.registry.items.ThighHighsItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

public class ThighHighStyleLootFunction extends LootItemConditionalFunction {

    public static final LootItemFunctionType TYPE = new LootItemFunctionType(new Serializer());

    protected ThighHighStyleLootFunction(LootItemCondition[] predicates) {
        super(predicates);
    }

    public static void register() {
        Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, Estrogen.id("thigh_high_style"), TYPE);
    }

    public static LootItemConditionalFunction.Builder<?> apply() {
        return simpleBuilder(ThighHighStyleLootFunction::new);
    }

    @Override
    protected @NotNull ItemStack run(ItemStack stack, LootContext context) {
        ThighHighsItem item = EstrogenItems.THIGH_HIGHS.get();
        item.setRandomStyle(stack, context.getRandom());
        return stack;
    }

    @Override
    public LootItemFunctionType getType() {
        return TYPE;
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<ThighHighStyleLootFunction> {

        @Override
        public @NotNull ThighHighStyleLootFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditions) {
            return new ThighHighStyleLootFunction(conditions);
        }
    }
}
