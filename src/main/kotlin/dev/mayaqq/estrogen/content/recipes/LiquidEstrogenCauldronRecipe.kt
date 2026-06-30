package dev.mayaqq.estrogen.content.recipes

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.teamresourceful.bytecodecs.base.ByteCodec
import com.teamresourceful.bytecodecs.base.`object`.ObjectByteCodec
import dev.mayaqq.estrogen.content.EstrogenFluids
import dev.mayaqq.estrogen.content.EstrogenRecipeSerializers
import dev.mayaqq.estrogen.content.EstrogenRecipes
import dev.mayaqq.estrogen.content.recipes.viewers.RecipeViewerInfo
import dev.mayaqq.estrogen.id
import net.minecraft.core.HolderLookup
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeInput
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level

class LiquidEstrogenCauldronRecipe(val enabled: Boolean) : Recipe<RecipeInput> {

    override fun getSerializer(): RecipeSerializer<*> = EstrogenRecipeSerializers.LIQUID_ESTROGEN_CAULDRON_SERIALIZER.value!!

    override fun getType(): RecipeType<*> = EstrogenRecipes.LIQUID_ESTROGEN_CAULDRON.value!!

    companion object : RecipeViewerInfo {
        val CODEC: Codec<LiquidEstrogenCauldronRecipe> = RecordCodecBuilder.create { instance ->
            instance.group(
                Codec.BOOL.fieldOf("enabled").forGetter(LiquidEstrogenCauldronRecipe::enabled)
            ).apply(instance, ::LiquidEstrogenCauldronRecipe)
        }

        val NET_CODEC: ByteCodec<LiquidEstrogenCauldronRecipe> = ObjectByteCodec.create(
            ByteCodec.BOOLEAN.fieldOf(LiquidEstrogenCauldronRecipe::enabled),
            ::LiquidEstrogenCauldronRecipe
        )

        override val display: ItemStack = Items.CAULDRON.defaultInstance
        override val catalyst: ItemStack get() = EstrogenFluids.FiltratedHorseUrine.bucket.defaultInstance
        override val id: ResourceLocation = id("liquid_estrogen_cauldron")
        override val height: Int = 70
        override val width: Int = 177
        override val type: RecipeType<*> get() = EstrogenRecipes.LIQUID_ESTROGEN_CAULDRON.value!!

    }

    override fun matches(container: RecipeInput, level: Level): Boolean = false
    override fun assemble(container: RecipeInput, registry: HolderLookup.Provider): ItemStack = EstrogenFluids.LiquidEstrogen.bucket.defaultInstance
    override fun canCraftInDimensions(x: Int, y: Int): Boolean = false
    override fun getResultItem(registry: HolderLookup.Provider): ItemStack = EstrogenFluids.LiquidEstrogen.bucket.defaultInstance
}