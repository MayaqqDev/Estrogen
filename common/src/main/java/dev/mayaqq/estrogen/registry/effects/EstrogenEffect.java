package dev.mayaqq.estrogen.registry.effects;

import dev.mayaqq.estrogen.client.Dash;
import dev.mayaqq.estrogen.client.registry.EstrogenKeybinds;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.networking.EstrogenNetworkManager;
import dev.mayaqq.estrogen.networking.messages.c2s.DashPacket;
import dev.mayaqq.estrogen.networking.messages.s2c.StatusEffectPacket;
import dev.mayaqq.estrogen.registry.EstrogenAttributes;
import dev.mayaqq.estrogen.registry.blocks.DreamBlock;
import dev.mayaqq.estrogen.registry.sounds.EstrogenEffectSoundInstance;
import dev.mayaqq.estrogen.utils.PlayerLookup;
import dev.mayaqq.estrogen.utils.Time;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.LiquidBlock;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import static dev.mayaqq.estrogen.registry.EstrogenAttributes.*;
import static dev.mayaqq.estrogen.registry.EstrogenEffects.ESTROGEN_EFFECT;

public class EstrogenEffect extends MobEffect {

    public static HashMap<UUID, Integer> dashing = new HashMap<>();

    private static final UUID DASH_MODIFIER_UUID = UUID.fromString("2a2591c5-009f-4b24-97f2-b15f43415e4c");
    static public short currentDashes = 0;
    public int dashCooldown = 0;
    public boolean onCooldown = false;
    private boolean shouldWaveDash = false;

    // Only needed for particles
    private BlockPos lastPos = null;

    public EstrogenEffect(MobEffectCategory statusEffectType, int color) {
        super(statusEffectType, color);
    }

    private static Boolean shouldRefreshDash(Player player) {
        return player.onGround() || player.level().getBlockState(player.blockPosition()).getBlock() instanceof LiquidBlock;
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        // Check if Dash is enabled on the server
        if (!EstrogenConfig.server().dashEnabled.get()) return;
        // Only tick on the client and if the entity is a player
        if (entity instanceof Player player && player.level().isClientSide) {
            // Sound
            Minecraft client = Minecraft.getInstance();
            if (EstrogenConfig.client().ambientMusic.get() && !client.getSoundManager().isActive(getSoundInstance())) {
                client.getSoundManager().play(getSoundInstance());
            }

            // cooldown processing
            dashCooldown--;
            if (dashCooldown < 0) dashCooldown = 0;

            // Refresh number of dashes
            if (shouldRefreshDash(player)) {
                currentDashes = (short) player.getAttributeValue(EstrogenAttributes.DASH_LEVEL.get());
            }

            // Dash particles
            if (dashCooldown > 0 && dashCooldown % 2 == 0 && player.blockPosition() != lastPos) {
                EstrogenNetworkManager.CHANNEL.sendToServer(new DashPacket(false));
            }
            lastPos = player.blockPosition();

            // Wave dash, we process it near the top so it happens after the dash which triggers this.
            if (dashCooldown > 0 && shouldWaveDash && Minecraft.getInstance().options.keyJump.isDown()) {
                player.setDeltaMovement(player.getLookAngle().x * 3, 1, player.getLookAngle().z * 3);
                shouldWaveDash = false;
            }

            cooldown(dashCooldown > 0 || currentDashes == 0);
            // Here is when the dash happens
            if (EstrogenKeybinds.DASH_KEY.consumeClick() && !onCooldown) {
                DreamBlock.lookAngle = null;
                EstrogenNetworkManager.CHANNEL.sendToServer(new DashPacket(true));
                EstrogenEffect.dashing.put(player.getUUID(), 20);
                // This makes you wave dash the next tick
                if (player.getXRot() > 50 && player.getXRot() < 90) {
                    shouldWaveDash = true;
                }
                // Reset the cooldown to wait half a second
                dashCooldown = 10;
                // Decrement the dash counter
                currentDashes--;

                // Dash
                int dashDeltaModifier = EstrogenConfig.server().dashDeltaModifier.get();
                player.setDeltaMovement(player.getLookAngle().x * dashDeltaModifier, player.getLookAngle().y * dashDeltaModifier, player.getLookAngle().z * dashDeltaModifier);
            }
        }
    }

    public void cooldown(boolean onCooldown) {
        this.onCooldown = onCooldown;
        Dash.onCooldown = onCooldown;
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributes, int amplifier) {
        dashing.remove(entity.getUUID());

        if (entity instanceof ServerPlayer player) {
            sendPlayerStatusEffect(player, ESTROGEN_EFFECT.get(), PlayerLookup.tracking(player).toArray(ServerPlayer[]::new));
        }

        if (entity instanceof Player) {
            entity.getAttribute(EstrogenAttributes.DASH_LEVEL.get()).removeModifier(DASH_MODIFIER_UUID);
        }

        if (entity instanceof Player player && player.level().isClientSide) {
            resetDash(entity);
            Minecraft client = Minecraft.getInstance();
            client.getSoundManager().stop(getSoundInstance());
            soundInstance = null;
        }

        if (!entity.hasEffect(ESTROGEN_EFFECT.get()) && entity instanceof Player) {
            entity.getAttribute(BOOB_INITIAL_SIZE.get()).setBaseValue(0.0);
            entity.getAttribute(BOOB_GROWING_START_TIME.get()).setBaseValue(-1.0);
        }
    }

    public void resetDash(LivingEntity entity) {
        currentDashes = 0;
        onCooldown = false;
        Dash.onCooldown = false;
        dashCooldown = 0;
    }

    private static SoundInstance soundInstance = null;

    private static SoundInstance getSoundInstance() {
        if (soundInstance == null) {
            soundInstance = new EstrogenEffectSoundInstance();
        }
        return soundInstance;
    }

    @Override
    public void addAttributeModifiers(@NotNull LivingEntity entity, @NotNull AttributeMap attributes, int amplifier) {

        if (!(entity instanceof Player)) return;

        if (entity instanceof ServerPlayer player) {
            sendPlayerStatusEffect(player, ESTROGEN_EFFECT.get(), PlayerLookup.tracking(player).toArray(ServerPlayer[]::new));
        }

        super.addAttributeModifiers(entity, attributes, amplifier);

        AttributeModifier dashModifier = new AttributeModifier(DASH_MODIFIER_UUID, "Dash Level", amplifier + 1, AttributeModifier.Operation.ADDITION);
        entity.getAttribute(DASH_LEVEL.get()).removeModifier(DASH_MODIFIER_UUID);
        entity.getAttribute(DASH_LEVEL.get()).addPermanentModifier(dashModifier);

        AttributeInstance startTime = entity.getAttribute(BOOB_GROWING_START_TIME.get());
        // should fix crash related to applying effect to entity without given attribute
        if (startTime != null && startTime.getBaseValue() < 0.0) {
            double currentTime = Time.currentTime(entity.level());
            entity.getAttribute(BOOB_GROWING_START_TIME.get()).setBaseValue(currentTime);
        }
    }

    public static void sendPlayerStatusEffect(ServerPlayer player, MobEffect effect, ServerPlayer... targetPlayers) {
        MobEffectInstance effectInstance = player.getEffect(effect);
        if (effectInstance != null) {
            Packet<ClientGamePacketListener> packet = new ClientboundUpdateMobEffectPacket(player.getId(), effectInstance);
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            packet.write(buf);
            EstrogenNetworkManager.CHANNEL.sendToPlayers(new StatusEffectPacket(buf), Arrays.stream(targetPlayers).toList());
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
