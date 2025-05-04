package dev.mayaqq.estrogen.client.features.dash

import dev.mayaqq.estrogen.client.content.EstrogenKeybinds
import dev.mayaqq.estrogen.config.EstrogenCommonConfig
import dev.mayaqq.estrogen.content.EstrogenAttributes
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.content.blocks.DreamBlock
import dev.mayaqq.estrogen.features.dash.CommonDash.removeDashing
import dev.mayaqq.estrogen.features.dash.CommonDash.setDashing
import dev.mayaqq.estrogen.network.EstrogenNetwork
import dev.mayaqq.estrogen.network.messages.c2s.DashPacket
import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.Mth
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.block.LiquidBlock
import net.minecraft.world.phys.Vec3

object ClientDash {

    private const val DASH_SPEED: Double = 1.0
    private const val DASH_END_SPEED: Double = 0.4
    private const val HYPER_H_SPEED: Double = 3.0
    private const val HYPER_V_SPEED: Double = 0.5
    private const val SUPER_H_SPEED: Double = 0.8
    private const val SUPER_V_SPEED: Double = 1.0
    private const val BOUNCE_H_SPEED: Double = 0.8
    private const val BOUNCE_V_SPEED: Double = 1.5
    private const val ULTRA_MULTIPLIER: Double = 1.8

    private var isOnCooldown: Boolean = false

    private var groundCooldown: Int = 0
    private var dashCooldown: Int = 0
    private var dashes: Int = 0
    private var dashLevel: Int = 0
    private var extraParticleTicks: Int = 0

    private var dashDirection: Vec3? = null
    private var dashXRot: Double = 0.0
    private var dashDeltaModifier: Double = 0.0

    private var lastPos: BlockPos? = null

    private var dashVelocity: Vec3? = null
    private var canUltra: Boolean = false
    private var willUltra: Boolean = false
    private var ultraCooldown: Int = 0
    private var ultraVelocity: Vec3? = null

    fun tick() {
        val player = Minecraft.getInstance().player ?: return
        if (!player.hasEffect(EstrogenEffects.ESTROGEN)) {
            reset()
            return
        }

        // Refresh number of dashes
        if (canRefresh(player) && groundCooldown == 0) {
            groundCooldown = 4
            refresh(player)
        }

        if (groundCooldown > 0) groundCooldown--

        // During Dash
        if (dashCooldown > 0) dashTick(player)

        isOnCooldown = dashCooldown > 0 || dashes == 0

        if (extraParticleTicks > 0) {
            EstrogenNetwork.sendToServer(DashPacket(false, dashLevel))
            extraParticleTicks--
        }

        // After Dash (check for ultra)
        if (willUltra) {
            player.deltaMovement = (ultraVelocity?: dashVelocity!!)
                .scale(ULTRA_MULTIPLIER)
                .with(Direction.Axis.Y, 0.42)
            ultraCooldown = 0
            ultraVelocity = null
            willUltra = false
        }
        if (canUltra) {
            if (player.onGround()) {
                canUltra = false
                ultraCooldown = 5
            } else ultraVelocity = player.deltaMovement
        }
        if (ultraCooldown > 0) {
            ultraCooldown--
            if (player.onGround() and Minecraft.getInstance().options.keyJump.isDown) willUltra = true
        }

        // Start Dash
        if (EstrogenKeybinds.DASH_KEY.consumeClick() && !isOnCooldown()) {
            // Dash level of current dash (number of dashes at the beginning)
            dashLevel = dashes
            // Decrement the dash counter
            if (dashes > 0) dashes--

            dash(player, player.lookAngle, dashLevel)
        }
    }

    private fun dashTick(player: LocalPlayer) {
        dashCooldown--
        extraParticleTicks = 0

        // End Dash
        if (dashCooldown == 0) {
            removeDashing(player.uuid)
            if (!player.isFallFlying && dashXRot < 15) {
                player.deltaMovement = dashDirection!!.scale(DASH_END_SPEED).scale(dashDeltaModifier)
            } else {
                canUltra = true
            }
            return
        }

        player.deltaMovement = dashVelocity!!

        // Hyper and Super Detection
        if (Minecraft.getInstance().options.keyJump.isDown) {
            val doReverse = (Minecraft.getInstance().options.keyDown.isDown)
            if (player.onGround() && dashXRot > 15 && dashXRot < 60) {
                hyperJump(player, if (doReverse) player.lookAngle.reverse() else player.lookAngle)
            } else if (player.onGround() && dashXRot > 0 && dashXRot < 15) {
                superJump(player, if (doReverse) player.lookAngle.reverse() else player.lookAngle)
            } else if (dashXRot < -60) {
                for (direction in Direction.Plane.HORIZONTAL) {
                    // change required distance from wall here
                    val vector = Vec3.atLowerCornerOf(direction.normal).scale(0.25)
                    val aabb = player.boundingBox.expandTowards(vector)
                    if (player.level().noCollision(player, aabb)) continue

                    val jumpDirection = Vec3.atLowerCornerOf(direction.opposite.normal)
                    wallJump(player, jumpDirection)
                    break
                }
            }
        }

        // Dash particles
        if (player.blockPosition() !== lastPos) {
            EstrogenNetwork.sendToServer(DashPacket(false, dashLevel))
        }
        lastPos = player.blockPosition()
    }

    private fun dash(player: LocalPlayer, dashDirection: Vec3, dashLevel: Int = 0) {
        //TODO: DreamBlock.lookAngle = null
        setDashing(player.uuid)

        EstrogenNetwork.sendToServer(DashPacket(true, dashLevel))

        // Set counter to duration of dash
        dashCooldown = 5

        // math from Entity.lookAt()
        dashXRot = Mth.wrapDegrees(
            (-(Mth.atan2(
                dashDirection.y,
                dashDirection.horizontalDistance()
            ) * (180f / Math.PI.toFloat()).toDouble())).toFloat()
        ).toDouble()
        ClientDash.dashDirection = dashDirection

        dashDeltaModifier = EstrogenCommonConfig.Dash.deltaModifier

        // if the player is moving horizontally faster than a regular dash, use that horizontal speed instead
        dashVelocity = dashDirection.scale(DASH_SPEED).scale(dashDeltaModifier)
        if (dashDirection.horizontalDot(player.deltaMovement) > 1) {
            val speedScale = player.deltaMovement.horizontalDistance() / dashVelocity!!.horizontalDistance()
            dashVelocity = dashVelocity!!.scale(speedScale).with(Direction.Axis.Y, dashVelocity!!.y)
        }
    }

    private fun Vec3.horizontalDot(vec3: Vec3): Double {
        return this.x * vec3.x + this.y * vec3.y
    }

    private fun hyperJump(player: LocalPlayer, jumpDirection: Vec3) {
        val hyperMotion = Vec3(
            jumpDirection.x * HYPER_H_SPEED,
            HYPER_V_SPEED,
            jumpDirection.z * HYPER_H_SPEED
        )
        player.deltaMovement = hyperMotion
        dashCooldown = 0
        extraParticleTicks = 2
    }

    private fun superJump(player: LocalPlayer, jumpDirection: Vec3) {
        val superMotion = Vec3(
            jumpDirection.x * SUPER_H_SPEED,
            SUPER_V_SPEED,
            jumpDirection.z * SUPER_H_SPEED
        )
        player.deltaMovement = superMotion
        dashCooldown = 0
        extraParticleTicks = 1
    }

    private fun wallJump(player: LocalPlayer, jumpDirection: Vec3) {
        player.setDeltaMovement(
            jumpDirection.x * BOUNCE_H_SPEED,
            BOUNCE_V_SPEED,
            jumpDirection.z * BOUNCE_H_SPEED
        )
        dashCooldown = 0
        extraParticleTicks = 1
    }

    fun reset() {
        dashes = 1
        dashLevel = 0
        isOnCooldown = false
        dashCooldown = 0
    }

    fun refresh(player: Player) {
        dashes = player.getAttributeValue(EstrogenAttributes.DASH_LEVEL).toInt().toShort().toInt()
    }

    private fun canRefresh(player: Player): Boolean {
        return player.onGround() || player.level().getBlockState(player.blockPosition()).block is LiquidBlock || player.onClimbable()
    }

    fun isOnCooldown(): Boolean {
        return isOnCooldown
    }

    fun getDashLevel(): Int {
        return dashLevel
    }
}