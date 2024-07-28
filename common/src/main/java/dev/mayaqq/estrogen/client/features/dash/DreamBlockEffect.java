package dev.mayaqq.estrogen.client.features.dash;

import dev.mayaqq.estrogen.registry.EstrogenSounds;
import dev.mayaqq.estrogen.registry.blocks.DreamBlock;
import dev.mayaqq.estrogen.registry.sounds.DreamBlockSoundInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public class DreamBlockEffect {

    private static DreamBlockSoundInstance sound = null;
    private static boolean isInDreamBlock = false;
    private static int dreamBlockTick = 0;

    public static void tick() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        if (DreamBlock.isInDreamBlock(player)) {
            dreamBlockTick++;
            if (dreamBlockTick == 1) {
                player.playSound(EstrogenSounds.DREAM_BLOCK_ENTER.get(), 1.0F, 1.0F);
            } else if (dreamBlockTick == 2) {
                if (sound == null) {
                    sound = new DreamBlockSoundInstance(player);
                }
                Minecraft.getInstance().getSoundManager().play(sound);
            }
            isInDreamBlock = true;
        } else {
            if (isInDreamBlock) {
                player.playSound(EstrogenSounds.DREAM_BLOCK_EXIT.get(), 1.0F, 1.0F);
            }
            if (sound != null) {
                Minecraft.getInstance().getSoundManager().stop(sound);
                sound = null;
            }
            dreamBlockTick = 0;
            isInDreamBlock = false;
        }
    }

    public static boolean isInDreamBlock() {
        return isInDreamBlock;
    }
}
