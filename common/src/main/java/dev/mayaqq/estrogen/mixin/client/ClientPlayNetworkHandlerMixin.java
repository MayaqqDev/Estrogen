package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.datagen.tags.EstrogenTags;
import dev.mayaqq.estrogen.registry.common.EstrogenEnchantments;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static dev.mayaqq.estrogen.utils.UwUfy.uwufyString;

@Mixin(ClientPacketListener.class)
public class ClientPlayNetworkHandlerMixin {
    @ModifyVariable(
            method = "sendChat(Ljava/lang/String;)V",
            at = @At(value = "HEAD"),
            ordinal = 0, argsOnly = true
    )
    private String modifyMessage(String message) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player.getInventory().contains(EstrogenTags.ItemTags.UWUFYING)) {
            message = uwufyString(message);
        } else {
            for (ItemStack stack : player.getArmorSlots()) {
                if (EnchantmentHelper.getEnchantments(stack).containsKey(EstrogenEnchantments.UWUFYING_CURSE)) {
                    message = uwufyString(message);
                    break;
                }
            }
        }
        return message;
    }
}
