package dev.mayaqq.estrogen.content.blocks

import dev.mayaqq.cynosure.events.PostInitEvent
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.utils.of
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.EstrogenFluids
import dev.mayaqq.estrogen.content.EstrogenItems
import earth.terrarium.botarium.common.registry.fluid.FluidBucketItem
import net.minecraft.core.cauldron.CauldronInteraction
import net.minecraft.core.cauldron.CauldronInteraction.emptyBucket
import net.minecraft.core.cauldron.CauldronInteraction.fillBucket
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.ItemUtils
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.LayeredCauldronBlock
import net.minecraft.world.level.gameevent.GameEvent

@EventSubscriber
object CauldronInteractions {
    private val PRE_HORSE_URINE by lazy { createMap(
        Items.BUCKET to bucket { EstrogenFluids.HorseUrine.bucket },
        Items.GLASS_BOTTLE to fillBottle(),
        EstrogenItems.HorseUrineBottle to emptyBottle { EstrogenBlocks.HorseUrineCauldron }
    ) }
    private val PRE_FILTRATED_HORSE_URINE by lazy { createMap(
        Items.BUCKET to bucket { EstrogenFluids.FiltratedHorseUrine.bucket }
    ) }
    private val PRE_ESTROGEN by lazy { createMap(
        Items.BUCKET to bucket { EstrogenFluids.LiquidEstrogen.bucket },
        Items.COOKIE to cookie(),
        EstrogenItems.EstrogenPatches to fillEstrogenPatch()
    ) }
    private val PRE_WATER by lazy { createMap() }
    private val PRE_EMPTY by lazy { createMap(
        EstrogenFluids.HorseUrine.bucket to fill { EstrogenBlocks.HorseUrineCauldron },
        EstrogenFluids.FiltratedHorseUrine.bucket to fill { EstrogenBlocks.FiltratedHorseUrineCauldron },
        EstrogenFluids.LiquidEstrogen.bucket to fill { EstrogenBlocks.LiquidEstrogenCauldron },
        EstrogenItems.HorseUrineBottle to emptyBottle { EstrogenBlocks.HorseUrineCauldron }
    ) }

    val HORSE_URINE = createMap()
    val FILTRATED_HORSE_URINE = createMap()
    val ESTROGEN = createMap()

    private fun fill(block: () -> LayeredCauldronBlock): CauldronInteraction = CauldronInteraction { state, level, pos, player, hand, stack ->
        emptyBucket(level, pos, player, hand, stack,
            block.invoke().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3),
            SoundEvents.BUCKET_EMPTY
        )
    }

    private fun fillEstrogenPatch() = CauldronInteraction { state, level, pos, player, hand, stack ->
        if (state.hasProperty(LayeredCauldronBlock.LEVEL) && state.getValue(LayeredCauldronBlock.LEVEL) != 3)
            return@CauldronInteraction InteractionResult.PASS
        if (!level.isClientSide) {
            val item: Item = stack.item
            player.setItemInHand(hand, EstrogenItems.EstrogenPatches.getFullStack())
            player.awardStat(Stats.USE_CAULDRON)
            player.awardStat(Stats.ITEM_USED.get(item))
            level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState())
            level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0f, 1.0f)
            level.gameEvent(null, GameEvent.FLUID_PICKUP, pos)
        }

        InteractionResult.sidedSuccess(level.isClientSide)
    }

    private fun bucket(bucket: () -> FluidBucketItem): CauldronInteraction = CauldronInteraction { state, level, pos, player, hand, stack ->
        fillBucket(
            state,
            level,
            pos,
            player,
            hand,
            stack,
            bucket.invoke().defaultInstance,
            {it.getValue(LayeredCauldronBlock.LEVEL) == 3},
            SoundEvents.BUCKET_FILL
        )
    }

    private fun emptyBottle(cauldron: () -> LayeredCauldronBlock): CauldronInteraction = CauldronInteraction { state, level, pos, player, hand, stack ->
        if (state.hasProperty(LayeredCauldronBlock.LEVEL) && state.getValue(LayeredCauldronBlock.LEVEL) == 3)
            return@CauldronInteraction InteractionResult.PASS
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

        InteractionResult.sidedSuccess(level.isClientSide)
    }

    private fun fillBottle(): CauldronInteraction = CauldronInteraction { state, level, pos, player, hand, stack ->
        if (!level.isClientSide) {
            val item: Item = stack.item
            player.setItemInHand(
                hand,
                ItemUtils.createFilledResult(
                    stack,
                    player,
                    EstrogenItems.HorseUrineBottle.defaultInstance
                )
            )
            player.awardStat(Stats.USE_CAULDRON)
            player.awardStat(Stats.ITEM_USED.get(item))
            LayeredCauldronBlock.lowerFillLevel(state, level, pos)
            level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0f, 1.0f)
            level.gameEvent(null, GameEvent.FLUID_PICKUP, pos)
        }
        InteractionResult.sidedSuccess(level.isClientSide)
    }

    private fun cookie(): CauldronInteraction = CauldronInteraction { state, level, pos, player, hand, stack ->
        if (!level.isClientSide) {
            val item: Item = stack.item
            player.setItemInHand(
                hand,
                ItemUtils.createFilledResult(
                    stack,
                    player,
                    EstrogenItems.EstrogenPill.defaultInstance
                )
            )
            player.awardStat(Stats.USE_CAULDRON)
            player.awardStat(Stats.ITEM_USED.get(item))
            LayeredCauldronBlock.lowerFillLevel(state, level, pos)
            level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0f, 1.0f)
            level.gameEvent(null, GameEvent.FLUID_PICKUP, pos)
        }
        InteractionResult.sidedSuccess(level.isClientSide)
    }


    private fun createMap(vararg pairs: Pair<Item, CauldronInteraction>): MutableMap<Item, CauldronInteraction> =
        object : LinkedHashMap<Item, CauldronInteraction>() {
            override fun get(key: Item): CauldronInteraction? {
                return super.get(key) ?: return CauldronInteraction { _, _, _, _, _, _ -> InteractionResult.PASS }
            }
        }.apply { this.putAll(pairs) }

    @Subscription
    fun registerAll(event: PostInitEvent) {
        CauldronInteraction.WATER.putAll(PRE_WATER)
        CauldronInteraction.EMPTY.putAll(PRE_EMPTY)
        ESTROGEN.putAll(PRE_ESTROGEN)
        HORSE_URINE.putAll(PRE_HORSE_URINE)
        FILTRATED_HORSE_URINE.putAll(PRE_FILTRATED_HORSE_URINE)
    }
}

