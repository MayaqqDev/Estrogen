package dev.mayaqq.estrogen.forge;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderer;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.forge.loot.AddSpecialThighHigh;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import static dev.mayaqq.estrogen.Estrogen.MOD_ID;

@Mod(MOD_ID)
public class EstrogenForge {

    public EstrogenForge() {
        // Config
        EstrogenConfig.register(ModLoadingContext.get()::registerConfig);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(Estrogen.REGISTRIES);

        // Init Estrogen main class
        Estrogen.init();
        AddSpecialThighHigh.REGISTER.register(modEventBus);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> EstrogenRenderer::register);
        modEventBus.addListener(EstrogenForge::init);
    }

    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(Estrogen::postInit);
    }
}