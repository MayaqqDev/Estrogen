package dev.mayaqq.estrogen.client.content.models

import net.minecraft.client.renderer.texture.TextureAtlasSprite


data class ConnectionState(
    val up: Boolean,
    val down: Boolean,
    val left: Boolean,
    val right: Boolean,
    val topLeft: Boolean,
    val topRight: Boolean,
    val bottomLeft: Boolean,
    val bottomRight: Boolean
)


val ConnectionState.textureShift: Int get() {
    var tileX = 0
    var tileY = 0
    val borders =
        (if (!up) 1 else 0) + (if (!down) 1 else 0) + (if (!left) 1 else 0) + (if (!right) 1 else 0)

    if (up) tileX++
    if (down) tileX += 2
    if (left) tileY++
    if (right) tileY += 2

    if (borders == 0) {
        if (topRight) tileX++
        if (topLeft) tileX += 2
        if (bottomRight) tileY += 2
        if (bottomLeft) tileY++
    }

    if (borders == 1) {
        if (!right) {
            if (topLeft || bottomLeft) {
                tileY = 4
                tileX = -1 + (if (bottomLeft) 1 else 0) + (if (topLeft) 1 else 0) * 2
            }
        }
        if (!left) {
            if (topRight || bottomRight) {
                tileY = 5
                tileX = -1 + (if (bottomRight) 1 else 0) + (if (topRight) 1 else 0) * 2
            }
        }
        if (!down) {
            if (topLeft || topRight) {
                tileY = 6
                tileX = -1 + (if (topLeft) 1 else 0) + (if (topRight) 1 else 0) * 2
            }
        }
        if (!up) {
            if (bottomLeft || bottomRight) {
                tileY = 7
                tileX = -1 + (if (bottomLeft) 1 else 0) + (if (bottomRight) 1 else 0) * 2
            }
        }
    }

    if (borders == 2) {
        if ((up && left && topLeft) || (down && left && bottomLeft)
            || (up && right && topRight) || (down && right && bottomRight)
        ) tileX += 3
    }

    return tileX + 8 * tileY
}

fun getUnInterpolatedU(sprite: TextureAtlasSprite, u: Float): Float {
    val f = sprite.u1 - sprite.u0
    return (u - sprite.u0) / f * 16.0f
}

fun getUnInterpolatedV(sprite: TextureAtlasSprite, v: Float): Float {
    val f = sprite.v1 - sprite.v0
    return (v - sprite.v0) / f * 16.0f
}