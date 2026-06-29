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
import dev.mayaqq.cynosure.core.identifier
import dev.mayaqq.cynosure.utils.fluidTag
import invoke.kitty.kritter.utils.Either
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.tags.TagKey
import net.minecraft.world.level.material.Fluid

object FluidRecipeCodec : Codec<Either<Fluid, TagKey<Fluid>>> by Codecs.either(
    Codecs.lazy({Codec.PASSTHROUGH.comapFlatMap(FluidRecipeCodec::decodeFluidBlock, FluidRecipeCodec::encodeFluidBlock)}),
    Codecs.lazy({Codec.PASSTHROUGH.comapFlatMap(FluidRecipeCodec::decodeFluidTag, FluidRecipeCodec::encodeFluidTag)})
) {

    @JvmField
    val NETWORK: ByteCodec<Either<Fluid, TagKey<Fluid>>> = ByteCodecs.either(
        FriendlyByteCodec(::encodeFluidBlockToNetwork, ::decodeFluidBlockFromNetwork),
        FriendlyByteCodec(::encodeFluidTagToNetwork, ::decodeFluidTagFromNetwork)
    )

    private fun encodeFluidBlockToNetwork(block: Fluid, buf: FriendlyByteBuf) {
        buf.writeResourceLocation(BuiltInRegistries.FLUID.getKey(block))
    }

    private fun encodeFluidTagToNetwork(tag: TagKey<Fluid>, buf: FriendlyByteBuf) {
        buf.writeResourceLocation(tag.location())
    }

    private fun decodeFluidTagFromNetwork(buf: FriendlyByteBuf): TagKey<Fluid> {
        return fluidTag(buf.readResourceLocation())
    }

    private fun decodeFluidBlockFromNetwork(buf: FriendlyByteBuf): Fluid {
        return BuiltInRegistries.FLUID.get(buf.readResourceLocation())
    }

    private fun decodeFluidBlock(dynamic: Dynamic<*>): DataResult<Fluid> {
        val json = dynamic.convert(JsonOps.INSTANCE).value
        return if (json is JsonObject) {
            if (json.has("fluid")) {
                DataResult.success(BuiltInRegistries.FLUID.get(identifier(json["fluid"].asString)))
            } else {
                DataResult.error { "Invalid JSON: Expected 'fluid' or 'tag'" }
            }
        } else {
            DataResult.error { "Invalid JSON format" }
        }
    }

    private fun decodeFluidTag(dynamic: Dynamic<*>): DataResult<TagKey<Fluid>> {
        val json = dynamic.convert(JsonOps.INSTANCE).value
        return if (json is JsonObject) {
            if (json.has("tag")) {
                DataResult.success(fluidTag(identifier(json["tag"].asString)))
            } else {
                DataResult.error { "Invalid JSON: Expected 'fluid' or 'tag'" }
            }
        } else {
            return DataResult.error { "Invalid JSON: Expected 'fluid' or 'tag'" }
        }
    }

    private fun encodeFluidBlock(fluid: Fluid): Dynamic<JsonElement> {
        val json = JsonObject()
        json.addProperty("fluid", BuiltInRegistries.FLUID.getKey(fluid).toString())
        return Dynamic(JsonOps.INSTANCE, json)
    }

    private fun encodeFluidTag(tag: TagKey<Fluid>): Dynamic<JsonElement> {
        val json = JsonObject()
        json.addProperty("tag", tag.location().toString())
        return Dynamic(JsonOps.INSTANCE, json)
    }
}