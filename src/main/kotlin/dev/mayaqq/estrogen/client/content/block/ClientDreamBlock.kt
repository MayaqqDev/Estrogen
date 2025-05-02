package dev.mayaqq.estrogen.client.content.block

import dev.mayaqq.cynosure.Cynosure
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.events.world.LevelEvent
import dev.mayaqq.cynosure.utils.Environment
import dev.mayaqq.estrogen.client.features.TextRendererFeatures
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.content.blockEntities.DreamBlockEntity
import dev.mayaqq.estrogen.content.blocks.DreamBlock
import dev.mayaqq.estrogen.mixin.client.accessor.LevelRendererAccessor
import it.unimi.dsi.fastutil.longs.LongOpenHashSet
import net.minecraft.client.Minecraft
import net.minecraft.core.SectionPos
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.level.ChunkPos

@EventSubscriber(env = [Environment.CLIENT])
object ClientDreamBlock {

    private var rebuildDreamChunks = false

    @Subscription
    fun tick(event: LevelEvent.EndTick) {

        if (TextRendererFeatures.obfuscate != Minecraft.getInstance().player?.hasEffect(EstrogenEffects.DREAMING)) {
            rebuildDreamChunks = true
        }

        if (event.isClientSide && rebuildDreamChunks) {
            rebuildDreamChunks = false

            DreamBlockEntity.CHUNKS.toCollection(LongOpenHashSet()).longIterator().forEach {
                (Minecraft.getInstance().levelRenderer as LevelRendererAccessor)
                    .invokeSetSetionDirty(SectionPos.x(it), SectionPos.y(it), SectionPos.z(it))
            }
        }
    }

    public fun setChanged() {
        rebuildDreamChunks = true
    }
}