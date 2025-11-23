package dev.mayaqq.estrogen.fabric.client.models

import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.client.content.block.ClientDreamBlock
import dev.mayaqq.estrogen.client.content.models.getUnInterpolatedU
import dev.mayaqq.estrogen.client.content.models.getUnInterpolatedV
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel
import net.fabricmc.fabric.api.renderer.v1.model.SpriteFinder
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.core.BlockPos
import net.minecraft.util.RandomSource
import net.minecraft.world.inventory.InventoryMenu
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.block.state.BlockState
import java.util.function.Supplier

internal class FabricConnectedModel(
    wrapped: BakedModel,
    private val connectedSprite: TextureAtlasSprite
) : ForwardingBakedModel() {

    init {
        this.wrapped = wrapped
    }

    override fun isVanillaAdapter(): Boolean = false

    override fun emitBlockQuads(
        blockView: BlockAndTintGetter,
        state: BlockState,
        pos: BlockPos,
        randomSupplier: Supplier<RandomSource>,
        context: RenderContext
    ) {
        val spriteFinder = SpriteFinder.get(McClient.modelManager.getAtlas(InventoryMenu.BLOCK_ATLAS))
        context.pushTransform { quad ->
            val original = spriteFinder.find(quad)
            val index = ClientDreamBlock.getConnectionForFace(blockView, pos, state, quad.lightFace())
            val uOffset = index % 8
            val vOffset = index / 8

            for (vertex in 0..<4) {
                val u = getUnInterpolatedU(original, quad.u(vertex))
                val v = getUnInterpolatedV(original, quad.v(vertex))
                quad.uv(
                    vertex,
                    connectedSprite.getU((u + (uOffset * 16)) / 8.0),
                    connectedSprite.getV((v + (vOffset * 16)) / 8.0)
                )
            }

            true
        }
        super.emitBlockQuads(blockView, state, pos, randomSupplier, context)
        context.popTransform()
    }
}
