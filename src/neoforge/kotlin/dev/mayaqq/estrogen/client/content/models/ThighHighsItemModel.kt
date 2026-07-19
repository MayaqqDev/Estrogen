package dev.mayaqq.estrogen.client.content.models

import dev.mayaqq.estrogen.content.EstrogenItems
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.block.model.ItemOverrides
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.RandomSource
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.state.BlockState

@Suppress("ACTUAL_WITHOUT_EXPECT")
actual class BakedThighHighsModel actual constructor(val default: BakedModel, val styleModels: Map<ResourceLocation, BakedModel>) : BakedModel {

    override fun getRenderPasses(itemStack: ItemStack, fabulous: Boolean): List<BakedModel> {
        return EstrogenItems.ThighHighs.get().getStyle(itemStack)?.let(styleModels::get)
            ?.let(::listOf)
            ?: default.getRenderPasses(itemStack, fabulous)
    }

    override fun getQuads(
        state: BlockState?,
        direction: Direction?,
        random: RandomSource
    ): List<BakedQuad?> = default.getQuads(state, direction, random)

    override fun useAmbientOcclusion(): Boolean = default.useAmbientOcclusion()

    override fun isGui3d(): Boolean = default.isGui3d

    override fun usesBlockLight(): Boolean = default.usesBlockLight()

    override fun isCustomRenderer(): Boolean = default.isCustomRenderer

    override fun getParticleIcon(): TextureAtlasSprite = default.particleIcon

    override fun getOverrides(): ItemOverrides = default.overrides
}