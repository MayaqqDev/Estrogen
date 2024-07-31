package dev.mayaqq.estrogen.fabric.datagen.recipes;

import com.google.gson.JsonArray;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public interface EstrogenRecipeInterface {
    long getAmount(long amount);

    String getName(String name);

    EstrogenLoadCondition isModLoaded(String modid);

    public record EstrogenLoadCondition(String name, JsonArray condition) {}

    public TagKey<Item> getCommonTag(String name);
}
