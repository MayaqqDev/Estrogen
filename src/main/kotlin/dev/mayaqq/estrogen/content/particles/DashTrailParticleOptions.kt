package dev.mayaqq.estrogen.content.particles

import invoke.kitty.kritter.utils.color.Color
import java.util.*

data class DashTrailParticleOptions(val player: UUID, val color: Color) /*: CynosureParticleOptions<DashTrailParticleOptions> {

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
*/
