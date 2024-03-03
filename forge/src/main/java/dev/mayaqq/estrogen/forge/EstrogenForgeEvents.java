package dev.mayaqq.estrogen.forge;

import com.simibubi.create.foundation.config.ConfigBase;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
import dev.mayaqq.estrogen.registry.EstrogenEvents;
import dev.mayaqq.estrogen.registry.effects.EstrogenEffect;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = Estrogen.MOD_ID)
public class EstrogenForgeEvents {
    // Forge Specific Events
    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        for (ConfigBase config : EstrogenConfig.CONFIGS.values())
            if (config.specification == event.getConfig()
                    .getSpec())
                config.onLoad();
    }

    @SubscribeEvent
    public static void onReload(ModConfigEvent.Reloading event) {
        for (ConfigBase config : EstrogenConfig.CONFIGS.values())
            if (config.specification == event.getConfig()
                    .getSpec())
                config.onReload();
    }

    // Minigame ticking
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.getPhase() == EventPriority.LOWEST) {
            EstrogenEvents.playerTickEnd(event.player);
        }
    }

    // Player Tracking
    @SubscribeEvent
    public static void onPlayerStartTracking(PlayerEvent.StartTracking event) {
        EstrogenEvents.playerTracking(event.getTarget(), event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerStopTracking(PlayerEvent.StopTracking event) {
        EstrogenEvents.playerTracking(event.getTarget(), event.getEntity());
    }

    // Boob Growing
    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinLevelEvent event) {
        EstrogenEvents.onPlayerJoin(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerQuit(EntityLeaveLevelEvent event) {
        EstrogenEvents.onPlayerQuit(event.getEntity());
    }

    // Urine Collection
    @SubscribeEvent
    public static void entityInteractEvent(PlayerInteractEvent.EntityInteract event) {
        InteractionResult result = EstrogenEvents.entityInteract(event.getEntity(), event.getTarget(), event.getItemStack(), event.getLevel());
        if (result != null) {
            event.setCancellationResult(result);
            event.setCanceled(true);
        }
    }
}