package dev.mayaqq.estrogen.content.recipes.data

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.Dynamic
import com.mojang.serialization.JsonOps
import com.teamresourceful.bytecodecs.base.ByteCodec
import dev.mayaqq.cynosure.core.bytecodecs.ByteCodecs
import dev.mayaqq.cynosure.core.bytecodecs.FriendlyByteCodec
import dev.mayaqq.cynosure.core.codecs.Codecs
import dev.mayaqq.cynosure.utils.Either
import dev.mayaqq.cynosure.utils.entityTypeTag
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.EntityType

object EntityTypeRecipeCodec : Codec<Either<EntityType<*>, TagKey<EntityType<*>>>> by Codecs.either(
    Codec.PASSTHROUGH.comapFlatMap(EntityTypeRecipeCodec::decodeEntityType, EntityTypeRecipeCodec::encodeEntityType),
    Codec.PASSTHROUGH.comapFlatMap(EntityTypeRecipeCodec::decodeEntityTag, EntityTypeRecipeCodec::encodeEntityTag)
) {

    @JvmField
    val NETWORK: ByteCodec<Either<EntityType<*>, TagKey<EntityType<*>>>> = ByteCodecs.either(
        FriendlyByteCodec(::encodeEntityTypeToNetwork, ::decodeEntityTypeFromNetwork),
        FriendlyByteCodec(::encodeEntityTagToNetwork, ::decodeEntityTagFromNetwork)
    )

    private fun encodeEntityTypeToNetwork(entity: EntityType<*>, buf: FriendlyByteBuf) {
        buf.writeResourceLocation(BuiltInRegistries.ENTITY_TYPE.getKey(entity))
    }

    private fun encodeEntityTagToNetwork(tag: TagKey<EntityType<*>>, buf: FriendlyByteBuf) {
        buf.writeResourceLocation(tag.location())
    }

    private fun decodeEntityTagFromNetwork(buf: FriendlyByteBuf): TagKey<EntityType<*>> {
        return entityTypeTag(buf.readResourceLocation())
    }

    private fun decodeEntityTypeFromNetwork(buf: FriendlyByteBuf): EntityType<*> {
        return BuiltInRegistries.ENTITY_TYPE.get(buf.readResourceLocation())
    }

    private fun decodeEntityType(dynamic: Dynamic<*>): DataResult<EntityType<*>> {
        val json = dynamic.convert(JsonOps.INSTANCE).value
        return if (json is JsonObject) {
            if (json.has("entity")) {
                DataResult.success(BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation(json["entity"].asString)))
            } else {
                DataResult.error { "Invalid JSON: Expected 'entity' or 'tag'" }
            }
        } else {
            DataResult.error { "Invalid JSON format" }
        }
    }

    private fun decodeEntityTag(dynamic: Dynamic<*>): DataResult<TagKey<EntityType<*>>> {
        val json = dynamic.convert(JsonOps.INSTANCE).value
        return if (json is JsonObject) {
            if (json.has("tag")) {
                DataResult.success(entityTypeTag(ResourceLocation(json["tag"].asString)))
            } else {
                return DataResult.error { "Invalid JSON: Expected 'entity' or 'tag'" }
            }
        } else {
            return DataResult.error { "Invalid JSON: Expected 'entity' or 'tag'" }
        }
    }

    private fun encodeEntityType(entity: EntityType<*>): Dynamic<JsonElement> {
        val json = JsonObject()
        json.addProperty("entity", BuiltInRegistries.ENTITY_TYPE.getKey(entity).toString())
        return Dynamic(JsonOps.INSTANCE, json)
    }

    private fun encodeEntityTag(tag: TagKey<EntityType<*>>): Dynamic<JsonElement> {
        val json = JsonObject()
        json.addProperty("tag", tag.location().toString())
        return Dynamic(JsonOps.INSTANCE, json)
    }
}