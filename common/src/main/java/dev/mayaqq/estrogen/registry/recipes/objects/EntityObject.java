package dev.mayaqq.estrogen.registry.recipes.objects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teamresourceful.bytecodecs.utils.Either;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;

import java.util.ArrayList;

public record EntityObject(Either<EntityType<?>, TagKey<EntityType<?>>> entity) {

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
            String tagName = tag.getAsString();
            return new EntityObject(Either.ofRight(TagKey.create(BuiltInRegistries.ENTITY_TYPE.key(), new ResourceLocation(tagName))));
        }
        throw new IllegalArgumentException("Invalid entity object json");
    }

    public static JsonElement toJson(EntityObject entityObject) {
        JsonObject object = new JsonObject();
        if (entityObject.entity.isLeft()) {
            object.addProperty("entity", BuiltInRegistries.ENTITY_TYPE.getKey(entityObject.entity.left().get()).toString());
            return object;
        } else {
            object.addProperty("tag", entityObject.entity.right().get().location().toString());
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

    static int cycleCounter = 0;
    static int cycleAmount = 0;
    static EntityType<?> currentEntity = null;

    public EntityType<?> getCycling() {

        if (entity.isLeft()) {
            return entity.left().get();
        } else {
            if (cycleCounter == 0) {
                ArrayList<EntityType<?>> entityTypes = getEntityTypes();
                currentEntity = entityTypes.get(0);
            }
            if (cycleCounter >= 100) {
                cycleCounter = 1;
                currentEntity = getEntityTypes().get(cycleAmount);

            }
            cycleAmount++;
            if (cycleAmount >= getEntityTypes().size()) {
                cycleAmount = 0;
            }
            cycleCounter++;
            return currentEntity;
        }
    }

    public ItemStack[] spawnEggs() {
        ArrayList<EntityType<?>> entityTypes = getEntityTypes();
        ItemStack[] spawnEggs = new ItemStack[entityTypes.size()];
        for (int i = 0; i < entityTypes.size(); i++) {
            spawnEggs[i] = SpawnEggItem.byId(entityTypes.get(i)).getDefaultInstance();
        }
        return spawnEggs;
    }

    private ArrayList<EntityType<?>> getEntityTypes() {
        ArrayList<EntityType<?>> entityTypes = new ArrayList<>();
        BuiltInRegistries.ENTITY_TYPE.getTagOrEmpty(entity.right().get()).forEach(entityTypeHolder -> {
            com.mojang.datafixers.util.Either<ResourceKey<EntityType<?>>, EntityType<?>> either = entityTypeHolder.unwrap();
            if (either.left().isPresent()) {
                entityTypes.add(BuiltInRegistries.ENTITY_TYPE.get(either.left().get()));
            } else {
                entityTypes.add(either.right().get());
            }
        });
        return entityTypes;
    }
}
