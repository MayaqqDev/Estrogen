package dev.mayaqq.estrogen.client.content.entityRenderers.boobs

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartPose
import net.minecraft.core.Direction
import org.joml.Quaternionf
import org.joml.Vector3f
import org.joml.Vector4f
import java.util.random.RandomGenerator

class BoobArmorRenderer {
    val DEFAULT_SCALE: Float = 1.0f
    private var models: MutableList<BoobArmorModel>? = null
    var pivotX: Float = 0f
    var pivotY: Float = 0f
    var pivotZ: Float = 0f
    var pitch: Float = 0f
    var yaw: Float = 0f
    var roll: Float = 0f
    var scaleX: Float = 1.0f
    var scaleY: Float = 1.0f
    var scaleZ: Float = 1.0f
    var visible: Boolean = true
    var skipDraw: Boolean = false
    private var initalTransform: PartPose = PartPose.ZERO
    private var u = 0f
    private var v = 0f
    private var leftU = 0f
    private var leftV = 0f
    private var rightU = 0f
    private var rightV = 0f
    private var textureWidth = 0f
    private var textureHeight = 0f

    fun BoobArmorRenderer() {
        this.u = 20.0f
        this.v = 23.0f
        this.leftU = 18.0f
        this.leftV = 23.0f
        this.rightU = 28.0f
        this.rightV = 23.0f
        this.textureWidth = 64.0f
        this.textureHeight = 32.0f
        this.createModel()
    }

    fun getTransform(): PartPose {
        return PartPose.offsetAndRotation(this.pivotX, this.pivotY, this.pivotZ, this.pitch, this.yaw, this.roll)
    }

    fun setTransform(rotationData: PartPose) {
        this.pivotX = rotationData.x
        this.pivotY = rotationData.y
        this.pivotZ = rotationData.z
        this.pitch = rotationData.xRot
        this.yaw = rotationData.yRot
        this.roll = rotationData.zRot
        this.scaleX = 1.0f
        this.scaleY = 1.0f
        this.scaleZ = 1.0f
    }

    fun getInitialTransform(): PartPose {
        return this.initalTransform
    }

    fun setInitialTransform(initialTransform: PartPose) {
        this.initalTransform = initialTransform
    }

    fun resetTransform() {
        this.setTransform(this.initalTransform)
    }

    fun copyTransform(part: ModelPart) {
        this.scaleX = part.xScale
        this.scaleY = part.yScale
        this.scaleZ = part.zScale
        this.pitch = part.xRot
        this.yaw = part.yRot
        this.roll = part.zRot
        this.pivotX = part.x
        this.pivotY = part.y
        this.pivotZ = part.z
    }

    fun copyTransform(part: BoobArmorRenderer) {
        this.scaleX = part.scaleX
        this.scaleY = part.scaleY
        this.scaleZ = part.scaleZ
        this.pitch = part.pitch
        this.yaw = part.yaw
        this.roll = part.roll
        this.pivotX = part.pivotX
        this.pivotY = part.pivotY
        this.pivotZ = part.pivotZ
    }

    fun setPivot(x: Float, y: Float, z: Float) {
        this.pivotX = x
        this.pivotY = y
        this.pivotZ = z
    }

    fun setAngles(pitch: Float, yaw: Float, roll: Float) {
        this.pitch = pitch
        this.yaw = yaw
        this.roll = roll
    }

    //    public void render(PoseStack stack, VertexConsumer vertices, int light, int overlay) {
    //        this.render(stack, vertices, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
    //    }
    fun createModel() {
        this.models =
            mutableListOf(BoobArmorModel(this.u, this.v, this.leftU, this.leftV, this.rightU, this.rightV, 8f, 2f, 2f, false, this.textureWidth, this.textureHeight))
    }

    fun render(stack: PoseStack,
               vertices: VertexConsumer,
               light: Int,
               overlay: Int,
               red: Float,
               green: Float,
               blue: Float,
               alpha: Float,
               u: Float,
               v: Float,
               leftU: Float,
               leftV: Float,
               rightU: Float,
               rightV: Float,
               textureWidth: Float,
               textureHeight: Float) {
        if (!this.visible) {
            return
        }
        if (this.models!!.isEmpty()) {
            return
        }
        if (this.u != u || this.v != v || this.leftU != leftU || this.leftV != leftV || this.rightU != rightU || this.rightV != rightV || this.textureWidth != textureWidth || this.textureHeight != textureHeight) {
            this.u = u
            this.v = v
            this.leftU = leftU
            this.leftV = leftV
            this.rightU = rightU
            this.rightV = rightV
            this.textureWidth = textureWidth
            this.textureHeight = textureHeight
            this.createModel()
        }
        stack.pushPose()
        this.rotate(stack)
        if (!this.skipDraw) {
            this.renderCuboids(stack.last(), vertices, light, overlay, red, green, blue, alpha)
        }
        stack.popPose()
    }

    fun rotate(stack: PoseStack) {
        stack.translate(this.pivotX / 16.0f, this.pivotY / 16.0f, this.pivotZ / 16.0f)
        if (this.pitch != 0.0f || this.yaw != 0.0f || this.roll != 0.0f) {
            stack.mulPose(Quaternionf().rotationZYX(this.roll, this.yaw, this.pitch))
        }
        if (this.scaleX != 1.0f || this.scaleY != 1.0f || this.scaleZ != 1.0f) {
            stack.scale(this.scaleX, this.scaleY, this.scaleZ)
        }
    }

    private fun renderCuboids(entry: PoseStack.Pose,
                              vertexConsumer: VertexConsumer,
                              light: Int,
                              overlay: Int,
                              red: Float,
                              green: Float,
                              blue: Float,
                              alpha: Float) {
        for (boobArmorModel in this.models!!) {
            boobArmorModel.renderCuboid(entry, vertexConsumer, light, overlay, red, green, blue, alpha)
        }
    }

    fun getRandomCuboid(random: RandomGenerator): BoobArmorModel? {
        return this.models!!.get(random.nextInt(this.models!!.size))
    }

    fun isEmpty(): Boolean {
        return this.models!!.isEmpty()
    }

    fun translate(vec: Vector3f) {
        this.pivotX += vec.x()
        this.pivotY += vec.y()
        this.pivotZ += vec.z()
    }

    fun rotate(vec: Vector3f) {
        this.pitch += vec.x()
        this.yaw += vec.y()
        this.roll += vec.z()
    }

    fun scale(vec: Vector3f) {
        this.scaleX += vec.x()
        this.scaleY += vec.y()
        this.scaleZ += vec.z()
    }

    class BoobArmorModel(u: Float, v: Float, leftU: Float, leftV: Float, rightU: Float, rightV: Float, sizeX: Float, sizeY: Float, sizeZ: Float, mirror: Boolean, squishU: Float, squishV: Float) {
        private val sides: Array<ModelPart.Polygon> = Array(4) { index ->
            val vertex = ModelPart.Vertex(-4.0f * 1.24f, 0.0f, 0.0f, 0.0f, 0.0f)
            val vertex2 = ModelPart.Vertex(4.0f * 1.24f, 0.0f, 0.0f, 0.0f, 8.0f)
            val vertex3 = ModelPart.Vertex(4.0f * 1.24f, 1.08f * 1.25f, 1.68f * 1.25f, 8.0f, 8.0f)
            val vertex4 = ModelPart.Vertex(-4.0f * 1.24f, 1.08f * 1.25f, 1.68f * 1.25f, 8.0f, 0.0f)
            val vertex5 = ModelPart.Vertex(-4.0f * 1.24f, -1.68f * 1.25f, 1.68f * 1.25f, 0.0f, 0.0f)
            val vertex6 = ModelPart.Vertex(4.0f * 1.24f, -1.68f * 1.25f, 1.68f * 1.25f, 0.0f, 8.0f)
            when (index) {
                0 -> ModelPart.Polygon(arrayOf(vertex, vertex5, vertex5, vertex4), leftU, leftV, leftU + 2, leftV + 2, squishU, squishV, mirror, Direction.WEST)
                1 -> ModelPart.Polygon(arrayOf(vertex6, vertex5, vertex, vertex2), u, v, u + 8, v + 2, squishU, squishV, mirror, Direction.DOWN)
                2 -> ModelPart.Polygon(arrayOf(vertex2, vertex, vertex4, vertex3), u, v + 2, u + 8, v + 4, squishU, squishV, mirror, Direction.NORTH)
                3 -> ModelPart.Polygon(arrayOf(vertex6, vertex2, vertex3, vertex3), rightU, rightV, rightU + 2, rightV + 2, squishU, squishV, mirror, Direction.EAST)
                else -> throw IllegalStateException("Unexpected index: $index")
            }
        } //private, add to AW/AT or smth, same deal with modelpart.vertex

        fun renderCuboid(entry: PoseStack.Pose,
                         vertexConsumer: VertexConsumer,
                         light: Int,
                         overlay: Int,
                         red: Float,
                         green: Float,
                         blue: Float,
                         alpha: Float) {
            val matrix4f = entry.pose()
            val matrix3f = entry.normal()
            for (quad in this.sides) {
                val vector3f = matrix3f.transform(Vector3f(quad.normal))
                val f = vector3f.x()
                val g = vector3f.y()
                val h = vector3f.z()
                for (vertex in quad.vertices) {
                    val i = vertex.pos.x() / 16.0f
                    val j = vertex.pos.y() / 16.0f
                    val k = vertex.pos.z() / 16.0f
                    val vector4f = matrix4f.transform(Vector4f(i, j, k, 1.0f))
                    vertexConsumer.vertex(vector4f.x(), vector4f.y(), vector4f.z(), red, green, blue, alpha, vertex.u, vertex.v, overlay, light, f, g, h)
                }
            }
        }
    }
}