package dev.mayaqq.estrogen.registry.effects;

import dev.mayaqq.estrogen.client.features.dash.ClientDash;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.features.dash.CommonDash;
import dev.mayaqq.estrogen.registry.EstrogenAttributes;
import dev.mayaqq.estrogen.utils.Boob;
import dev.mayaqq.estrogen.utils.PlayerLookup;
import dev.mayaqq.estrogen.utils.Time;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.player.LocalPlayer;
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
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static dev.mayaqq.estrogen.registry.EstrogenAttributes.*;
import static dev.mayaqq.estrogen.registry.EstrogenEffects.ESTROGEN_EFFECT;

public class EstrogenEffect extends MobEffect {

    private static final UUID DASH_MODIFIER_UUID = UUID.fromString("2a2591c5-009f-4b24-97f2-b15f43415e4c");
    private static final UUID FALL_DAMAGE_RESISTANCE_UUID = UUID.fromString("2a2591c5-009f-4b24-97f2-b15f43415e4d");
    private static final UUID BOOBS_MODIFIER_UUID = UUID.fromString("2a2591c5-009f-4b24-97f2-b15f43415e4e");

    public EstrogenEffect(MobEffectCategory statusEffectType, int color) {
        super(statusEffectType, color);
        addAttributeModifier(FALL_DAMAGE_RESISTANCE.get(), FALL_DAMAGE_RESISTANCE_UUID.toString(), 2, AttributeModifier.Operation.ADDITION);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        // Check if Dash is enabled on the server
        if (!EstrogenConfig.server().dashEnabled.get()) return;
        // Only tick on the client and if the entity is a player
        if (!(entity instanceof LocalPlayer player && player.level().isClientSide)) return;

        ClientDash.tick();
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributes, int amplifier) {
        super.removeAttributeModifiers(entity, attributes, amplifier);
        CommonDash.removeDashing(entity.getUUID());

        if (entity instanceof ServerPlayer player) {
            sendRemovePlayerStatusEffect(player, ESTROGEN_EFFECT.get(), PlayerLookup.tracking(player).toArray(ServerPlayer[]::new));
        }

        if (entity instanceof Player) {
            entity.getAttribute(EstrogenAttributes.DASH_LEVEL.get()).removeModifier(DASH_MODIFIER_UUID);
            entity.getAttribute(EstrogenAttributes.SHOW_BOOBS.get()).removeModifier(BOOBS_MODIFIER_UUID);
        }

        if (entity instanceof Player player && !Boob.shouldShow(player)) {
            entity.getAttribute(BOOB_INITIAL_SIZE.get()).setBaseValue(0.0);
            entity.getAttribute(BOOB_GROWING_START_TIME.get()).setBaseValue(-1.0);
        }
    }

    @Override
    public void addAttributeModifiers(@NotNull LivingEntity entity, @NotNull AttributeMap attributes, int amplifier) {
        super.addAttributeModifiers(entity, attributes, amplifier);
        if (!(entity instanceof Player)) return;

        if (entity instanceof ServerPlayer player) {
            sendPlayerStatusEffect(player, ESTROGEN_EFFECT.get(), PlayerLookup.tracking(player).toArray(ServerPlayer[]::new));
        }

        super.addAttributeModifiers(entity, attributes, amplifier);

        AttributeModifier dashModifier = new AttributeModifier(DASH_MODIFIER_UUID, "Dash Level", amplifier + 1, AttributeModifier.Operation.ADDITION);
        entity.getAttribute(DASH_LEVEL.get()).removeModifier(DASH_MODIFIER_UUID);
        entity.getAttribute(DASH_LEVEL.get()).addPermanentModifier(dashModifier);

        entity.getAttribute(SHOW_BOOBS.get()).removeModifier(BOOBS_MODIFIER_UUID);
        entity.getAttribute(SHOW_BOOBS.get()).addPermanentModifier(new AttributeModifier(BOOBS_MODIFIER_UUID, "Show Boobs", 1, AttributeModifier.Operation.ADDITION));

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

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
