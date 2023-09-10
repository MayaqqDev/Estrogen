package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.datagen.tags.EstrogenTags;
import dev.mayaqq.estrogen.registry.common.EstrogenEnchantments;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static dev.mayaqq.estrogen.utils.UwUfy.uwufyString;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @ModifyVariable(
            method = "sendChatMessage(Ljava/lang/String;)V",
            at = @At(value = "HEAD"),
            ordinal = 0, argsOnly = true
    )
    private String modifyMessage(String message) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        if (player.getInventory().contains(EstrogenTags.ItemTags.UWUFYING)) {
            message = uwufyString(message);
        } else {
            for (ItemStack stack : player.getArmorItems()) {
                if (EnchantmentHelper.get(stack).containsKey(EstrogenEnchantments.UWUFYING_CURSE)) {
                    message = uwufyString(message);
                    break;
                }
            }
        }
        return message;
    }
}