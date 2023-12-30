package dev.mayaqq.estrogen.registry.common.effects;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.Dash;
import dev.mayaqq.estrogen.networking.EstrogenC2S;
import dev.mayaqq.estrogen.networking.EstrogenStatusEffectSender;
import dev.mayaqq.estrogen.registry.common.EstrogenAttributes;
import dev.mayaqq.estrogen.utils.Time;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.FluidBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.core.BlockPos;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import java.util.UUID;

import static dev.mayaqq.estrogen.registry.client.EstrogenKeybinds.dashKey;
import static dev.mayaqq.estrogen.registry.common.EstrogenAttributes.BOOB_GROWING_START_TIME;
import static dev.mayaqq.estrogen.registry.common.EstrogenAttributes.BOOB_INITIAL_SIZE;
import static dev.mayaqq.estrogen.registry.common.EstrogenEffects.ESTROGEN_EFFECT;

public class EstrogenEffect extends MobEffect {

    public short currentDashes = 0;

    public int dashCooldown = 0;
    public int groundCooldown = 0;
    public boolean onCooldown = false;
    private boolean shouldWaveDash = false;
    private BlockPos lastPos = null;

    public EstrogenEffect(MobEffectCategory statusEffectType, int color) {
        super(statusEffectType, color);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        dashCooldown--;
        groundCooldown--;
        if (dashCooldown < 0) dashCooldown = 0;
        if (groundCooldown < 0) groundCooldown = 0;

        // Dash particles
        if (dashCooldown > 0 && dashCooldown % 2 == 0 && entity.getBlockPos() != lastPos) {
            ClientPlayNetworking.send(EstrogenC2S.DASH_PARTICLES, PacketByteBufs.empty());
        }
        lastPos = entity.getBlockPos();

        // Wave dash
        if (entity instanceof PlayerEntity player && player.getWorld().isClient) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (dashCooldown > 0 && shouldWaveDash && client.options.jumpKey.isPressed()) {
                player.setVelocity(player.getRotationVector().x * 3, 1, player.getRotationVector().z * 3);
                shouldWaveDash = false;
            }

            // Whole Dash Mechanic
            if (shouldRefreshDash(player) && groundCooldown == 0) {
                groundCooldown = 4;
                currentDashes = (short) player.getAttributeValue(EstrogenAttributes.DASH_LEVEL);
            }
            onCooldown = dashCooldown > 0 || currentDashes == 0;
            Dash.onCooldown = onCooldown;
            if (dashKey.wasPressed() && !onCooldown) {
                if (player.getPitch() > 50 && player.getPitch() < 90) {
                    shouldWaveDash = true;
                }
                dashCooldown = 10;
                currentDashes--;
                player.setVelocity(player.getRotationVector().x * 2, player.getRotationVector().y * 2, player.getRotationVector().z * 2);
                ClientPlayNetworking.send(Estrogen.id("dash"), PacketByteBufs.empty());
            }
        }
    }

    private static Boolean shouldRefreshDash(PlayerEntity player) {
        return player.isOnGround() || player.getWorld().getBlockState(player.getBlockPos()).getBlock() instanceof FluidBlock;
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity instanceof ServerPlayerEntity player) {
            EstrogenStatusEffectSender.sendRemovePlayerStatusEffect(player, ESTROGEN_EFFECT, PlayerLookup.tracking(player).toArray(ServerPlayerEntity[]::new));
        }

        resetDash(entity);

        if (!entity.hasStatusEffect(ESTROGEN_EFFECT) && entity instanceof PlayerEntity) {
            entity.getAttributeInstance(BOOB_INITIAL_SIZE).setBaseValue(0.0);
            entity.getAttributeInstance(BOOB_GROWING_START_TIME).setBaseValue(-1.0);
        }
    }

    public void resetDash(LivingEntity entity) {
            if (entity instanceof ServerPlayerEntity player) {
            player.getAttributeInstance(EstrogenAttributes.DASH_LEVEL).removeModifier(UUID.fromString("c2c16ccb-9acc-4f57-88e1-7b3e0c2ffe16"));
        }
        currentDashes = 0;
        onCooldown = false;
        Dash.onCooldown = false;
        dashCooldown = 0;
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {

        if (entity instanceof ServerPlayerEntity player) {
            EstrogenStatusEffectSender.sendPlayerStatusEffect(player, ESTROGEN_EFFECT, PlayerLookup.tracking(player).toArray(ServerPlayerEntity[]::new));
        }

        super.onApplied(entity, attributes, amplifier);

        EntityAttributeInstance attributeInstance = entity.getAttributeInstance(BOOB_GROWING_START_TIME);
        // should fix crash related to applying effect to entity without given attribute
        if (attributeInstance != null && attributeInstance.getBaseValue() < 0.0) {
            double currentTime = Time.currentTime(entity.getWorld());
            entity.getAttributeInstance(BOOB_GROWING_START_TIME).setBaseValue(currentTime);
        }
    }
}
