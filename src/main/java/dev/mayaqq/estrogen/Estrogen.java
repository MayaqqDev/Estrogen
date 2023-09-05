package dev.mayaqq.estrogen;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.networking.EstrogenC2S;
import dev.mayaqq.estrogen.registry.common.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Estrogen implements ModInitializer, PreLaunchEntrypoint {
    public static final Logger LOGGER = LoggerFactory.getLogger("Estrogen");
    public static final EstrogenConfig CONFIG = EstrogenConfig.createAndLoad();

    public static Identifier id(String path) {
        return new Identifier("estrogen", path);
    }

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create("estrogen");

    static {
        REGISTRATE.setTooltipModifierFactory(item ->
                new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE)
                        .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
        );
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Injecting Estrogen into your veins...");
        EstrogenFluids.register();
        EstrogenEffects.register();
        EstrogenC2S.register();
        EstrogenItems.register();
        EstrogenFoodComponents.register();
        EstrogenSounds.register();
        EstrogenBlocks.register();
        EstrogenBlockEntities.register();
        EstrogenRecipes.register();
        EstrogenEnchantments.register();
        EstrogenPonderScenes.register();
        EstrogenEvents.register();
        EstrogenAttributes.register();
        REGISTRATE.register();
    }

    @Override
    public void onPreLaunch() {
        MixinExtrasBootstrap.init();
    }
}