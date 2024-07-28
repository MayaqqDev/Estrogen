package dev.mayaqq.estrogen.registry.effects;

import dev.mayaqq.estrogen.client.Dash;
import dev.mayaqq.estrogen.client.registry.EstrogenKeybinds;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.networking.EstrogenNetworkManager;
import dev.mayaqq.estrogen.networking.messages.c2s.DashPacket;
import dev.mayaqq.estrogen.registry.EstrogenAttributes;
import dev.mayaqq.estrogen.registry.blocks.DreamBlock;
import dev.mayaqq.estrogen.utils.PlayerLookup;
import dev.mayaqq.estrogen.utils.Time;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static dev.mayaqq.estrogen.registry.EstrogenAttributes.*;
import static dev.mayaqq.estrogen.registry.EstrogenEffects.ESTROGEN_EFFECT;

public class EstrogenEffect extends MobEffect {

    public static ConcurrentHashMap<UUID, Integer> dashing = new ConcurrentHashMap<>();

    private static final UUID DASH_MODIFIER_UUID = UUID.fromString("2a2591c5-009f-4b24-97f2-b15f43415e4c");
    static public short currentDashes = 0;
    public int dashCooldown = 0;
    public boolean onCooldown = false;

    private Vec3 dashDirection = null;
    private double dashXRot = 0.0;
    private double dashDeltaModifier = 0.0;
    private final double dashSpeed = 1.0;
    private final double dashEndSpeed = 0.4;
    private final double hyperHSpeed = 3.0;
    private final double hyperVSpeed = 0.5;
    private final double superHSpeed = 0.8;
    private final double superVSpeed = 1.0;
    private boolean willHyper = false;
    private boolean willSuper = false;
    private short groundCooldown = 0;

    // Only needed for particles
    private BlockPos lastPos = null;

    public EstrogenEffect(MobEffectCategory statusEffectType, int color) {
        super(statusEffectType, color);
    }

    private static Boolean shouldRefreshDash(Player player) {
        return player.onGround() || player.level().getBlockState(player.blockPosition()).getBlock() instanceof LiquidBlock;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        // Check if Dash is enabled on the server
        if (!EstrogenConfig.server().dashEnabled.get()) return;
        // Only tick on the client and if the entity is a player
        if (!(entity instanceof Player player && player.level().isClientSide && player instanceof LocalPlayer)) return;

        // Refresh number of dashes
        if (shouldRefreshDash(player) && groundCooldown == 0) {
            groundCooldown = 4;
            currentDashes = (short) player.getAttributeValue(EstrogenAttributes.DASH_LEVEL.get());
        }

        groundCooldown--;
        if (groundCooldown < 0) groundCooldown = 0;

        // Hyper
        if (willHyper) {
            willHyper = false;
            Vec3 hyperMotion = new Vec3(
                    player.getLookAngle().x * hyperHSpeed,
                    hyperVSpeed,
                    player.getLookAngle().z * hyperHSpeed
            );
            player.setDeltaMovement(hyperMotion);
            dashCooldown = 0;
        }
        // Super
        if (willSuper) {
            willSuper = false;
            Vec3 superMotion = new Vec3(
                    player.getLookAngle().x * superHSpeed,
                    superVSpeed,
                    player.getLookAngle().z * superHSpeed
            );
            player.setDeltaMovement(superMotion);
            dashCooldown = 0;
        }

        // During Dash
        Dash:
        if (dashCooldown > 0) {
            dashCooldown--;

            // End Dash
            if (dashCooldown == 0) {
                player.setDeltaMovement(dashDirection.scale(dashEndSpeed).scale(dashDeltaModifier));
                break Dash;
            }

            player.setDeltaMovement(dashDirection.scale(dashSpeed).scale(dashDeltaModifier));

            // Hyper and Super Detection
            if (Minecraft.getInstance().options.keyJump.isDown() && player.onGround()) {
                if (dashXRot > 15 && dashXRot < 60) willHyper = true;
                else if (dashXRot > 0 && dashXRot < 15) willSuper = true;
            }

            // Dash particles
            if (player.blockPosition() != lastPos) {
                EstrogenNetworkManager.CHANNEL.sendToServer(new DashPacket(false));
            }
            lastPos = player.blockPosition();
        }

        cooldown(dashCooldown > 0 || currentDashes == 0);
        // Here is when the dash happens
        if (EstrogenKeybinds.DASH_KEY.consumeClick() && !onCooldown) {
            DreamBlock.lookAngle = null;
            EstrogenNetworkManager.CHANNEL.sendToServer(new DashPacket(true));
            EstrogenEffect.dashing.put(player.getUUID(), 20);

            // Set counter to duration of dash
            dashCooldown = 5;
            // Decrement the dash counter
            currentDashes--;

            dashDirection = player.getLookAngle();
            dashXRot = player.getXRot();
            dashDeltaModifier = EstrogenConfig.server().dashDeltaModifier.get();
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
            sendRemovePlayerStatusEffect(player, ESTROGEN_EFFECT.get(), PlayerLookup.tracking(player).toArray(ServerPlayer[]::new));
        }

        if (entity instanceof Player) {
            entity.getAttribute(EstrogenAttributes.DASH_LEVEL.get()).removeModifier(DASH_MODIFIER_UUID);
        }

        if (entity instanceof Player player && player.level().isClientSide) {
            resetDash(entity);
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
        if (effectInstance == null) return;
        sendPacket(new ClientboundUpdateMobEffectPacket(player.getId(), effectInstance), targetPlayers);
    }

    public static void sendRemovePlayerStatusEffect(ServerPlayer player, MobEffect effect, ServerPlayer... targetPlayers) {
        sendPacket(new ClientboundRemoveMobEffectPacket(player.getId(), effect), targetPlayers);
    }

    private static void sendPacket(Packet<?> packet, ServerPlayer... players) {
        for (ServerPlayer player : players) {
            player.connection.send(packet);
        }
    }
}