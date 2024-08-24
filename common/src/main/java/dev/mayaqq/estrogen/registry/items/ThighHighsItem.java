package dev.mayaqq.estrogen.registry.items;

import com.google.common.collect.Multimap;
import dev.mayaqq.estrogen.networking.EstrogenNetworkManager;
import dev.mayaqq.estrogen.networking.messages.s2c.ThighHighStylesPacket;
import dev.mayaqq.estrogen.registry.EstrogenAttributes;
import earth.terrarium.baubly.Baubly;
import earth.terrarium.baubly.common.Bauble;
import earth.terrarium.baubly.common.SlotInfo;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ThighHighsItem extends Item implements Bauble {
    private final int primaryColorDefault;
    private final int secondaryColorDefault;
    private List<ResourceLocation> styles;
    public static final String TAG_PRIMARY = "primaryColor";
    public static final String TAG_SECONDARY = "secondaryColor";
    public static final String TAG_STYLE = "specialStyle";

    public ThighHighsItem(Properties properties, int primaryColor, int secondaryColor) {
        super(properties);
        this.primaryColorDefault = primaryColor;
        this.secondaryColorDefault = secondaryColor;
        Baubly.registerBauble(this);
    }

    public void loadStyles(List<ResourceLocation> newStyles) {
        styles = newStyles;
    }

    public void syncStyles(ServerPlayer player) {
        EstrogenNetworkManager.CHANNEL.sendToPlayer(new ThighHighStylesPacket(styles), player);
    }

    public int getDefaultColor(int tintIndex) {
        return tintIndex == 0 ? this.primaryColorDefault : this.secondaryColorDefault;
    }

    public boolean hasCustomColor(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        return compoundTag != null && compoundTag.contains(TAG_PRIMARY) && compoundTag.contains(TAG_SECONDARY);
    }

    public int getColor(ItemStack stack, int tintIndex) {
        CompoundTag compoundTag = stack.getTag();
        if (compoundTag != null) {
            if (tintIndex == 0 && compoundTag.contains(TAG_PRIMARY)) {
                return compoundTag.getInt(TAG_PRIMARY);
            } else if (tintIndex == 1 && compoundTag.contains(TAG_SECONDARY)) {
                return compoundTag.getInt(TAG_SECONDARY);
            }
        }
        return getDefaultColor(tintIndex);
    }

    public void clearColor(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        if (compoundTag != null) {
            compoundTag.remove(TAG_PRIMARY);
            compoundTag.remove(TAG_SECONDARY);
        }
    }

    public void setColor(ItemStack stack, int primaryColor, int secondaryColor) {
        stack.getOrCreateTag().putInt(TAG_PRIMARY, primaryColor);
        stack.getOrCreateTag().putInt(TAG_SECONDARY, secondaryColor);
    }

    public void setStyle(ItemStack stack, ResourceLocation style) {
        stack.getOrCreateTag().putString(TAG_STYLE, style.toString());
    }

    public void setRandomStyle(ItemStack stack, RandomSource randomSource) {
        setStyle(stack, styles.get(randomSource.nextInt(styles.size())));
    }

    public Optional<ResourceLocation> getStyle(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if(tag != null && tag.contains(TAG_STYLE)) {
            ResourceLocation style = new ResourceLocation(tag.getString(TAG_STYLE));
            if(styles.contains(style)) return Optional.of(style);
        }
        return Optional.empty();
    }

    public void clearStyle(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if(tag != null) {
            tag.remove(TAG_STYLE);
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(Multimap<Attribute, AttributeModifier> defaultModifiers, ItemStack stack, SlotInfo slot, UUID uuid) {
        defaultModifiers.put(EstrogenAttributes.FALL_DAMAGE_RESISTANCE.get(), new AttributeModifier(uuid, "ThighHighsFallDamageResistance", 100, AttributeModifier.Operation.ADDITION));
        return defaultModifiers;
    }

    public static final CauldronInteraction CAULDRON_INTERACTION = (blockState, level, blockPos, player, interactionHand, itemStack) -> {
        Item item = itemStack.getItem();
        if (!(item instanceof ThighHighsItem thighHighsItem)) {
            return InteractionResult.PASS;
        }
        if (!thighHighsItem.hasCustomColor(itemStack)) {
            return InteractionResult.PASS;
        }
        if (!level.isClientSide) {
            thighHighsItem.clearColor(itemStack);
            player.awardStat(Stats.CLEAN_ARMOR);
            LayeredCauldronBlock.lowerFillLevel(blockState, level, blockPos);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    };
}
