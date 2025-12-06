package dev.mayaqq.estrogen.forge.mixins;

import net.minecraftforge.network.NetworkDirection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * Fixes wrong direction specified in kittynetworking
 * Should be 100% removed once kritter merge happens
 */
@Mixin(targets = {"uwu.serenity.kittynetwork.forge.ForgeNetworkChannel$ForgeConfigurationManager"})
public class ForgeNetworkChannelMixin {
    @ModifyArg(
            method = "sendPacket",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/network/simple/SimpleChannel;toVanillaPacket(Ljava/lang/Object;Lnet/minecraftforge/network/NetworkDirection;)Lnet/minecraft/network/protocol/Packet;"
            ),
            index = 1
    )
    private NetworkDirection modifyDirection(NetworkDirection original) {
        return NetworkDirection.PLAY_TO_CLIENT;
    }
}
