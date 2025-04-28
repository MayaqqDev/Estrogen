package dev.mayaqq.estrogen.client.content.blockRenderers.dreamBlock

import dev.engine_room.flywheel.api.instance.Instance
import dev.engine_room.flywheel.api.visual.BlockEntityVisual
import dev.engine_room.flywheel.api.visualization.BlockEntityVisualizer
import dev.engine_room.flywheel.api.visualization.VisualizationContext
import dev.engine_room.flywheel.lib.visual.AbstractBlockEntityVisual
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.content.blockEntities.DreamBlockEntity
import net.minecraft.client.Minecraft
import java.util.function.Consumer

class DreamBlockVisual(
    ctx: VisualizationContext,
    blockEntity: DreamBlockEntity,
    partialTick: Float
) : AbstractBlockEntityVisual<DreamBlockEntity>(ctx, blockEntity, partialTick) {

    init {
        //TODO()
    }

    override fun _delete() {
        //TODO("Not yet implemented")
    }

    override fun collectCrumblingInstances(p0: Consumer<Instance?>) {
        //TODO("Not yet implemented")
    }

    override fun updateLight(p0: Float) {
       // TODO("Not yet implemented")
    }

    companion object Visualizer : BlockEntityVisualizer<DreamBlockEntity> {
        override fun createVisual(
            p0: VisualizationContext,
            p1: DreamBlockEntity,
            p2: Float
        ): BlockEntityVisual<in DreamBlockEntity> {
            return TODO()
            //if(Minecraft.getInstance().player?.hasEffect(EstrogenEffects.DREAMING) == true || )
        }

        override fun skipVanillaRender(p0: DreamBlockEntity): Boolean = true
    }
}