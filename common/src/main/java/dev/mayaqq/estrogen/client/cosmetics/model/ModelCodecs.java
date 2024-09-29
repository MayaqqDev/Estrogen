package dev.mayaqq.estrogen.client.cosmetics.model;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Keyable;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.floats.FloatList;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockElementRotation;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.core.Direction;

import static com.mojang.serialization.Codec.*;
import static net.minecraft.util.ExtraCodecs.*;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public class ModelCodecs {

    private static final Codec<float[]> BASIC_UVS = Codec.FLOAT.listOf()
        .xmap(list -> new float[] {list.get(0), list.get(1), list.get(2), list.get(3)}, FloatList::of);

    public static final Codec<BlockFaceUV> UV = Codec.either(
        BASIC_UVS.xmap(uvs -> new BlockFaceUV(uvs, 0), uv -> uv.uvs),
        RecordCodecBuilder.<BlockFaceUV>create(instance -> instance.group(
            BASIC_UVS.fieldOf("uvs").forGetter(uv -> uv.uvs),
            INT.fieldOf("rotation").forGetter(uv -> uv.rotation)
        ).apply(instance, BlockFaceUV::new))
    ).xmap(
        either -> either.map(Function.identity(), Function.identity()),
        uv -> uv.rotation == 0 ? Either.left(uv) : Either.right(uv)
    );

    public static final Codec<BlockElementRotation> ROTATION = RecordCodecBuilder.create(instance -> instance.group(
        FLOAT.fieldOf("angle").forGetter(BlockElementRotation::angle),
        Direction.Axis.CODEC.fieldOf("axis").forGetter(BlockElementRotation::axis),
        VECTOR3F.fieldOf("origin").forGetter(BlockElementRotation::origin),
        BOOL.optionalFieldOf("rescale", false).forGetter(BlockElementRotation::rescale)
    ).apply(instance, (angle, axis, origin, rescale) -> new BlockElementRotation(origin, axis, angle, rescale)));

    public static final Codec<BlockElementFace> FACE = RecordCodecBuilder.create(instance -> instance.group(
        UV.fieldOf("uv").forGetter(elem -> elem.uv),
        STRING.fieldOf("texture").forGetter(elem -> elem.texture)
    ).apply(instance, (uv, texture) -> new BlockElementFace(null, -1, texture, uv)));

    public static final Codec<BlockElement> ELEMENT = RecordCodecBuilder.create(instance -> instance.group(
        VECTOR3F.fieldOf("from").forGetter(elem -> elem.from),
        VECTOR3F.fieldOf("to").forGetter(elem -> elem.to),
        simpleMap(Direction.CODEC, FACE, Keyable.forStrings(() -> Arrays.stream(Direction.values()).map(Direction::getName)))
            .fieldOf("faces").forGetter(el -> el.faces),
        ROTATION.optionalFieldOf("rotation", null).forGetter(el -> el.rotation),
        BOOL.optionalFieldOf("shade", true).forGetter(el -> el.shade)
    ).apply(instance, BlockElement::new));

    public static final Codec<BlockElementGroup> GROUP = RecordCodecBuilder.create(instance -> instance.group(
        STRING.fieldOf("name").forGetter(BlockElementGroup::name),
        VECTOR3F.fieldOf("origin").forGetter(BlockElementGroup::origin),
        // This codec has to be lazy-initialized to avoid self referencing issues
        lazyInitializedCodec(() -> either(INT, ModelCodecs.GROUP).listOf())
            .fieldOf("children").forGetter(BlockElementGroup::encodeData)
    ).apply(instance, BlockElementGroup::of));

}
