package dev.mayaqq.estrogen.fabric;

import dev.architectury.event.events.common.InteractionEvent;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.fabric.platformSpecific.EstrogenFabricEvents;
import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingEntityDamageEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;

public class EstrogenFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Estrogen.init();
        EstrogenFabricEvents.register();
        LivingEntityDamageEvents.HURT.register((event) -> {
            if (event.damaged instanceof Player player) {
                if (event.damageSource.is(DamageTypes.FALL) && player.hasEffect(EstrogenEffects.ESTROGEN_EFFECT)) {
                    event.damageAmount /= 1.5f;
                }
            }
        });
    }
}