package dev.mayaqq.estrogen.forge;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderer;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.forge.loot.AddSpecialThighHigh;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

import static dev.mayaqq.estrogen.Estrogen.MOD_ID;

@Mod(MOD_ID)
public class EstrogenForge {

    public EstrogenForge() {
        // Config
        EstrogenConfig.register(EstrogenForge::forceLoadConfig);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(Estrogen.REGISTRIES);
        EntityAttributeCreationEvent

        // Init Estrogen main class
        Estrogen.init();
        AddSpecialThighHigh.REGISTER.register(modEventBus);

        if(FMLEnvironment.dist == Dist.CLIENT) {
            EstrogenRenderer.register();
        }
    }

    public static void forceLoadConfig(ModConfig.Type type, ForgeConfigSpec spec) {
        ModContainer container = ModLoadingContext.get().getActiveContainer();
        ModConfig config = new ModConfig(type, spec, container);

        container.addConfig(config);

        Path path = FMLPaths.CONFIGDIR.get().resolve(config.getFileName());
        CommentedFileConfig data = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();

        data.load();
        spec.setConfig(data);
    }
}