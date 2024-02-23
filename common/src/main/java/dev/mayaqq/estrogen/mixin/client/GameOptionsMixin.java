package dev.mayaqq.estrogen.mixin.client;

import com.google.common.collect.Lists;
import dev.mayaqq.estrogen.client.registry.EstrogenKeybinds;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Options.class)
public class GameOptionsMixin {
    @Mutable
    @Final
    @Shadow
    public KeyMapping[] keyMappings;

    @Inject(at = @At("HEAD"), method = "load()V")
    public void loadHook(CallbackInfo info) {
        List<KeyMapping> allKeys = Lists.newArrayList(keyMappings);
        allKeys.remove(EstrogenKeybinds.DASH_KEY);
        allKeys.add(EstrogenKeybinds.DASH_KEY);
        keyMappings = allKeys.toArray(new KeyMapping[0]);
    }
}
