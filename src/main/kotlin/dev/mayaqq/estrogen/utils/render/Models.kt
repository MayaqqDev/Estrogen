package dev.mayaqq.estrogen.utils.render

import dev.mayaqq.estrogen.mixin.client.accessor.ModelManagerAccessor
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.client.resources.model.ModelManager
import net.minecraft.resources.ResourceLocation

fun ModelManager.getModel(id: ResourceLocation): BakedModel =
    (this as ModelManagerAccessor).bakedRegistry[id] ?: missingModel