package dev.mayaqq.estrogen.utils

import net.minecraft.core.BlockPos
import org.joml.Vector3f

fun Vector3f.blockPos() = BlockPos(this.x.toInt(), this.y.toInt(), this.z.toInt())