package dev.mayaqq.estrogen.client.features.boobs

import dev.mayaqq.estrogen.utils.Time
import net.minecraft.util.Mth
import net.minecraft.world.entity.player.Player
import net.minecraft.world.phys.Vec2
import net.minecraft.world.phys.Vec3

class Physics {
    private var previousSneaking = false
    private var previousPosition: Vec3? = null
    private var previousVelocity: Vec2? = null
    private var previousAcceleration = Vec2(0.0f, 0.0f)
    var boobPosition: Vec2 = Vec2(0.0f, 0.0f)
    private var boobVelocity = Vec2(0.0f, 0.0f)
    private var boobAcceleration: Vec2? = null
    private var iBoobPosition: Vec2? = null
    private var iBoobVelocity: Vec2? = null
    private var lastRenderTime: Double? = null
    private var lastTickDelta = 0.0
    var active: Boolean = false
    var expired: Boolean = false

    fun update(player: Player) {
        if (this.lastRenderTime != null && Time.currentTime(player.level()) - this.lastRenderTime!! > 200) {
            this.expired = true
            return
        }

        if (this.previousPosition == null) {
            this.previousSneaking = player.isShiftKeyDown
            this.previousPosition = player.position()
            return
        }

        val movement = player.position().subtract(this.previousPosition)

        var velocity = Vec2(
            Mth.sqrt(
                ((Mth.square(movement.x * Mth.sin(player.yBodyRot * Mth.DEG_TO_RAD)) + Mth.square(
                    movement.z * Mth.cos(player.yBodyRot * Mth.DEG_TO_RAD)
                )).toFloat())
            ), movement.y.toFloat()
        )

        if (player.isShiftKeyDown != this.previousSneaking) {
            val sign = if (this.previousSneaking) -1.0f else 1.0f
            velocity = velocity.add(Vec2(sign * 0.3f, -sign * 0.2f))
        }
        this.previousSneaking = player.isShiftKeyDown

        if (this.previousVelocity == null) {
            this.previousVelocity = velocity
            return
        }

        //TODO: val chestConfig: ChestConfig? = (player as PlayerEntityExtension).`estrogen$getChestConfig`()
        if (TODO("chestConfig == null")) {
            this.active = false
            return
        }
        val SPRING_COEFFICIENT: Float = TODO("1.0f / (chestConfig.bounciness() * 10.0f)")
        val DAMPING_COEFFICIENT: Float = TODO("chestConfig.damping()")

        this.previousPosition = player.position()
        val acceleration = velocity.add(this.previousVelocity!!.negated())
        this.previousVelocity = velocity
        this.boobAcceleration =
            this.boobPosition.scale(-SPRING_COEFFICIENT).add(this.boobVelocity.scale(DAMPING_COEFFICIENT).negated())
        if (this.boobAcceleration!!.length() < 0.002) {
            this.boobPosition = Vec2(0.0f, 0.0f)
            this.boobVelocity = Vec2(0.0f, 0.0f)
        } else {
            this.boobVelocity = this.boobVelocity.add(this.boobAcceleration)
            this.boobPosition = this.boobPosition.add(this.boobVelocity)
        }

        this.boobPosition = this.boobPosition.add(
            acceleration.add(this.previousAcceleration.negated()).scale(1.0f / SPRING_COEFFICIENT).negated()
        )
        this.boobPosition =
            Vec2(Mth.clamp(this.boobPosition.x, -1.0f, 1.0f), Mth.clamp(this.boobPosition.y, -1.0f, 1.0f))
        this.previousAcceleration = acceleration

        this.active = true
    }

    fun interpolate(currentTime: Double, tickDelta: Double): Vec2? {
        if (this.lastRenderTime == null) {
            this.lastRenderTime = currentTime
            this.lastTickDelta = tickDelta
        }

        if (this.iBoobPosition == null || currentTime != this.lastRenderTime) {
            this.iBoobPosition = this.boobPosition
            this.iBoobVelocity = this.boobVelocity
        }
        this.lastRenderTime = currentTime

        this.iBoobPosition =
            this.iBoobPosition!!.add(this.iBoobVelocity!!.scale((tickDelta - this.lastTickDelta).toFloat()))
        this.iBoobVelocity =
            this.iBoobVelocity!!.add(this.boobAcceleration!!.scale((tickDelta - this.lastTickDelta).toFloat()))
        this.lastTickDelta = tickDelta
        return this.iBoobPosition
    }
}