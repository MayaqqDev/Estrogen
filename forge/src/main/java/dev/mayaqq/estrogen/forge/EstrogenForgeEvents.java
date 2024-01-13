package dev.mayaqq.estrogen.forge;

import com.simibubi.create.foundation.config.ConfigBase;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import dev.mayaqq.estrogen.registry.common.EstrogenItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

@Mod.EventBusSubscriber(modid = Estrogen.MOD_ID)
public class EstrogenForgeEvents {

    @SubscribeEvent
    public static void modifyDamageSource(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.hasEffect(EstrogenEffects.ESTROGEN_EFFECT)) {
                event.setDamageMultiplier(event.getDamageMultiplier() / 1.5f);
            }
        }
    }

    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        for (ConfigBase config : EstrogenConfig.CONFIGS.values())
            if (config.specification == event.getConfig()
                    .getSpec())
                config.onLoad();
    }

    @SubscribeEvent
    public static void onReload(ModConfigEvent.Reloading event) {
        for (ConfigBase config : EstrogenConfig.CONFIGS.values())
            if (config.specification == event.getConfig()
                    .getSpec())
                config.onReload();
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<ItemStack> evt) {
        ItemStack stack = evt.getObject();
        Item item = stack.getItem();
        evt.addCapability(EstrogenItems.ESTROGEN_PATCHES.getId(), CuriosApi.createCurioProvider(new ICurio() {

            @Override
            public ItemStack getStack() {
                return stack;
            }

            public void onEquip(SlotContext slotContext, ItemStack prevStack) {
                if (slotContext.entity() instanceof Player player) {
                    player.addEffect(new MobEffectInstance(EstrogenEffects.ESTROGEN_EFFECT, -1, stack.getCount() - 1, false, false, false));
                }
            }

            public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                slotContext.entity().removeEffect(EstrogenEffects.ESTROGEN_EFFECT);
            }
        }));
    }
}
