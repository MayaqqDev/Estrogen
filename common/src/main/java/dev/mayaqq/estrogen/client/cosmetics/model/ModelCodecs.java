package dev.mayaqq.estrogen.client.cosmetics.model;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.mayaqq.estrogen.utils.EstrogenCodecs;
import it.unimi.dsi.fastutil.floats.FloatList;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockElementRotation;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.core.Direction;

import java.util.function.Function;

import static com.mojang.serialization.Codec.*;
import static net.minecraft.util.ExtraCodecs.*;

public class ModelCodecs {

    public static final Codec<float[]> BASIC_UVS = Codec.FLOAT.listOf()
        .xmap(list -> new float[] {list.get(0), list.get(1), list.get(2), list.get(3)}, FloatList::of);

    public static final Codec<Integer> UV_ROTATION = INT.comapFlatMap(ModelCodecs::validateUVRot, Function.identity());

    public static final Codec<BlockElementRotation> ROTATION = RecordCodecBuilder.create(instance -> instance.group(
        FLOAT.fieldOf("angle").forGetter(BlockElementRotation::angle),
        Direction.Axis.CODEC.fieldOf("axis").forGetter(BlockElementRotation::axis),
        VECTOR3F.fieldOf("origin").forGetter(BlockElementRotation::origin),
        BOOL.optionalFieldOf("rescale", false).forGetter(BlockElementRotation::rescale)
    ).apply(instance, (angle, axis, origin, rescale) -> new BlockElementRotation(origin, axis, angle, rescale)));

    public static final Codec<BlockElementFace> FACE = RecordCodecBuilder.create(instance -> instance.group(
        BASIC_UVS.fieldOf("uv").forGetter(elem -> elem.uv.uvs),
        UV_ROTATION.optionalFieldOf("rotation", 0).forGetter(elem -> elem.uv.rotation),
        STRING.optionalFieldOf("texture", "").forGetter(elem -> elem.texture)
    ).apply(instance, (uv, rotation, texture) ->
        new BlockElementFace(null, -1, texture, new BlockFaceUV(uv, rotation))
    ));

    public static final Codec<BlockElement> ELEMENT = RecordCodecBuilder.create(instance -> instance.group(
        VECTOR3F.fieldOf("from").forGetter(elem -> elem.from),
        VECTOR3F.fieldOf("to").forGetter(elem -> elem.to),
        simpleMap(Direction.CODEC, FACE, EstrogenCodecs.arrayKeyable(Direction::values)).fieldOf("faces").forGetter(elem -> elem.faces),
        ROTATION.optionalFieldOf("rotation", null).forGetter(elem -> elem.rotation),
        BOOL.optionalFieldOf("shade", true).forGetter(elem -> elem.shade)
    ).apply(instance, BlockElement::new));

    public static final Codec<BlockElementGroup> GROUP = EstrogenCodecs.recursive("element_group", codec ->
        RecordCodecBuilder.create(instance -> instance.group(
            STRING.fieldOf("name").forGetter(BlockElementGroup::name),
            VECTOR3F.fieldOf("origin").forGetter(BlockElementGroup::origin),
            either(INT, codec).listOf().fieldOf("children").forGetter(BlockElementGroup::toCombinedList)
        ).apply(instance, BlockElementGroup::fromCombinedList))
    );

    private static DataResult<Integer> validateUVRot(Integer uvRot) {
        return switch (uvRot) {
            case 0, 90, 180, 270 -> DataResult.success(uvRot);
            default -> DataResult.error(() -> "Invalid uv rotation");
        };
    }

}
