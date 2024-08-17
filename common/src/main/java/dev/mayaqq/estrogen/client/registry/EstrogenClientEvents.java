package dev.mayaqq.estrogen.client.registry;

import dev.mayaqq.estrogen.client.features.UwUfy;
import dev.mayaqq.estrogen.client.registry.entityRenderers.moth.MothModel;
import dev.mayaqq.estrogen.client.registry.particles.DashParticle;
import dev.mayaqq.estrogen.client.registry.particles.MothFuzzParticle;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import dev.mayaqq.estrogen.registry.EstrogenParticles;
import dev.mayaqq.estrogen.registry.items.ThighHighsItem;
import dev.mayaqq.estrogen.utils.EstrogenParticleRegistrator;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.Item;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class EstrogenClientEvents {
    public static void onDisconnect() {
        UwUfy.disconnect();
    }

    public static void onRegisterParticles(BiConsumer<ParticleType<SimpleParticleType>, EstrogenParticleRegistrator<SimpleParticleType>> consumer) {
        consumer.accept(EstrogenParticles.DASH.get(), DashParticle.Provider::new);
        consumer.accept(EstrogenParticles.MOTH_FUZZ.get(), (SpriteSet spriteSet) -> (simpleParticleType, clientLevel, d, e, f, g, h, i) -> new MothFuzzParticle(clientLevel, d, e, f, spriteSet));
    }

    public static void registerModelLayer(LayerDefinitionRegistry consumer) {
        consumer.register(MothModel.LAYER_LOCATION, MothModel::createBodyLayer);
    }
    public static void registerItemColorProviders(BiConsumer<ItemColor, Item[]> consumer) {
        consumer.accept((stack, tintIndex) -> ((ThighHighsItem) stack.getItem()).getColor(stack, tintIndex), new Item[]{EstrogenItems.THIGH_HIGHS.get()});
    }

    @FunctionalInterface
    public interface LayerDefinitionRegistry {

        void register(ModelLayerLocation location, Supplier<LayerDefinition> definition);
    }
}
