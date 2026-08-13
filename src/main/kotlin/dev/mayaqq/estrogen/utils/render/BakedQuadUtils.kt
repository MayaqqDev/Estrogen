package dev.mayaqq.estrogen.utils.render

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import net.minecraft.client.renderer.block.model.BakedQuad


private val FORMAT: VertexFormat = DefaultVertexFormat.BLOCK
private val VERTEX_STRIDE: Int = FORMAT.vertexSize / 4

private const val X_OFFSET = 0
private const val Y_OFFSET = 1
private const val Z_OFFSET = 2
private const val COLOR_OFFSET = 3
private const val U_OFFSET = 4
private const val V_OFFSET = 5
private const val LIGHT_OFFSET = 6
private const val NORMAL_OFFSET = 7

fun BakedQuad.getU(vertex: Int): Float = Float.fromBits(vertices[vertex * VERTEX_STRIDE + U_OFFSET])

fun BakedQuad.setU(vertex: Int, value: Float) {
    vertices[vertex * VERTEX_STRIDE + U_OFFSET] = value.toBits()
}

fun BakedQuad.getV(vertex: Int): Float = Float.fromBits(vertices[vertex * VERTEX_STRIDE + V_OFFSET])

fun BakedQuad.setV(vertex: Int, value: Float) {
    vertices[vertex * VERTEX_STRIDE + V_OFFSET] = value.toBits()
}

fun BakedQuad.copy(): BakedQuad = BakedQuad(vertices.copyOf(), tintIndex, direction, sprite, isShade)

