package dev.mayaqq.estrogen.utils.render

import dev.engine_room.flywheel.api.material.LightShader
import dev.engine_room.flywheel.lib.material.SimpleLightShader
import dev.mayaqq.estrogen.id

public object EstrogenFlywheelShaders {
    val FULL_BRIGHT: LightShader = SimpleLightShader(id("light/fullbright.glsl"))
}

