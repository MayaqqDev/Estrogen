package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.datagen.tags.EstrogenTags;
import dev.mayaqq.estrogen.registry.common.EstrogenEnchantments;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.mayaqq.estrogen.utils.UwUfy.uwufyString;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {

    @Unique
    private String message;

    @ModifyArg(
            method = "sendChatMessage(Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V"
            ),
            index = 0
    )
    private Packet<?> modifyMessage(Packet<?> packet) {
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
        return new ChatMessageC2SPacket(message);
    }

    @Inject(
            method = "sendChatMessage(Ljava/lang/String;)V",
            at = @At(
                    value = "HEAD"
            )
    )
    private void saveMessage(String message, CallbackInfo ci) {
        this.message = message;
    }
}
