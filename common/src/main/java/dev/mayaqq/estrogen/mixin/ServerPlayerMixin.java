package dev.mayaqq.estrogen.mixin;

import dev.mayaqq.estrogen.config.ChestConfig;
import dev.mayaqq.estrogen.config.PlayerEntityExtension;
import dev.mayaqq.estrogen.networking.EstrogenNetworkManager;
import dev.mayaqq.estrogen.networking.messages.s2c.ChestConfigPacket;
import dev.mayaqq.estrogen.registry.EstrogenAttributes;
import dev.mayaqq.estrogen.utils.Boob;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends PlayerEntityMixin {

    @Shadow @Final public MinecraftServer server;

    protected ServerPlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "restoreFrom", at = @At("TAIL"))
    private void restoreFrom(ServerPlayer oldPlayer, boolean alive, CallbackInfo ci) {
        if (oldPlayer instanceof PlayerEntityExtension oldPlayerExtension) {
            ChestConfig chestConfig = oldPlayerExtension.estrogen$getChestConfig();
            if (chestConfig != null) {
                this.estrogen$setChestConfig(chestConfig);
            }
        }

        AttributeInstance showBoobsOld = oldPlayer.getAttribute(EstrogenAttributes.SHOW_BOOBS.get());
        AttributeInstance showBoobsNew = this.getAttribute(EstrogenAttributes.SHOW_BOOBS.get());
        AttributeInstance boobInitialSizeNew = this.getAttribute(EstrogenAttributes.BOOB_INITIAL_SIZE.get());
        AttributeInstance boobGrowingStartTimeNew = this.getAttribute(EstrogenAttributes.BOOB_GROWING_START_TIME.get());
        if (showBoobsOld != null && showBoobsNew != null) {
            showBoobsNew.setBaseValue(showBoobsOld.getBaseValue());

            Player thisPlayer = (Player) (Object) this;
            if (Boob.shouldShow(thisPlayer)) {
                AttributeInstance boobInitialSizeOld = oldPlayer.getAttribute(EstrogenAttributes.BOOB_INITIAL_SIZE.get());
                if (boobInitialSizeOld != null && boobInitialSizeNew != null) {
                    boobInitialSizeNew.setBaseValue(boobInitialSizeOld.getBaseValue());
                }
                AttributeInstance boobGrowingStartTimeOld = oldPlayer.getAttribute(EstrogenAttributes.BOOB_GROWING_START_TIME.get());
                if (boobGrowingStartTimeOld != null && boobGrowingStartTimeNew != null) {
                    boobGrowingStartTimeNew.setBaseValue(boobGrowingStartTimeOld.getBaseValue());
                }
            }
        }

        this.server.tell(new TickTask(this.server.getTickCount(), this::estrogen$syncChestConfig));
    }

    @Unique
    private void estrogen$syncChestConfig() {
        ChestConfig chestConfig = this.estrogen$getChestConfig();
        if (chestConfig != null) {
            EstrogenNetworkManager.CHANNEL.sendToAllPlayers(new ChestConfigPacket(this.getUUID(), chestConfig), this.server);
        }
    }
}
