package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.config.types.ChestConfig;
import dev.mayaqq.estrogen.injection.IPlayerInfo;
import dev.mayaqq.estrogen.mixin.PlayerMixin;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin extends PlayerMixin {

    @Shadow
    @Nullable
    private PlayerInfo playerInfo;

    @Override
    public @Nullable ChestConfig estrogen$getChestConfig() {
        if (this.playerInfo == null) return null;
        return ((IPlayerInfo) this.playerInfo).estrogen$getChestConfig();
    }

    @Override
    public void estrogen$setChestConfig(@Nullable ChestConfig config) {
        if (this.playerInfo == null) return;
        ((IPlayerInfo) this.playerInfo).estrogen$setChestConfig(config);
    }
}
