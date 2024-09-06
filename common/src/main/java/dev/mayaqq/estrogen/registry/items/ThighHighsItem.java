package dev.mayaqq.estrogen.registry.items;

import com.google.common.collect.Multimap;
import dev.mayaqq.estrogen.networking.EstrogenNetworkManager;
import dev.mayaqq.estrogen.networking.messages.s2c.ThighHighStylesPacket;
import dev.mayaqq.estrogen.registry.EstrogenAttributes;
import earth.terrarium.baubly.Baubly;
import earth.terrarium.baubly.common.Bauble;
import earth.terrarium.baubly.common.SlotInfo;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

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
    }

    public static int getItemColor(ItemStack stack, int tintIndex) {
        ThighHighsItem thighHighsItem = (ThighHighsItem) stack.getItem();
        if(thighHighsItem.getStyle(stack).isPresent()) return -1;
        return thighHighsItem.getColor(stack, tintIndex);
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

    public Stream<ItemStack> streamStyleItems() {
        return styles.stream().map(s -> {
            ItemStack stack = getDefaultInstance();
            setStyle(stack, s);
            return stack;
        });
    }

    public Optional<ResourceLocation> getStyle(ItemStack stack) {
        if(styles == null) return Optional.empty();
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

        level.playLocalSound(blockPos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 0.3f, 1.8f, true);

        if(level.isClientSide) {
            float fillHeight = blockState.getValue(LayeredCauldronBlock.LEVEL) / 3f;
            for (int i = 0; i < 8; i++) {
                double xOff = level.random.nextGaussian() / 5 + 0.5;
                double zOff = level.random.nextGaussian() / 5 + 0.5;

                level.addParticle(ParticleTypes.BUBBLE_POP, blockPos.getX() + xOff, blockPos.getY() + fillHeight * 0.8, blockPos.getZ() + zOff, 0, 0.05, 0);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    };
}
