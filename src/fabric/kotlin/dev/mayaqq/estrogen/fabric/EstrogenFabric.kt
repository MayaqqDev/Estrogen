@file:JvmName("EstrogenFabric")
package dev.mayaqq.estrogen.fabric

import dev.mayaqq.cynosure.client.keymapping.KeyMappingRegistry
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.client.content.EstrogenKeybinds
import dev.mayaqq.estrogen.content.items.MothElytraItem
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents
import net.minecraft.client.particle.ParticleRenderType
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ElytraItem.isFlyEnabled

fun init() {
    EntityElytraEvents.CUSTOM.register { entity, elytraTick ->
        val stack = entity.getItemBySlot(EquipmentSlot.CHEST)
        if (stack.item is MothElytraItem) isFlyEnabled(stack) else false
    }
}