package dev.mayaqq.estrogen.content.recipes.data

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.Dynamic
import com.mojang.serialization.JsonOps
import com.mojang.datafixers.util.Either
import com.teamresourceful.bytecodecs.base.ByteCodec
import dev.mayaqq.cynosure.core.bytecodecs.FriendlyByteCodec
import dev.mayaqq.cynosure.utils.entityTypeTag
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.crafting.Ingredient

object EntityTypeRecipeCodec : Codec<Either<EntityType<*>, TagKey<EntityType<*>>>> by Codec.PASSTHROUGH.comapFlatMap(
    EntityTypeRecipeCodec::decodeEntityType, EntityTypeRecipeCodec::encodeEntityType
) {

    @JvmField
    val NETWORK: ByteCodec<Either<EntityType<*>, TagKey<EntityType<*>>>> = FriendlyByteCodec( ::encodeToNetwork, ::decodeFromNetwork)

    private fun encodeToNetwork(entity: Either<EntityType<*>, TagKey<EntityType<*>>>, buf: FriendlyByteBuf) {
        //TODO Penis
    }

    private fun decodeFromNetwork(buf: FriendlyByteBuf): Either<EntityType<*>, TagKey<EntityType<*>>> {
        //TODO Penis
    }

    private fun decodeEntityType(dynamic: Dynamic<*>): DataResult<Either<EntityType<*>, TagKey<EntityType<*>>>> {
        val json = dynamic.convert(JsonOps.INSTANCE).value
        return if (json is JsonObject) {
            if (json.has("entity")) {
                DataResult.success(Either.left(BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation(json["entity"].asString))))
            } else if (json.has("tag")) {
                DataResult.success(Either.right(entityTypeTag(ResourceLocation(json["tag"].asString))))
            } else {
                DataResult.error { "Invalid JSON: Expected 'entity' or 'tag'" }
            }
        } else {
            DataResult.error { "Invalid JSON format" }
        }
    }

    private fun encodeEntityType(entity: Either<EntityType<*>, TagKey<EntityType<*>>>): Dynamic<JsonElement> {
        val json = JsonObject()
        entity.ifLeft {
            json.addProperty("entity", BuiltInRegistries.ENTITY_TYPE.getId(entity.left().get()))
        }
        entity.ifRight {
            json.addProperty("tag", entity.right().get().location().toString())
        }
        return Dynamic(JsonOps.INSTANCE, json)
    }
}