package dev.mayaqq.estrogen.utils.render

import dev.engine_room.flywheel.api.material.Material
import dev.engine_room.flywheel.api.material.Transparency
import dev.engine_room.flywheel.lib.material.Materials
import dev.engine_room.flywheel.lib.material.SimpleMaterial

object EstrogenMaterials {
    val ITEM_GLINT: Material = SimpleMaterial.builderOf(Materials.GLINT)
        .light(EstrogenFlywheelShaders.FULL_BRIGHT)
        .build()

    val PARTICLE_TRANSLUCENT_EMISSIVE: Material = SimpleMaterial.builderOf(Materials.TRANSLUCENT_BLOCK)
        .light(EstrogenFlywheelShaders.FULL_BRIGHT)
        .transparency(Transparency.TRANSLUCENT)
        .build()
}