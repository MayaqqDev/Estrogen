package dev.mayaqq.estrogen;

import com.google.common.base.Suppliers;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.registry.registries.RegistrarManager;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.registry.common.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class Estrogen {
    public static final String MOD_ID = "estrogen";

    public static final Logger LOGGER = LoggerFactory.getLogger("Estrogen");

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create("estrogen");

    public static final Supplier<RegistrarManager> MANAGER = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));

    public static EstrogenConfig CONFIG() {
        return new EstrogenConfig();
    }

    static {
        REGISTRATE.setTooltipModifierFactory(item ->
                new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE)
                        .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
        );
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static void init() {
        EstrogenCreativeTab.register();
        EstrogenAttributes.register();
        EstrogenBlockEntities.register();
        EstrogenBlocks.register();
        EstrogenEffects.register();
        EstrogenEnchantments.register();
        EstrogenEvents.register();
        EstrogenFluids.register();
        EstrogenFluidBlocks.register();
        EstrogenFluidItems.register();
        EstrogenFluidAttributes.register();
        EstrogenItems.register();
        EstrogenPonderScenes.register();
        EstrogenRecipes.register();
        EstrogenSounds.register();

        InteractionEvent.INTERACT_ENTITY.register((player, entity, hand) -> {
            ItemStack stack = player.getItemInHand(hand);
            if (entity instanceof Horse horse) {
                if (!horse.isBaby()) {
                    if (stack.getItem() == Items.GLASS_BOTTLE) {
                        stack.shrink(1);
                        player.playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH, 1.0f, 1.0f);
                        player.addItem(new ItemStack(EstrogenItems.HORSE_URINE_BOTTLE.get()));
                        return EventResult.interruptTrue();
                    }
                }
            }
            return EventResult.pass();
        });
    }
}
