package dev.mayaqq.estrogen.content.blocks

import dev.mayaqq.estrogen.content.EstrogenBlockEntities
import dev.mayaqq.estrogen.content.blockEntities.DreamCatcherBlockEntity
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.entity.BlockEntityType
import uwu.serenity.kritter.stdlib.BlockEntityBlock
import kotlin.reflect.KClass

class DreamCatcherBlock(properties: Properties) : BaseEntityBlock(properties), BlockEntityBlock<DreamCatcherBlockEntity> {

    override val blockEntityClass: KClass<out DreamCatcherBlockEntity> = DreamCatcherBlockEntity::class
    override fun getBlockEntityType(): BlockEntityType<out DreamCatcherBlockEntity> = EstrogenBlockEntities.DreamCatcher
}