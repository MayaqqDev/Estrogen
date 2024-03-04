package dev.mayaqq.estrogen.registry.recipes.codecs;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import com.teamresourceful.resourcefullib.common.exceptions.UtilityClassException;
import dev.mayaqq.estrogen.registry.recipes.objects.EntityObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.List;

public class EntityObjectCodec {
    public static final Codec<EntityObject> CODEC = Codec.PASSTHROUGH.comapFlatMap(EntityObjectCodec::decodeEntityObject, EntityObjectCodec::encodeEntityObject);
    public static final Codec<EntityObject> NETWORK_CODEC = Codec.BYTE.listOf().flatXmap(EntityObjectCodec::decodeEntityObjectFromNetwork, EntityObjectCodec::encodeEntityObjectToNetwork);

    private EntityObjectCodec() throws UtilityClassException {
        throw new UtilityClassException();
    }

    private static DataResult<EntityObject> decodeEntityObject(Dynamic<?> dynamic) {
        Object object = dynamic.convert(JsonOps.INSTANCE).getValue();
        if (object instanceof JsonElement jsonElement) {
            return DataResult.success(EntityObject.fromJson(jsonElement));
        }
        return DataResult.error(() -> "Value was not an instance of JsonElement");
    }

    private static Dynamic<JsonElement> encodeEntityObject(EntityObject entityObject) {
        return new Dynamic<>(JsonOps.INSTANCE, EntityObject.toJson(entityObject)).convert(JsonOps.COMPRESSED);
    }

    private static DataResult<EntityObject> decodeEntityObjectFromNetwork(List<Byte> data) {
        try {
            byte[] array = new byte[data.size()];
            for (int i = 0; i < data.size(); i++) {
                array[i] = data.get(i);
            }
            ByteBuf buffer = Unpooled.wrappedBuffer(array);
            return DataResult.success(EntityObject.fromNetwork(new FriendlyByteBuf(buffer)));
        }catch (Exception e){
            return DataResult.error(() -> "Failed to decode ingredient from network: " + e.getMessage());
        }
    }

    private static DataResult<List<Byte>> encodeEntityObjectToNetwork(EntityObject entity) {
        try {
            ByteBuf buffer = Unpooled.buffer();
            entity.toNetwork(new FriendlyByteBuf(buffer));
            byte[] array = buffer.array();
            List<Byte> bytes = new ArrayList<>(array.length);
            for (byte b : array) {
                bytes.add(b);
            }
            return DataResult.success(bytes);
        }catch (Exception e){
            return DataResult.error(() -> "Failed to encode ingredient to network: " + e.getMessage());
        }
    }
}
