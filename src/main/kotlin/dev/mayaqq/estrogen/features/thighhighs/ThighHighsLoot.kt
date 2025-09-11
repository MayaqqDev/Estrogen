@file:EventSubscriber
package dev.mayaqq.estrogen.features.thighhighs

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.events.world.LoottableEvents
import dev.mayaqq.cynosure.injection.conditionally
import dev.mayaqq.cynosure.injection.with
import dev.mayaqq.estrogen.content.EstrogenItems
import dev.mayaqq.estrogen.content.EstrogenLootFunctions
import dev.mayaqq.estrogen.content.items.ThighHighsItem
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.storage.loot.LootContext
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue

private val LOOT_LOCATIONS: MutableList<ResourceLocation> = listOf(
    "minecraft:chests/woodland_mansion",
    "minecraft:chests/buried_treasure",
    "minecraft:chests/abandoned_mineshaft",
    "minecraft:chests/shipwreck_supply",
    "minecraft:chests/end_city",
    "minecraft:chests/ancient_city"
).map { ResourceLocation(it) }.toMutableList()

@Subscription
fun LoottableEvents.Modify.onLootModify() {
    if (LOOT_LOCATIONS.contains(id)) {
        val pool = LootPool.lootPool()
            .setRolls(ConstantValue.exactly(1F))
            .conditionally(LootItemRandomChanceCondition.randomChance(0.1f).build())
            .with(
                LootItem.lootTableItem(EstrogenItems.ThighHighs)
                    .apply(ThighHighStyleLootFunction.apply())
                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f)))
                    .build()
            )
        this.builder.withPool(pool)
    }
}

class ThighHighStyleLootFunction(predicates: Array<LootItemCondition>) : LootItemConditionalFunction(predicates) {
    override fun run(stack: ItemStack, context: LootContext): ItemStack {
        val item: ThighHighsItem = EstrogenItems.ThighHighs
        item.setRandomStyle(stack, context.random)
        return stack
    }

    override fun getType(): LootItemFunctionType = EstrogenLootFunctions.ThighHighLoot

    class Serializer : LootItemConditionalFunction.Serializer<ThighHighStyleLootFunction?>() {
        override fun deserialize(
            jsonObject: JsonObject,
            deserializationContext: JsonDeserializationContext,
            conditions: Array<LootItemCondition>
        ): ThighHighStyleLootFunction {
            return ThighHighStyleLootFunction(conditions)
        }
    }

    companion object {
        fun apply(): Builder<*> = simpleBuilder(::ThighHighStyleLootFunction)
    }
}