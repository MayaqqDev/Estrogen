package dev.mayaqq.estrogen.mixin;

import dev.mayaqq.estrogen.config.types.ChestConfig;
import dev.mayaqq.estrogen.features.dash.CommonDash;
import dev.mayaqq.estrogen.injection.IPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements IPlayer {

    @Unique
    @Nullable
    private ChestConfig estrogen$chestConfig;

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @Nullable ChestConfig estrogen$getChestConfig() {
        return estrogen$chestConfig;
    }

    @Override
    public void estrogen$setChestConfig(@Nullable ChestConfig config) {
        estrogen$chestConfig = config;
    }

    @Inject(method = "jumpFromGround()V", at = @At("HEAD"), cancellable = true)
    private void jumpFromGround(CallbackInfo callbackInfo) {
        if (CommonDash.INSTANCE.isDashing(getUUID())) callbackInfo.cancel();
    }
}
