package dev.mayaqq.estrogen.forge;

import com.simibubi.create.foundation.config.ConfigBase;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderer;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
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

        // Init Estrogen main class
        Estrogen.init();

        if(FMLEnvironment.dist == Dist.CLIENT) {
            EstrogenRenderer.register();
        }
    }
}