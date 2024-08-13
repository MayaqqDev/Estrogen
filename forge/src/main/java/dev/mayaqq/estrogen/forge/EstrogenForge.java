package dev.mayaqq.estrogen.forge;

import com.simibubi.create.foundation.config.ConfigBase;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderer;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.registry.EstrogenEntities;
import dev.mayaqq.estrogen.registry.EstrogenEvents;
import dev.mayaqq.estrogen.registry.EstrogenPotatoProjectiles;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.Map;

import static dev.mayaqq.estrogen.Estrogen.MOD_ID;

@Mod(MOD_ID)
public class EstrogenForge {
    public EstrogenForge() {
        // Config
        EstrogenConfig.register();
        for (Map.Entry<ModConfig.Type, ConfigBase> pair : EstrogenConfig.CONFIGS.entrySet())
            ModLoadingContext.get().registerConfig(pair.getKey(), pair.getValue().specification);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Init Estrogen main class
        Estrogen.init();

        if(FMLEnvironment.dist == Dist.CLIENT) {
            EstrogenRenderer.register();
        }
        modEventBus.addListener(EstrogenForge::init);
        modEventBus.addListener(EstrogenForge::onEntityAttributeCreation);
    }

    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(EstrogenPotatoProjectiles::register);
        event.enqueueWork(EstrogenEntities::registerSpawnPlacements);
    }

    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        EstrogenEvents.onEntityAttributeCreation().forEach(event::put);
    }
}