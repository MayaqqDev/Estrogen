package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.config.types.ChestConfig;
import dev.mayaqq.estrogen.injection.IPlayerInfo;
import net.minecraft.client.multiplayer.PlayerInfo;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerInfo.class)
public class PlayerInfoMixin implements IPlayerInfo {

    @Unique
    @Nullable
    private ChestConfig estrogen$chestConfig;

    @Override
    public @Nullable ChestConfig estrogen$getChestConfig() {
        return this.estrogen$chestConfig;
    }

    @Override
    public void estrogen$setChestConfig(@Nullable ChestConfig config) {
        this.estrogen$chestConfig = config;
    }
}
