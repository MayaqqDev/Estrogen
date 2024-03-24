package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.networking.EstrogenNetworkManager;
import dev.mayaqq.estrogen.networking.messages.c2s.SetChestConfigPacket;
import net.minecraftforge.common.ForgeConfigSpec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ForgeConfigSpec.ConfigValue.class)
public class ConfigValueMixin<T> {

    @Inject(method = "set(Ljava/lang/Object;)V", at=@At("TAIL"), remap = false)
    public void estrogen$set(T value, CallbackInfo ci) {
        List<String> path = ((ForgeConfigSpec.ConfigValue<T>) (Object) this).getPath();
        if (path.size() == 2 && path.get(0).equals("chestEstrogen")) {
            EstrogenNetworkManager.CHANNEL.sendToServer(new SetChestConfigPacket(EstrogenConfig.client().chestFeature.get(), EstrogenConfig.client().chestArmor.get(), EstrogenConfig.client().chestPhysics.get(), EstrogenConfig.client().chestBounciness.get().floatValue(), EstrogenConfig.client().chestDamping.get().floatValue()));
        }
    }
}
