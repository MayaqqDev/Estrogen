@file:JvmName("EstrogenFabric")
package dev.mayaqq.estrogen.fabric

import dev.mayaqq.cynosure.utils.tag
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.content.EstrogenEntities
import dev.mayaqq.estrogen.content.EstrogenTags
import dev.mayaqq.estrogen.content.items.MothElytraItem
import dev.mayaqq.estrogen.id
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.item.ElytraItem.isFlyEnabled
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.placement.PlacedFeature

fun init() {
    Estrogen.init()
    EntityElytraEvents.CUSTOM.register { entity, elytraTick ->
        val stack = entity.getItemBySlot(EquipmentSlot.CHEST)
        if (stack.item is MothElytraItem) isFlyEnabled(stack) else false
    }
}