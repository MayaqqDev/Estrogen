package dev.mayaqq.estrogen.content.recipes

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.teamresourceful.bytecodecs.base.ByteCodec
import com.teamresourceful.bytecodecs.base.`object`.ObjectByteCodec
import dev.mayaqq.cynosure.core.bytecodecs.ByteCodecs
import dev.mayaqq.estrogen.content.EstrogenFluids
import dev.mayaqq.estrogen.content.EstrogenItems
import dev.mayaqq.estrogen.content.EstrogenRecipes
import dev.mayaqq.estrogen.content.recipes.viewers.RecipeViewerInfo
import dev.mayaqq.estrogen.id
import net.minecraft.core.RegistryAccess
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level

class LiquidEstrogenCauldronRecipe(val recipeId: ResourceLocation, val enabled: Boolean) : Recipe<Container> {
    override fun getId(): ResourceLocation = recipeId

    override fun getSerializer(): RecipeSerializer<*> = EstrogenRecipes.Serializers.LIQUID_ESTROGEN_CAULDRON_SERIALIZER

    override fun getType(): RecipeType<*> = EstrogenRecipes.LIQUID_ESTROGEN_CAULDRON

    companion object : RecipeViewerInfo {
        fun codec(id: ResourceLocation): Codec<LiquidEstrogenCauldronRecipe> = RecordCodecBuilder.create { instance ->
            instance.group(
                RecordCodecBuilder.point(id),
                Codec.BOOL.fieldOf("enabled").forGetter(LiquidEstrogenCauldronRecipe::enabled)
            ).apply(instance, ::LiquidEstrogenCauldronRecipe)
        }

        fun netcodec(id: ResourceLocation): ByteCodec<LiquidEstrogenCauldronRecipe> = ObjectByteCodec.create(
            ByteCodecs.constantFieldOf(id),
            ByteCodec.BOOLEAN.fieldOf(LiquidEstrogenCauldronRecipe::enabled),
            ::LiquidEstrogenCauldronRecipe
        )

        override val display: ItemStack = Items.CAULDRON.defaultInstance
        override val catalyst: ItemStack get() = EstrogenFluids.FiltratedHorseUrine.bucket.defaultInstance
        override val id: ResourceLocation = id("liquid_estrogen_cauldron")
        override val height: Int = 70
        override val width: Int = 177
        override val type: RecipeType<*> get() = EstrogenRecipes.LIQUID_ESTROGEN_CAULDRON

    }

    override fun matches(container: Container, level: Level): Boolean = false
    override fun assemble(container: Container, registry: RegistryAccess): ItemStack = EstrogenFluids.LiquidEstrogen.bucket.defaultInstance
    override fun canCraftInDimensions(x: Int, y: Int): Boolean = false
    override fun getResultItem(registry: RegistryAccess): ItemStack = EstrogenFluids.LiquidEstrogen.bucket.defaultInstance
}