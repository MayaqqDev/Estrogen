package dev.mayaqq.estrogen.registry.recipes.objects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teamresourceful.bytecodecs.utils.Either;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class EntityObject {

    public final Either<EntityType<?>, TagKey<EntityType<?>>> entity;

    public EntityObject(Either<EntityType<?>, TagKey<EntityType<?>>> entity) {
        this.entity = entity;
    }

    public static EntityObject of(EntityType<?> entity) {
        return new EntityObject(Either.ofLeft(entity));
    }

    public static EntityObject of(TagKey<EntityType<?>> entity) {
        return new EntityObject(Either.ofRight(entity));
    }

    public static EntityObject fromJson(JsonElement jsonElement) {
        JsonObject object = jsonElement.getAsJsonObject();
        if (object.has("entity")) {
            JsonElement entity = object.get("entity");
            String entityName = entity.getAsString();
            return new EntityObject(Either.ofLeft(BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation(entityName))));
        } else if (object.has("tag")) {
            JsonElement tag = object.get("tag");
            String tagName = tag.getAsString().substring(1);
            return new EntityObject(Either.ofRight(TagKey.create(BuiltInRegistries.ENTITY_TYPE.key(), new ResourceLocation(tagName))));
        }
        return null;
    }

    public static JsonElement toJson(EntityObject entityObject) {
        JsonObject object = new JsonObject();
        if (entityObject.entity.isLeft()) {
            object.addProperty("entity", BuiltInRegistries.ENTITY_TYPE.getKey(entityObject.entity.left().get()).toString());
            return object;
        } else {
            object.addProperty("tag", "#" + entityObject.entity.right().get().location());
        }
        return object;
    }

    public static EntityObject fromNetwork(FriendlyByteBuf buf) {
        if (buf.readBoolean()) {
            return new EntityObject(Either.ofLeft(BuiltInRegistries.ENTITY_TYPE.get(buf.readResourceLocation())));
        } else {
            return new EntityObject(Either.ofRight(TagKey.create(BuiltInRegistries.ENTITY_TYPE.key(), buf.readResourceLocation())));
        }
    }

    public void toNetwork(FriendlyByteBuf buf) {
        if (entity.isLeft()) {
            buf.writeBoolean(true);
            buf.writeResourceLocation(BuiltInRegistries.ENTITY_TYPE.getKey(entity.left().get()));
        } else {
            buf.writeBoolean(false);
            buf.writeResourceLocation(entity.right().get().location());
        }
    }

    public boolean matches(EntityType<?> entity) {
        if (this.entity.isLeft()) {
            return this.entity.left().get().equals(entity);
        } else {
            return entity.is(this.entity.right().get());
        }
    }
}
