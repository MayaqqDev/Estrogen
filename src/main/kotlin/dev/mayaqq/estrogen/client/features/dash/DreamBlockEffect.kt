@file:Suppress("unused")

package dev.mayaqq.estrogen.client.features.dash

import dev.mayaqq.cynosure.client.events.ClientTickEvent
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.estrogen.client.content.sounds.DreamBlockSoundInstance
import dev.mayaqq.estrogen.content.EstrogenSounds
import dev.mayaqq.estrogen.content.blockEntities.DreamBlockEntity
import dev.mayaqq.estrogen.content.blocks.DreamBlock
import invoke.kitty.kritter.platform.Side
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.world.phys.Vec3
import kotlin.math.floor

@EventSubscriber(Side.CLIENT)
object DreamBlockEffect {
    private var sound: DreamBlockSoundInstance? = null
    var isInDreamBlock = false
        private set
    var isEyeInDream = false
        private set
    private var dreamBlockTick = 0
    var eyeDreamTick = 0
        private set

    @Subscription
    fun tick(event: ClientTickEvent.End) {
        val player = Minecraft.getInstance().player ?: return

        if (DreamBlock.isInDreamBlock(player)) {
            dreamBlockTick++
            if (dreamBlockTick == 1) {
                player.playSound(EstrogenSounds.DREAM_BLOCK_ENTER.value, 1.0f, 1.0f)
            } else if (dreamBlockTick == 2) {
                if (sound == null) {
                    sound = DreamBlockSoundInstance(player)
                    Minecraft.getInstance().soundManager.play(sound!!)
                }
            }
            isInDreamBlock = true
            isEyeInDream = player.clientLevel.getBlockEntity(player.eyePosition.toBlockPos()) is DreamBlockEntity
            if (isEyeInDream) {
                eyeDreamTick++
            } else eyeDreamTick = 0
        } else {
            if (isInDreamBlock) {
                player.playSound(EstrogenSounds.DREAM_BLOCK_EXIT.value, 1.0f, 1.0f)
            }
            if (sound != null) {
                Minecraft.getInstance().soundManager.stop(sound!!)
                sound = null
            }
            dreamBlockTick = 0
            eyeDreamTick = 0
            isInDreamBlock = false
            isEyeInDream = false
        }
    }

    // Remove this when cynosure pr is approved...!
    private fun Vec3.toBlockPos(): BlockPos {
        return BlockPos(floor(this.x).toInt(), floor(this.y).toInt(), floor(this.z).toInt())
    }
}