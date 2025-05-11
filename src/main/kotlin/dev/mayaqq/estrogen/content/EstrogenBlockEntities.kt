package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.client.content.blockRenderers.cookieJar.CookieJarRenderer
import dev.mayaqq.estrogen.client.content.blockRenderers.cookieJar.CookieJarVisual
import dev.mayaqq.estrogen.client.content.blockRenderers.dreamBlock.DreamBlockRenderer
import dev.mayaqq.estrogen.client.content.blockRenderers.dreamBlock.DreamBlockVisual
import dev.mayaqq.estrogen.content.blockEntities.CookieJarBlockEntity
import dev.mayaqq.estrogen.content.blockEntities.DreamBlockEntity
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.entity.BlockEntityType
import uwu.serenity.kritter.api.Registrar
import uwu.serenity.kritter.client.stdlib.renderer
import uwu.serenity.kritter.stdlib.blockEntity

object EstrogenBlockEntities : Registrar<BlockEntityType<*>> by Estrogen..Registries.BLOCK_ENTITY_TYPE {

    val DreamBlock: BlockEntityType<DreamBlockEntity> by blockEntity("dream_block", ::DreamBlockEntity) {
        validBlock(EstrogenBlocks::DreamBlock)
        renderer(::DreamBlockRenderer)
        visual(::DreamBlockVisual)
    }

    val CookieJar: BlockEntityType<CookieJarBlockEntity> by blockEntity("cookie_jar_block", ::CookieJarBlockEntity) {
        validBlock(EstrogenBlocks::CookieJar)
        renderer(::CookieJarRenderer)
        visual(::CookieJarVisual)
    }
}