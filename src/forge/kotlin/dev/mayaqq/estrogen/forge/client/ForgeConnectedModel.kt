package dev.mayaqq.estrogen.forge.client

import dev.mayaqq.estrogen.client.content.block.ClientDreamBlock
import dev.mayaqq.estrogen.client.content.models.getUnInterpolatedU
import dev.mayaqq.estrogen.client.content.models.getUnInterpolatedV
import dev.mayaqq.estrogen.utils.render.*
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.client.resources.model.Material
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.RandomSource
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.client.model.BakedModelWrapper
import net.minecraftforge.client.model.data.ModelData
import net.minecraftforge.client.model.data.ModelProperty

class ForgeConnectedModel(
    originalModel: BakedModel,
    connectedMaterial: Material
) : BakedModelWrapper<BakedModel>(originalModel) {

    private val connectedSprite: TextureAtlasSprite by lazy(LazyThreadSafetyMode.PUBLICATION, connectedMaterial::sprite)

    companion object {
        val CONNECTION: ModelProperty<IntArray> = ModelProperty()
    }

    override fun getModelData(
        level: BlockAndTintGetter,
        pos: BlockPos,
        state: BlockState,
        modelData: ModelData
    ): ModelData {
        return ModelData.builder().apply {
            val array = IntArray(6)
            for (face in Direction.entries) {
                array[face.get3DDataValue()] = ClientDreamBlock.getConnectionForFace(level, pos, state, face)
            }
            with(CONNECTION, array)
        }.build()
    }

    override fun getQuads(
        state: BlockState?,
        side: Direction?,
        rand: RandomSource,
        extraData: ModelData,
        renderType: RenderType?
    ): List<BakedQuad> {
        val quads = super.getQuads(state, side, rand, extraData, renderType)
        val data = extraData[CONNECTION] ?: return quads

        return quads.map { quad ->
            val index = data[quad.direction.get3DDataValue()]
            if (index == -1) return@map quad

            val uOffset = index % 8
            val vOffset = index / 8
            val newQuad = quad.copy()

            for (vertex in 0..3) {
                val u = getUnInterpolatedU(quad.sprite, quad.getU(vertex))
                val v = getUnInterpolatedV(quad.sprite, quad.getV(vertex))
                newQuad.setU(vertex, connectedSprite.getU((u + (uOffset * 16)) / 8.0))
                newQuad.setV(vertex, connectedSprite.getV((v + (vOffset * 16)) / 8.0))
            }

            newQuad
        }
    }
}