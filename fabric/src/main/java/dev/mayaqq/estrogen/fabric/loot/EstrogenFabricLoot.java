package dev.mayaqq.estrogen.fabric.loot;

import dev.mayaqq.estrogen.registry.EstrogenItems;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;
import java.util.stream.Stream;

public class EstrogenFabricLoot {

    public static final List<ResourceLocation> LOOT_LOCATIONS = Stream.of(
        "minecraft:chests/woodland_mansion",
        "minecraft:chests/buried_treasure",
        "minecraft:chests/abandoned_mineshaft",
        "minecraft:chests/shipwreck_supply",
        "minecraft:chests/end_city",
        "minecraft:chests/ancient_city"
        ).map(ResourceLocation::new).toList();

    public static void onModifyLootTable(ResourceManager resourceManager, LootDataManager lootManager, ResourceLocation id, LootTable.Builder builder, LootTableSource source) {
        if(LOOT_LOCATIONS.contains(id)) {
            LootPool pool = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .conditionally(LootItemRandomChanceCondition.randomChance(0.3f).build())
                .with(LootItem.lootTableItem(EstrogenItems.THIGH_HIGHS.get())
                    .apply(ThighHighStyleLootFunction.apply())
                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f)))
                    .build())
                .build();

            builder.pool(pool);
        }
    }
}
