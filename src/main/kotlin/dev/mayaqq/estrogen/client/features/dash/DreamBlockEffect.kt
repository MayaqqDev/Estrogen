@file:Suppress("unused")

package dev.mayaqq.estrogen.client.features.dash

import dev.mayaqq.cynosure.client.events.ClientTickEvent
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.utils.Environment
import dev.mayaqq.estrogen.client.content.sounds.DreamBlockSoundInstance
import dev.mayaqq.estrogen.content.EstrogenSounds
import dev.mayaqq.estrogen.content.blocks.DreamBlock
import net.minecraft.client.Minecraft

@EventSubscriber(env = [Environment.CLIENT])
object DreamBlockEffect {
    private var sound: DreamBlockSoundInstance? = null
    var isInDreamBlock = false
        private set
    private var dreamBlockTick = 0

    @Subscription
    fun tick(event: ClientTickEvent.End) {
        val player = Minecraft.getInstance().player ?: return

        if (DreamBlock.isInDreamBlock(player)) {
            dreamBlockTick++
            if (dreamBlockTick == 1) {
                player.playSound(EstrogenSounds.DREAM_BLOCK_ENTER, 1.0f, 1.0f)
            } else if (dreamBlockTick == 2) {
                if (sound == null) {
                    sound = DreamBlockSoundInstance(player)
                    Minecraft.getInstance().soundManager.play(sound!!)
                }
            }
            isInDreamBlock = true
        } else {
            if (isInDreamBlock) {
                player.playSound(EstrogenSounds.DREAM_BLOCK_EXIT, 1.0f, 1.0f)
            }
            if (sound != null) {
                Minecraft.getInstance().soundManager.stop(sound!!)
                sound = null
            }
            dreamBlockTick = 0
            isInDreamBlock = false
        }
    }
}