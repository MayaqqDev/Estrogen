@file:JvmName("EstrogenFabric")
package dev.mayaqq.estrogen.fabric

import com.mojang.blaze3d.vertex.VertexConsumer
import dev.mayaqq.cynosure.client.keymapping.KeyMappingRegistry
import dev.mayaqq.cynosure.entities.EntityAttributes
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.client.content.EstrogenKeybinds
import dev.mayaqq.estrogen.content.EstrogenFluids
import dev.mayaqq.estrogen.content.items.MothElytraItem
import invoke.kitty.kritter.mixin.client.ModelManagerMixin
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry
import net.fabricmc.fabric.api.client.model.loading.v1.FabricBakedModelManager
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents
import net.minecraft.client.model.AgeableHierarchicalModel
import net.minecraft.client.model.PlayerModel
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.particle.ParticleRenderType
import net.minecraft.client.particle.PlayerCloudParticle
import net.minecraft.client.renderer.entity.LivingEntityRenderer
import net.minecraft.client.resources.model.ModelManager
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ElytraItem.isFlyEnabled

fun init() {
    EstrogenFluids.fluidRegistry.init()
    EntityElytraEvents.CUSTOM.register { entity, elytraTick ->
        val stack = entity.getItemBySlot(EquipmentSlot.CHEST)
        if (stack.item is MothElytraItem) isFlyEnabled(stack) else false
    }
}