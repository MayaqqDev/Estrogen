package dev.mayaqq.estrogen.forge;

import com.simibubi.create.foundation.config.ConfigBase;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.registry.EstrogenEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
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
        if (event.phase != TickEvent.Phase.END) return;
        EstrogenEvents.playerTickEnd(event.player);
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

    // Entity Interaction Recipe
    @SubscribeEvent
    public static void entityInteractEvent(PlayerInteractEvent.EntityInteract event) {
        InteractionResult result = EstrogenEvents.entityInteract(event.getEntity(), event.getTarget(), event.getItemStack(), event.getLevel());
        if (result != null) {
            event.setCancellationResult(result);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void addResourcePack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            var resourcePath = ModList.get().getModFileById(Estrogen.MOD_ID).getFile().findResource("resourcepacks/estrogenprogrammerart");
            var pack = Pack.readMetaAndCreate("builtin/estrogenprogrammerart", Component.literal("Estrogen Programmer Art"), false,
                    (path) -> new PathPackResources(path, resourcePath, false), PackType.CLIENT_RESOURCES, Pack.Position.BOTTOM, PackSource.BUILT_IN);
            event.addRepositorySource((packConsumer) -> packConsumer.accept(pack));
        }
    }

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        EstrogenEvents.onEntityDeath(event.getEntity(), event.getSource());
    }

    // Forge only, fixes issue with deployers deleting horses
    @SubscribeEvent
    public static void onEntityMount(EntityMountEvent event) {
        if (event.getEntity() instanceof FakePlayer) {
            event.setCanceled(true);
        }
    }
}