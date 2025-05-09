package dev.mayaqq.estrogen.content.particles

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.teamresourceful.bytecodecs.base.ByteCodec
import com.teamresourceful.bytecodecs.base.`object`.ObjectByteCodec
import dev.mayaqq.cynosure.utils.codecs.fieldOf
import dev.mayaqq.cynosure.utils.colors.Color
import dev.mayaqq.cynosure.particles.CynosureParticleOptions
import dev.mayaqq.cynosure.particles.CynosureParticleType
import dev.mayaqq.estrogen.content.EstrogenParticles
import net.minecraft.core.UUIDUtil
import uwu.serenity.kritter.api.entry.getEntryOrThrow
import java.util.*

data class DashTrailParticleOptions(val player: UUID, val color: Color) : CynosureParticleOptions<DashTrailParticleOptions> {

    override fun getType(): CynosureParticleType<DashTrailParticleOptions> = EstrogenParticles.DashTrail

    override fun writeToString(): String = "${EstrogenParticles::DashTrail.getEntryOrThrow().key.location()} ($player, $color)"

    companion object {

        val CODEC: Codec<DashTrailParticleOptions> = RecordCodecBuilder.create { it.group(
            UUIDUtil.STRING_CODEC fieldOf DashTrailParticleOptions::player,
            Color.CODEC fieldOf DashTrailParticleOptions::color
        ).apply(it, ::DashTrailParticleOptions) }

        val NETWORK_CODEC: ByteCodec<DashTrailParticleOptions> = ObjectByteCodec.create(
            ByteCodec.UUID fieldOf DashTrailParticleOptions::player,
            Color.NETWORK_CODEC fieldOf DashTrailParticleOptions::color,
            ::DashTrailParticleOptions
        )
    }
}
