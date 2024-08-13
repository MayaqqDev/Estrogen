package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.client.features.UwUfy;
import dev.mayaqq.estrogen.registry.EstrogenEnchantments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ClientPacketListener.class)
public class ClientPlayNetworkHandlerMixin {
    /*
     * This mixin uwufies chat messages sent by the player if the player has a helmet with the Uwufying Curse enchantment.
     * Because we modify the String by itself, and do not replace any method calls, it should be compatible with most if not all mods.
     */
    @ModifyVariable(
            method = "sendChat(Ljava/lang/String;)V",
            at = @At(value = "HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private String modifyMessage(String message) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (EnchantmentHelper.getEnchantments(player.getInventory().getArmor(3)).containsKey(EstrogenEnchantments.UWUFYING_CURSE.get())) {
            message = UwUfy.uwufyString(message);

        }
        return message;
    }
}
