package dev.mayaqq.estrogen.client.content.blockRenderers.cookieJar

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import dev.engine_room.flywheel.api.instance.Instance
import dev.engine_room.flywheel.api.visualization.VisualizationContext
import dev.engine_room.flywheel.lib.instance.InstanceTypes
import dev.engine_room.flywheel.lib.instance.TransformedInstance
import dev.engine_room.flywheel.lib.visual.AbstractBlockEntityVisual
import dev.mayaqq.estrogen.content.blockEntities.CookieJarBlockEntity
import dev.mayaqq.estrogen.utils.render.ItemModel
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import java.util.function.Consumer

class CookieJarVisual(
    ctx: VisualizationContext,
    blockEntity: CookieJarBlockEntity,
    partialTick: Float
) : AbstractBlockEntityVisual<CookieJarBlockEntity>(ctx, blockEntity, partialTick) {

    private val instances: MutableList<TransformedInstance> = mutableListOf()
    private val poseStack: PoseStack = PoseStack()

    init {
        reloadInstances()
    }

    override fun update(partialTick: Float) {
        reloadInstances()
    }

    private fun reloadInstances() {
        poseStack.pushPose()
        poseStack.mulPose(Axis.XN.rotationDegrees(90f))
        poseStack.translate(0.5, -0.625, 0.032)

        var index = -2
        for (jarItem in blockEntity.items) {
            index += 2
            val hasInstances = index < instances.size - 1

            if (jarItem.isEmpty) {
                if (hasInstances) {
                    instances.removeAt(index + 1).delete()
                    instances.removeAt(index).delete()
                }
                continue
            }

            poseStack.translate(-0.025, -0.025, 0.032)
            if (!hasInstances) createItemInstance(jarItem)
            instances[index].setTransform(poseStack)
            poseStack.translate(0.05, 0.05, 0.032)
            if (!hasInstances) createItemInstance(jarItem)
            instances[index + 1].setTransform(poseStack)
            poseStack.translate(-0.025, -0.025, 0.0)
        }

        poseStack.popPose()
    }

    private fun createItemInstance(jarItem: ItemStack) {
        visualizationContext.instancerProvider()
            .instancer(InstanceTypes.TRANSFORMED, ItemModel(jarItem, ItemDisplayContext.GROUND))
            .createInstance()
            .let(instances::add)
    }

    override fun _delete() {
        instances.forEach { it.delete() }
    }

    override fun collectCrumblingInstances(p0: Consumer<Instance?>) {
        instances.forEach { p0.accept(it) }
    }

    override fun updateLight(p0: Float) {
        relight(instances)
    }
}