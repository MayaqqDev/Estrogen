package dev.mayaqq.estrogen.content.items

import com.google.common.collect.Multimap
import dev.mayaqq.estrogen.content.EstrogenAttributes
import dev.mayaqq.estrogen.network.EstrogenNetwork
import dev.mayaqq.estrogen.network.messages.s2c.ThighHighStylesPacket
import earth.terrarium.baubly.common.Bauble
import earth.terrarium.baubly.common.SlotInfo
import net.minecraft.ChatFormatting
import net.minecraft.core.cauldron.CauldronInteraction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.LayeredCauldronBlock
import java.util.*

class ThighHighsItem(properties: Properties, val primaryColor: Int, val secondaryColor: Int) : Item(properties), Bauble {
    private val styles = mutableListOf<ResourceLocation>()

    fun loadStyles(styles: List<ResourceLocation>) {
        this.styles.clear()
        this.styles.addAll(styles)
    }

    fun syncStyles(player: ServerPlayer) {
        EstrogenNetwork.sendToPlayer(ThighHighStylesPacket(styles), player)
    }

    fun getDefaultColor(tintIndex: Int): Int {
        return if (tintIndex == 0) primaryColor else secondaryColor
    }

    fun hasCustomColor(stack: ItemStack): Boolean {
        return stack.tag?.contains(TAG_PRIMARY) == true && stack.tag?.contains(TAG_SECONDARY) == true
    }

    fun getColor(stack: ItemStack, tintIndex: Int): Int {
        stack.tag?.let {
            if (tintIndex == 0 && it.contains(TAG_PRIMARY)) return it.getInt(TAG_PRIMARY)
            if (tintIndex == 1 && it.contains(TAG_SECONDARY)) return it.getInt(TAG_SECONDARY)
        }
        return getDefaultColor(tintIndex)
    }

    fun clearColor(stack: ItemStack) {
        stack.tag?.let {
            it.remove(TAG_PRIMARY)
            it.remove(TAG_SECONDARY)
        }
    }

    fun setColor(stack: ItemStack, primaryColor: Int, secondaryColor: Int) {
        stack.orCreateTag.let {
            it.putInt(TAG_PRIMARY, primaryColor)
            it.putInt(TAG_SECONDARY, secondaryColor)
        }
    }

    fun setStyle(stack: ItemStack, style: ResourceLocation) {
        stack.orCreateTag.putString(SPECIAL_STYLE, style.toString())
    }

    fun setRandomStyle(stack: ItemStack, randomSource: RandomSource) {
        setStyle(stack, styles[randomSource.nextInt(styles.size)])
    }

    val styleItems: Sequence<ItemStack>  get() {
        if (styles.isEmpty()) return emptySequence()
        return styles.asSequence().map { style ->
            defaultInstance.apply { setStyle(this, style) }
        }
    }

    fun getStyle(stack: ItemStack): ResourceLocation? {
        if (styles.isEmpty()) return null
        stack.tag?.let { it ->
            if (it.contains(SPECIAL_STYLE)) {
                ResourceLocation(it.getString(SPECIAL_STYLE)).let {
                    if (styles.contains(it)) return it
                }
            }
        }
        return null
    }

    fun clearStyle(stack: ItemStack) {
        stack.tag?.remove(SPECIAL_STYLE)
    }

    override fun appendHoverText(stack: ItemStack, level: Level?, list: MutableList<Component>, flags: TooltipFlag) {
        getStyle(stack)?.let {
            list.add(1, Component.translatable(it.toLanguageKey("tooltip.thigh_highs")))
        }?: list.add(1, Component.translatable("item.dyed").withStyle(
            ChatFormatting.GRAY,
            ChatFormatting.ITALIC
        ))
    }

    override fun getModifiers(defaultModifiers: Multimap<Attribute, AttributeModifier>, stack: ItemStack, slot: SlotInfo, uuid: UUID): Multimap<Attribute, AttributeModifier> {
        defaultModifiers.put(
            EstrogenAttributes.FallDamageResistance,
            AttributeModifier(uuid, "ThighHighsFallDamageResistance", 100.0, AttributeModifier.Operation.ADDITION)
        )
        return defaultModifiers
    }

    companion object {
        const val TAG_PRIMARY = "primaryColor"
        const val TAG_SECONDARY = "secondaryColor"
        const val SPECIAL_STYLE = "specialStyle"

        fun getItemColor(stack: ItemStack, tintIndex: Int): Int {
            val item = stack.item as ThighHighsItem
            if (item.getStyle(stack) != null) return -1
            return item.getColor(stack, tintIndex)
        }

        val CAULDRON_INTERACTION: CauldronInteraction = CauldronInteraction { blockState, level, blockPos, player, _, itemStack ->
                val item = itemStack.item
                if (item !is ThighHighsItem || !item.hasCustomColor(itemStack))
                    return@CauldronInteraction InteractionResult.PASS

                if (!level.isClientSide) {
                    item.clearColor(itemStack)
                    player.awardStat(Stats.CLEAN_ARMOR)
                    level.playSound(null, blockPos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 0.5f, 1.8f)
                    LayeredCauldronBlock.lowerFillLevel(blockState, level, blockPos)
                } else {
                    val fillHeight = blockState.getValue(LayeredCauldronBlock.LEVEL) / 3f
                    for (i in 0..7) {
                        val xOff = level.random.nextGaussian() / 5 + 0.5
                        val zOff = level.random.nextGaussian() / 5 + 0.5

                        level.addParticle(
                            ParticleTypes.BUBBLE_POP,
                            blockPos.x + xOff,
                            blockPos.y + fillHeight * 0.8,
                            blockPos.z + zOff,
                            0.0,
                            0.05,
                            0.0
                        )
                    }
                }

                InteractionResult.sidedSuccess(level.isClientSide)
            }
    }
}