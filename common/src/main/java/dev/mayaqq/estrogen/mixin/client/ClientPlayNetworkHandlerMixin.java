package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.registry.EstrogenEnchantments;
import dev.mayaqq.estrogen.registry.blockEntities.DreamBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.mayaqq.estrogen.utils.UwUfy.uwufyString;

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
            message = uwufyString(message);

        }
        return message;
    }

    @Inject(
            method = "handleBlockUpdate",
            at = @At("HEAD")
    )
    public void onNeighbourStateChange(ClientboundBlockUpdatePacket packet, CallbackInfo ci) {
        Level level = Minecraft.getInstance().level;
        if(level == null) return;

        for(Direction dir : Direction.values()) {
            BlockPos pos = packet.getPos().relative(dir);
            BlockEntity be = level.getBlockEntity(pos);
            if(be instanceof DreamBlockEntity dream) {
                dream.updateTexture(dir.getAxis() != Direction.Axis.Y);
            }


        }

    }
}
