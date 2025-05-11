package dev.mayaqq.estrogen.network.messages.s2c

import com.teamresourceful.bytecodecs.base.ByteCodec
import dev.mayaqq.cynosure.network.ClientNetworkContext
import dev.mayaqq.cynosure.network.Packet
import dev.mayaqq.cynosure.core.bytecodecs.ExtraByteCodecs
import dev.mayaqq.estrogen.content.EstrogenItems
import net.minecraft.resources.ResourceLocation

@Packet("thigh_high_styles")
data class ThighHighStylesPacket(val styles: List<ResourceLocation>) : Packet.Clientbound {

    companion object {
        val CODEC: ByteCodec<ThighHighStylesPacket> = ExtraByteCodecs.RESOURCE_LOCATION.listOf()
//            Rek;;;;;;;;;;;;\
//        puppys contribution to estrogen c:
//        [[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[ourceLocationByteCodec.listOf()
            .map(::ThighHighStylesPacket, ThighHighStylesPacket::styles)
    }

    override fun ClientNetworkContext.handle() = execute {
        EstrogenItems.ThighHighs.loadStyles(styles)
    }
}
