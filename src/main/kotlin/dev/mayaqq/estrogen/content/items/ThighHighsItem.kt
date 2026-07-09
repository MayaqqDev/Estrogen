package dev.mayaqq.estrogen.content.items

import com.google.common.collect.Multimap
import dev.mayaqq.cynosure.text.Text
import dev.mayaqq.cynosure.text.TextStyle.color
import dev.mayaqq.cynosure.text.TextStyle.italic
import dev.mayaqq.estrogen.api.item.equip.Equip
import dev.mayaqq.estrogen.api.item.equip.SlotInfo
import dev.mayaqq.estrogen.config.EstrogenServerConfig
import dev.mayaqq.estrogen.content.EstrogenAttributes
import dev.mayaqq.estrogen.content.EstrogenComponents.ThighHighColorComponent
import dev.mayaqq.estrogen.content.EstrogenComponents.ThighHighStyleComponent
import dev.mayaqq.estrogen.content.components.ThighHighColor
import dev.mayaqq.estrogen.content.components.ThighHighStyle
import dev.mayaqq.estrogen.network.EstrogenNetwork
import dev.mayaqq.estrogen.network.messages.s2c.ThighHighStylesPacket
import dev.mayaqq.estrogen.utils.holder
import invoke.kitty.kritter.registry.api.entry.holder
import invoke.kitty.kritter.utils.color.Color
import invoke.kitty.kritter.utils.color.MinecraftColors
import net.minecraft.core.Holder
import net.minecraft.core.cauldron.CauldronInteraction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.util.RandomSource
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.block.LayeredCauldronBlock

class ThighHighsItem(properties: Properties, val primaryColor: Color, val secondaryColor: Color) : Item(properties), Equip {
    private val styles = mutableListOf<ResourceLocation>()

    fun loadStyles(styles: List<ResourceLocation>) {
        this.styles.clear()
        this.styles.addAll(styles)
    }

    fun syncStyles(player: ServerPlayer) {
        EstrogenNetwork.sendToPlayer(player, ThighHighStylesPacket(styles))
    }

    fun getDefaultColor(tintIndex: Int): Color {
        return if (tintIndex == 0) primaryColor else secondaryColor
    }

    fun hasCustomColor(stack: ItemStack): Boolean {
        return stack.get(ThighHighColorComponent) != null
    }

    fun getColor(stack: ItemStack, tintIndex: Int): Color {
        stack.get(ThighHighColorComponent)?.let { color ->
            when(tintIndex) {
                0 -> return color.primary
                1 -> return color.secondary
            }
        }
        return getDefaultColor(tintIndex)
    }

    fun clearColor(stack: ItemStack) {
        stack.remove(ThighHighColorComponent)
    }

    fun setColor(stack: ItemStack, primary: Color, secondary: Color) {
        stack.set(ThighHighColorComponent, ThighHighColor(primary, secondary))
    }

    fun setStyle(stack: ItemStack, style: ResourceLocation) {
        stack.set(ThighHighStyleComponent, ThighHighStyle(style))
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
        return stack.get(ThighHighStyleComponent)?.style
    }

    fun clearStyle(stack: ItemStack) {
        stack.remove(ThighHighStyleComponent)
    }

    override fun appendHoverText(stack: ItemStack, context: TooltipContext, list: MutableList<Component>, flags: TooltipFlag) {
        getStyle(stack)?.let { style ->
            list.add(1,
                Component.translatable(style.toLanguageKey("tooltip.thigh_highs"))
            )} ?: {
            list.add(1,
                Text.translatable(if (hasCustomColor(stack)) "item.dyed" else "estrogen.item.dyeable") {
                    color = MinecraftColors.Gray;
                    italic = true
                }
            )
        }
    }

     override fun getAttributeModifiers(default: Multimap<Holder<Attribute>, AttributeModifier>, stack: ItemStack, slot: SlotInfo, id: ResourceLocation): Multimap<Holder<Attribute>, AttributeModifier> {
        default.put(
            EstrogenAttributes.FallDamageResistance.holder,
            AttributeModifier(id, EstrogenServerConfig.ThighHighs.fallDamageReduction.toDouble(), AttributeModifier.Operation.ADD_VALUE)
        )
        return default
    }

    companion object {
        fun getItemColor(stack: ItemStack, tintIndex: Int): Int {
            val item = stack.item as ThighHighsItem
            if (item.getStyle(stack) != null) return -1
            return item.getColor(stack, tintIndex).toInt()
        }

        val CAULDRON_INTERACTION: CauldronInteraction = CauldronInteraction { blockState, level, blockPos, player, _, itemStack ->
                val item = itemStack.item
                if (item !is ThighHighsItem || !item.hasCustomColor(itemStack))
                    return@CauldronInteraction ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION

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
            ItemInteractionResult.sidedSuccess(level.isClientSide)
            }
    }
}