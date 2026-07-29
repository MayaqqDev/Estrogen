@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package dev.mayaqq.estrogen.content.blocks

import dev.mayaqq.cynosure.events.PostInitEvent
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.utils.of
import dev.mayaqq.estrogen.config.EstrogenCommonConfig
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.EstrogenFluids
import dev.mayaqq.estrogen.content.EstrogenItems
import dev.mayaqq.estrogen.utils.defaultInstance
import invoke.kitty.kritter.registry.api.entry.RegistryEntry
import net.minecraft.core.BlockPos
import net.minecraft.core.cauldron.CauldronInteraction
import net.minecraft.core.cauldron.CauldronInteraction.emptyBucket
import net.minecraft.core.cauldron.CauldronInteraction.fillBucket
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.*
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.LayeredCauldronBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.gameevent.GameEvent

@EventSubscriber
object CauldronInteractions {
    private val PRE_HORSE_URINE by lazy { createMap(
        Items.BUCKET to bucket { EstrogenFluids.HorseUrine.bucket },
        Items.GLASS_BOTTLE to fillBottle(),
        EstrogenItems.HorseUrineBottle.get() to emptyBottle(EstrogenBlocks.HorseUrineCauldron)
    ) }
    private val PRE_FILTRATED_HORSE_URINE by lazy { createMap(
        Items.BUCKET to bucket { EstrogenFluids.FiltratedHorseUrine.bucket }
    ) }
    private val PRE_ESTROGEN by lazy { createMap(
        Items.BUCKET to bucket { EstrogenFluids.LiquidEstrogen.bucket },
        Items.COOKIE to cookie(),
        EstrogenItems.EstrogenPatch.get() to fillEstrogenPatch()
    ) }
    private val PRE_WATER by lazy { createMap() }
    private val PRE_EMPTY by lazy { createMap(
        EstrogenFluids.HorseUrine.bucket to fill(EstrogenBlocks.HorseUrineCauldron),
        EstrogenFluids.FiltratedHorseUrine.bucket to fill(EstrogenBlocks.FiltratedHorseUrineCauldron),
        EstrogenFluids.LiquidEstrogen.bucket to fill(EstrogenBlocks.LiquidEstrogenCauldron),
        EstrogenItems.HorseUrineBottle.get() to emptyBottle(EstrogenBlocks.HorseUrineCauldron)
    ) }

    val HORSE_URINE: CauldronInteraction.InteractionMap = CauldronInteraction.newInteractionMap("horse_urine")
    val FILTRATED_HORSE_URINE: CauldronInteraction.InteractionMap = CauldronInteraction.newInteractionMap("filtrated_horse_urine")
    val ESTROGEN: CauldronInteraction.InteractionMap = CauldronInteraction.newInteractionMap("estrogen")

    private fun fill(block: RegistryEntry<Block>): CauldronInteraction = object : RichCauldronInteraction {
        override val expectedOutput: ItemStack get() = Items.BUCKET.defaultInstance
        override val enabled get() = EstrogenCommonConfig.Recipes.cauldronInteractions

        override fun interact(
            state: BlockState,
            level: Level,
            pos: BlockPos,
            player: Player,
            hand: InteractionHand,
            stack: ItemStack
        ): ItemInteractionResult {
            if (!enabled) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
            return emptyBucket(level, pos, player, hand, stack,
                block.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3),
                SoundEvents.BUCKET_EMPTY
            )
        }
    }

    private fun fillEstrogenPatch() = object : RichCauldronInteraction {
        override val expectedOutput: ItemStack get() = EstrogenItems.EstrogenPatch.get().getFullStack()
        override val enabled: Boolean get() = EstrogenCommonConfig.Recipes.cauldronInteractions

        override fun interact(
            state: BlockState,
            level: Level,
            pos: BlockPos,
            player: Player,
            hand: InteractionHand,
            stack: ItemStack
        ): ItemInteractionResult {
            if (!enabled) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
            if (state.hasProperty(LayeredCauldronBlock.LEVEL) && state.getValue(LayeredCauldronBlock.LEVEL) != 3)
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
            if (!level.isClientSide) {
                val item: Item = stack.item
                player.setItemInHand(hand, EstrogenItems.EstrogenPatch.get().getFullStack())
                player.awardStat(Stats.USE_CAULDRON)
                player.awardStat(Stats.ITEM_USED.get(item))
                level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState())
                level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0f, 1.0f)
                level.gameEvent(null, GameEvent.FLUID_PICKUP, pos)
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide)
        }
    }

    private fun bucket(bucket: () -> BucketItem): CauldronInteraction = object : RichCauldronInteraction {
        override val expectedOutput: ItemStack get() = bucket.invoke().defaultInstance
        override val enabled: Boolean get() = EstrogenCommonConfig.Recipes.cauldronInteractions

        override fun interact(
            state: BlockState,
            level: Level,
            pos: BlockPos,
            player: Player,
            hand: InteractionHand,
            stack: ItemStack
        ): ItemInteractionResult {
            if (!enabled) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
            return fillBucket(
                state,
                level,
                pos,
                player,
                hand,
                stack,
                bucket.invoke().defaultInstance,
                { it.getValue(LayeredCauldronBlock.LEVEL) == 3 },
                SoundEvents.BUCKET_FILL
            )
        }
    }

    private fun emptyBottle(cauldron: () -> LayeredCauldronBlock): CauldronInteraction = object : RichCauldronInteraction {
        override val expectedOutput: ItemStack get() = Items.GLASS_BOTTLE.defaultInstance
        override val enabled: Boolean get() = EstrogenCommonConfig.Recipes.cauldronInteractions

        override fun interact(
            state: BlockState,
            level: Level,
            pos: BlockPos,
            player: Player,
            hand: InteractionHand,
            stack: ItemStack
        ): ItemInteractionResult {
            if (!enabled) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
            if (state.hasProperty(LayeredCauldronBlock.LEVEL) && state.getValue(LayeredCauldronBlock.LEVEL) == 3)
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
            if (!level.isClientSide) {
                val item: Item = stack.item
                player.setItemInHand(hand, ItemUtils.createFilledResult(
                    stack,
                    player,
                    ItemStack(Items.GLASS_BOTTLE)
                ))
                player.awardStat(Stats.USE_CAULDRON)
                player.awardStat(Stats.ITEM_USED.get(item))
                level.setBlockAndUpdate(pos,
                    if (state of cauldron.invoke()) state.cycle(LayeredCauldronBlock.LEVEL)
                    else cauldron.invoke().defaultBlockState()
                )
                level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f)
                level.gameEvent(null, GameEvent.FLUID_PLACE, pos)
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide)
        }
    }

    private fun fillBottle(): CauldronInteraction = object : RichCauldronInteraction {
        override val expectedOutput: ItemStack get() = EstrogenItems.HorseUrineBottle.defaultInstance()
        override val enabled: Boolean get() = EstrogenCommonConfig.Recipes.cauldronInteractions

        override fun interact(
            state: BlockState,
            level: Level,
            pos: BlockPos,
            player: Player,
            hand: InteractionHand,
            stack: ItemStack
        ): ItemInteractionResult {
            if (!enabled) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
            if (!level.isClientSide) {
                val item: Item = stack.item
                player.setItemInHand(
                    hand,
                    ItemUtils.createFilledResult(
                        stack,
                        player,
                        EstrogenItems.HorseUrineBottle.defaultInstance()
                    )
                )
                player.awardStat(Stats.USE_CAULDRON)
                player.awardStat(Stats.ITEM_USED.get(item))
                LayeredCauldronBlock.lowerFillLevel(state, level, pos)
                level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0f, 1.0f)
                level.gameEvent(null, GameEvent.FLUID_PICKUP, pos)
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide)
        }
    }

    private fun cookie(): CauldronInteraction = object : RichCauldronInteraction {
        override val expectedOutput: ItemStack get() = EstrogenItems.EstrogenPill.defaultInstance()
        override val enabled: Boolean get() = EstrogenCommonConfig.Recipes.cauldronInteractions

        override fun interact(
            state: BlockState,
            level: Level,
            pos: BlockPos,
            player: Player,
            hand: InteractionHand,
            stack: ItemStack
        ): ItemInteractionResult {
            if (!enabled) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
            if (!level.isClientSide) {
                val item: Item = stack.item
                player.setItemInHand(
                    hand,
                    ItemUtils.createFilledResult(
                        stack,
                        player,
                        EstrogenItems.EstrogenPill.defaultInstance()
                    )
                )
                player.awardStat(Stats.USE_CAULDRON)
                player.awardStat(Stats.ITEM_USED.get(item))
                LayeredCauldronBlock.lowerFillLevel(state, level, pos)
                level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0f, 1.0f)
                level.gameEvent(null, GameEvent.FLUID_PICKUP, pos)
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide)
        }

    }


    private fun createMap(vararg pairs: Pair<Item, CauldronInteraction>): MutableMap<Item, CauldronInteraction> {
        return object : LinkedHashMap<Item, CauldronInteraction>() {
            override fun get(key: Item): CauldronInteraction? {
                return super.get(key) ?: return CauldronInteraction { _, _, _, _, _, _ -> ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION }
            }
        }.apply { this.putAll(pairs) }
    }

    @Subscription
    fun registerAll(event: PostInitEvent) {
        CauldronInteraction.WATER.map().putAll(PRE_WATER)
        CauldronInteraction.EMPTY.map().putAll(PRE_EMPTY)
        ESTROGEN.map().putAll(PRE_ESTROGEN)
        HORSE_URINE.map().putAll(PRE_HORSE_URINE)
        FILTRATED_HORSE_URINE.map().putAll(PRE_FILTRATED_HORSE_URINE)
    }
}

interface RichCauldronInteraction : CauldronInteraction {
    val expectedOutput: ItemStack
    val enabled: Boolean
}

