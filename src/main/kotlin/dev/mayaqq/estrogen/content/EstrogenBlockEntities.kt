package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.client.content.blockRenderers.cookieJar.CookieJarRenderer
import dev.mayaqq.estrogen.client.content.blockRenderers.cookieJar.CookieJarVisual
import dev.mayaqq.estrogen.client.content.blockRenderers.dreamBlock.DreamBlockRenderer
import dev.mayaqq.estrogen.client.content.blockRenderers.dreamBlock.DreamBlockVisual
import dev.mayaqq.estrogen.client.content.blockRenderers.memorial.MemorialRenderer
import dev.mayaqq.estrogen.content.blockEntities.CookieJarBlockEntity
import dev.mayaqq.estrogen.content.blockEntities.DreamBlockEntity
import dev.mayaqq.estrogen.content.blockEntities.DreamCatcherBlockEntity
import dev.mayaqq.estrogen.content.blockEntities.MemorialBlockEntity
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.api.entry.RegistryEntry
import invoke.kitty.kritter.registry.blockEntity.blockEntity
import invoke.kitty.kritter.registry.blockEntity.renderer
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.entity.BlockEntityType

object EstrogenBlockEntities : Registrar<BlockEntityType<*>> by Registrar(MOD_ID, Registries.BLOCK_ENTITY_TYPE) {

    val DreamBlock: RegistryEntry<BlockEntityType<DreamBlockEntity>> = blockEntity("dream_block", ::DreamBlockEntity) {
        validBlock { EstrogenBlocks.DreamBlock.value!! }
        renderer(::DreamBlockRenderer)
        visual(::DreamBlockVisual) { false }
    }

    val CookieJar: RegistryEntry<BlockEntityType<CookieJarBlockEntity>> = blockEntity("cookie_jar", ::CookieJarBlockEntity) {
        validBlock { EstrogenBlocks.CookieJar.value!! }
        renderer(::CookieJarRenderer)
        visual(::CookieJarVisual) { false }
    }

    val DreamCatcher: RegistryEntry<BlockEntityType<DreamCatcherBlockEntity>> = blockEntity("dreamcatcher", ::DreamCatcherBlockEntity) {
        validBlock { EstrogenBlocks.DreamCatcher.value!! }
    }

    val Memorial: RegistryEntry<BlockEntityType<MemorialBlockEntity>> = blockEntity("memorial", ::MemorialBlockEntity) {
        validBlock { EstrogenBlocks.Memorial.value!! }
        renderer(::MemorialRenderer)
    }
}