package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.registry.common.EstrogenEnchantments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static dev.mayaqq.estrogen.utils.UwUfy.uwufyString;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @Shadow @Final private static Logger LOGGER;

    @ModifyVariable(
            method = "sendChat(Ljava/lang/String;Lnet/minecraft/network/chat/Component;)V",
            at = @At(value = "HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private String modifyMessage(String message) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (EnchantmentHelper.getEnchantments(player.getInventory().getArmor(3)).containsKey(EstrogenEnchantments.UWUFYING_CURSE.get())) {
            message = uwufyString(message);

        }
        return message;
    }
}
