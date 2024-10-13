package dev.mayaqq.estrogen.registry;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.networking.EstrogenNetworkManager;
import dev.mayaqq.estrogen.networking.messages.c2s.SpawnHeartsPacket;
import dev.mayaqq.estrogen.networking.messages.s2c.DreamBlockSeedPacket;
import dev.mayaqq.estrogen.registry.effects.EstrogenEffect;
import dev.mayaqq.estrogen.registry.items.ThighHighsItem;
import dev.mayaqq.estrogen.registry.recipes.inventory.EntityInteractionInventory;
import dev.mayaqq.estrogen.utils.Boob;
import dev.mayaqq.estrogen.utils.Time;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.WorldOptions;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicReference;

import static dev.mayaqq.estrogen.registry.EstrogenAttributes.BOOB_GROWING_START_TIME;
import static dev.mayaqq.estrogen.registry.EstrogenAttributes.BOOB_INITIAL_SIZE;
import static dev.mayaqq.estrogen.registry.EstrogenEffects.ESTROGEN_EFFECT;
import static dev.mayaqq.estrogen.utils.Boob.boobSize;

public class EstrogenEvents {
    // Entity Interaction Recipe
    public static InteractionResult entityInteract(Player player, Entity entity, ItemStack stack, Level level) {
        AtomicReference<InteractionResult> result = new AtomicReference<>(null);
        if (level instanceof ServerLevel) {
            level.getServer().getRecipeManager().getAllRecipesFor(EstrogenRecipes.ENTITY_INTERACTION.get()).forEach(recipe -> {
                EntityInteractionInventory inv = new EntityInteractionInventory(entity.getType(), stack);
                if (recipe.matches(inv, level)) {
                    level.playSound(null, player.blockPosition(), BuiltInRegistries.SOUND_EVENT.get(recipe.sound()), SoundSource.PLAYERS);
                    if (!player.isCreative()) stack.shrink(1);
                    player.getInventory().placeItemBackInInventory(recipe.assemble(inv, level.registryAccess()));
                    result.set(InteractionResult.SUCCESS);
                }
            });
        }

        if (player.level().isClientSide && EstrogenConfig.client().entityPatting.get() && player.isCrouching() && stack.isEmpty()) {
            ResourceLocation sound = Estrogen.id("empty");
            if (entity instanceof Mob mob) {
                if (mob.getAmbientSound() != null) {
                    sound = mob.getAmbientSound().getLocation();
                }
            }
            EstrogenNetworkManager.CHANNEL.sendToServer(new SpawnHeartsPacket(entity.position(), sound));
            LocalPlayer localPlayer = (LocalPlayer) player;
            localPlayer.swing(player.getUsedItemHand());
        }

        return result.get();
    }

    public static void onPlayerJoin(Entity entity) {
        if (entity instanceof Player player) {
            if (Boob.shouldShow(player)) {
                double currentTime = Time.currentTime(player.level());
                player.getAttribute(BOOB_GROWING_START_TIME.get()).setBaseValue(currentTime);
            }

            if(!player.level().isClientSide) {
                ServerLevel serverLevel = (ServerLevel) player.level();
                String seedAsString = String.valueOf(serverLevel.getSeed());

                try {
                    MessageDigest md5 = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
                    byte[] digest = md5.digest(seedAsString.getBytes());

                    long newSeed = WorldOptions.parseSeed(new String(digest)).getAsLong();

                    EstrogenNetworkManager.CHANNEL.sendToPlayer(new DreamBlockSeedPacket(newSeed), player);

                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }
            }

        }
    }

    public static void onPlayerQuit(Entity entity) {
        if (entity instanceof Player player) {
            if (Boob.shouldShow(player)) {
                double startTime = player.getAttributeValue(BOOB_GROWING_START_TIME.get());
                double currentTime = Time.currentTime(player.level());
                float initialSize = (float) player.getAttributeValue(BOOB_INITIAL_SIZE.get());
                float size = boobSize(startTime, currentTime, initialSize, 0.0F);
                player.getAttribute(BOOB_INITIAL_SIZE.get()).setBaseValue(size);
            }
        }
    }

    public static void playerTickEnd(Player player) {
        if (EstrogenConfig.common().minigameEnabled.get()) {
            if (EstrogenConfig.common().permaDash.get()) {
                player.addEffect(new MobEffectInstance(ESTROGEN_EFFECT.get(), 20, EstrogenConfig.common().girlPowerLevel.get(), false, false, false));
            }
        }
    }

    public static void playerTracking(Entity trackedEntity, Player player) {
        if (trackedEntity instanceof ServerPlayer trackedPlayer && player instanceof ServerPlayer trackingPlayer) {
            EstrogenEffect.sendPlayerStatusEffect(trackedPlayer, EstrogenEffects.ESTROGEN_EFFECT.get(), trackingPlayer);
        }
    }

    public static void onEntityDeath(LivingEntity entity, DamageSource source) {
        if (source.getEntity() instanceof ServerPlayer player) {
            EstrogenAdvancementCriteria.KILLED_WITH_EFFECT.trigger(player, entity);
        }
    }

    // Not using the provided player as it is always null on forge (wtf)
    public static void onDataPackSync(ServerPlayer player, boolean isJoin) {
        ThighHighsItem item = EstrogenItems.THIGH_HIGHS.get();
        item.syncStyles(player);
    }
}
