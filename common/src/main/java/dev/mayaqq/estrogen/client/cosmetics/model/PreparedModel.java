package dev.mayaqq.estrogen.client.cosmetics.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.block.model.BlockElement;

import java.util.List;

public record PreparedModel(List<BlockElement> elements, List<BlockElementGroup> groups) {

    public static final Codec<PreparedModel> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ModelCodecs.ELEMENT.listOf().fieldOf("elements").forGetter(PreparedModel::elements),
        ModelCodecs.GROUP.listOf().optionalFieldOf("groups", List.of()).forGetter(PreparedModel::groups)
    ).apply(instance, PreparedModel::new));


    public boolean hasGroups() {
        return !groups.isEmpty();
    }
}
