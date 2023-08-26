package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.datagen.tags.EstrogenTags;
import dev.mayaqq.estrogen.registry.common.EstrogenEnchantments;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static dev.mayaqq.estrogen.utils.UwUfy.uwufyString;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    @ModifyArg(
            method = "sendChatMessage(Ljava/lang/String;Lnet/minecraft/text/Text;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;sendChatMessageInternal(Ljava/lang/String;Lnet/minecraft/text/Text;)V"
            ),
            index = 0
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
