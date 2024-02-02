package dev.mayaqq.estrogen.forge;

import com.simibubi.create.foundation.config.ConfigBase;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.SlotTypeMessage;

@Mod.EventBusSubscriber(modid = Estrogen.MOD_ID)
public class EstrogenForgeEvents {
    // Config
    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        for (ConfigBase config : EstrogenConfig.CONFIGS.values())
            if (config.specification == event.getConfig()
                    .getSpec())
                config.onLoad();
    }

    // Config
    @SubscribeEvent
    public static void onReload(ModConfigEvent.Reloading event) {
        for (ConfigBase config : EstrogenConfig.CONFIGS.values())
            if (config.specification == event.getConfig()
                    .getSpec())
                config.onReload();
    }

    @SubscribeEvent
    public static void interModEnqueue(InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () ->
                new SlotTypeMessage.Builder("thighs")
                        .icon(Estrogen.id("slot/thighs"))
                        .build()
        );
    }
}