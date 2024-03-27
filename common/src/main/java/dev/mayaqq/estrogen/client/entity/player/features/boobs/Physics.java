package dev.mayaqq.estrogen.client.entity.player.features.boobs;

import dev.mayaqq.estrogen.config.ChestConfig;
import dev.mayaqq.estrogen.config.PlayerEntityExtension;
import dev.mayaqq.estrogen.utils.Time;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class Physics {
    private Vec3 previousPosition;
    private Vec2 previousVelocity;
    private Vec2 previousAcceleration = new Vec2(0.0f, 0.0f);
    public Vec2 boobPosition = new Vec2(0.0f, 0.0f);
    private Vec2 boobVelocity = new Vec2(0.0f, 0.0f);
    private Vec2 boobAcceleration;
    private Vec2 iBoobPosition;
    private Vec2 iBoobVelocity;
    private Double lastRenderTime;
    private double lastTickDelta;
    public boolean active = false;
    public boolean expired = false;

    public void update(Player player) {
        if (this.lastRenderTime != null && Time.currentTime(player.level()) - this.lastRenderTime > 200) {
            this.expired = true;
            return;
        }

        if (this.previousPosition == null) {
            this.previousPosition = player.position();
            return;
        }

        Vec3 movement = player.position().subtract(this.previousPosition);

        Vec2 velocity = new Vec2(Mth.sqrt(((float) (Mth.square(movement.x*Mth.sin(player.yBodyRot*Mth.DEG_TO_RAD)) + Mth.square(movement.z*Mth.cos(player.yBodyRot*Mth.DEG_TO_RAD))))), (float) movement.y);

        if (this.previousVelocity == null) {
             this.previousVelocity = velocity;
             return;
        }

        ChestConfig chestConfig = ((PlayerEntityExtension) player).estrogen$getChestConfig();
        if (chestConfig == null) {
            this.active = false;
            return;
        }
        final float SPRING_COEFFICIENT = 1.0f/(chestConfig.bounciness()*10.0f);
        final float DAMPING_COEFFICIENT = chestConfig.damping();

        this.previousPosition = player.position();
        Vec2 acceleration = velocity.add(this.previousVelocity.negated());
        this.previousVelocity = velocity;
        this.boobAcceleration = this.boobPosition.scale(-SPRING_COEFFICIENT).add(this.boobVelocity.scale(DAMPING_COEFFICIENT).negated());
        if (this.boobAcceleration.length() < 0.002) {
            this.boobPosition = new Vec2(0.0f, 0.0f);
            this.boobVelocity = new Vec2(0.0f, 0.0f);

        } else {
            this.boobVelocity = this.boobVelocity.add(this.boobAcceleration);
            this.boobPosition = this.boobPosition.add(this.boobVelocity);
        }

        this.boobPosition = this.boobPosition.add(acceleration.add(this.previousAcceleration.negated()).scale(1.0f/SPRING_COEFFICIENT).negated());
        this.boobPosition = new Vec2(Mth.clamp(this.boobPosition.x, -1.0f, 1.0f), Mth.clamp(this.boobPosition.y, -1.0f, 1.0f));
        this.previousAcceleration = acceleration;

        this.active = true;
    }

    public Vec2 interpolate(double currentTime, double tickDelta) {
        if (this.lastRenderTime == null) {
            this.lastRenderTime = currentTime;
            this.lastTickDelta = tickDelta;
        }

        if (this.iBoobPosition == null || currentTime != this.lastRenderTime) {
            this.iBoobPosition = this.boobPosition;
            this.iBoobVelocity = this.boobVelocity;
        }
        this.lastRenderTime = currentTime;

        this.iBoobPosition = this.iBoobPosition.add(this.iBoobVelocity.scale((float) (tickDelta - this.lastTickDelta)));
        this.iBoobVelocity = this.iBoobVelocity.add(this.boobAcceleration.scale((float) (tickDelta - this.lastTickDelta)));
        this.lastTickDelta = tickDelta;
        return this.iBoobPosition;
    }
}
