package dev.mayaqq.estrogen.client.content

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import dev.mayaqq.cynosure.client.events.CoreShaderRegistrationEvent
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.modId
import net.minecraft.client.renderer.ShaderInstance

@EventSubscriber
object EstrogenRenderer {

    lateinit var dreamBlockShader: ShaderInstance
        private set

    @Subscription
    fun onLoadShaders(event: CoreShaderRegistrationEvent) {
        event.register(modId("dream_block"), DefaultVertexFormat.BLOCK, ::dreamBlockShader)
    }
}