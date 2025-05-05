package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.cynosure.utils.fun.UwUfy;
import dev.mayaqq.estrogen.client.content.sounds.MothFlyingSoundInstance;
import dev.mayaqq.estrogen.content.EstrogenEnchantments;
import dev.mayaqq.estrogen.content.entities.MothEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @Shadow @Final private Minecraft minecraft;

    /**
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
        if (player != null && EnchantmentHelper.getEnchantments(player.getInventory().getArmor(3)).containsKey(EstrogenEnchantments.INSTANCE.getUWUFYING_CURSE())) {
            message = UwUfy.uwufy(message);

        }
        return message;
    }

    /**
     * This mixin adds a MothFlyingSoundInstance to the sound manager when a moth entity is added.
     */
    @Inject(method = "postAddEntitySoundInstance", at = @At("HEAD"))
    private void postAddEntitySoundInstanceMixin(Entity entity, CallbackInfo ci) {
        if (entity instanceof MothEntity moth) {
            minecraft.getSoundManager().queueTickingSound(new MothFlyingSoundInstance(moth));
        }
    }
}
