package dev.mayaqq.estrogen.mixin;

import dev.mayaqq.estrogen.config.types.ChestConfig;
import dev.mayaqq.estrogen.injection.IPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Player.class)
public class PlayerMixin implements IPlayer {

    @Unique
    @Nullable
    private ChestConfig estrogen$chestConfig;

    @Override
    public @Nullable ChestConfig estrogen$getChestConfig() {
        return estrogen$chestConfig;
    }

    @Override
    public void estrogen$setChestConfig(@Nullable ChestConfig config) {
        estrogen$chestConfig = config;
    }
}
