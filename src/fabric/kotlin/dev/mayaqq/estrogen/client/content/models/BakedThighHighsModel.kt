package dev.mayaqq.estrogen.client.content.models

import dev.mayaqq.estrogen.content.EstrogenItems
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.RandomSource
import net.minecraft.world.item.ItemStack
import java.util.function.Supplier

actual class BakedThighHighsModel actual constructor(default: BakedModel, val styleModels: Map<ResourceLocation, BakedModel>) : BakedModel, ForwardingBakedModel() {

    init {
        wrapped = default
    }

    override fun isVanillaAdapter(): Boolean = false

    override fun emitItemQuads(
        stack: ItemStack,
        randomSupplier: Supplier<RandomSource>,
        context: RenderContext
    ) {
        val model = EstrogenItems.ThighHighs.value!!.getStyle(stack)?.let(styleModels::get) ?: wrapped
        (model as FabricBakedModel).emitItemQuads(stack, randomSupplier, context)
    }
}