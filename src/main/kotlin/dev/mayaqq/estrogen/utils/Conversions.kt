package dev.mayaqq.estrogen.utils

import net.minecraft.core.BlockPos
import net.minecraft.core.Vec3i
import net.minecraft.world.phys.Vec3
import org.joml.Vector3d
import org.joml.Vector3f

// TODO: Once bigger put this in cynosure
fun Vector3f.blockPos() = BlockPos(this.x.toInt(), this.y.toInt(), this.z.toInt())
fun Vector3d.blockPos() = BlockPos(this.x.toInt(), this.y.toInt(), this.z.toInt())
fun Vec3.blockPos() = BlockPos(this.x.toInt(), this.y.toInt(), this.z.toInt())
fun Vec3i.blockPos() = BlockPos(this.x, this.y, this.z)

fun BlockPos.toVector3f() = Vector3f(this.x.toFloat(), this.y.toFloat(), this.z.toFloat())
fun BlockPos.toVector3d() = Vector3d(this.x.toDouble(), this.y.toDouble(), this.z.toDouble())
fun BlockPos.toVec3() = Vec3(this.x.toDouble(), this.y.toDouble(), this.z.toDouble())

fun Vector3f.toVector3d() = Vector3d(this.x.toDouble(), this.y.toDouble(), this.z.toDouble())
fun Vector3d.toVector3f() = Vector3f(this.x.toFloat(), this.y.toFloat(), this.z.toFloat())

fun Vector3f.toVec3() = Vec3(this.x.toDouble(), this.y.toDouble(), this.z.toDouble())
fun Vector3d.toVec3() = Vec3(this.x, this.y, this.z)

