package dev.mayaqq.estrogen.client.registry;

import com.jozufozu.flywheel.backend.Backend;
import dev.mayaqq.estrogen.client.features.UwUfy;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.texture.advanced.DynamicDreamTexture;
import dev.mayaqq.estrogen.client.registry.entityRenderers.moth.MothModel;
import dev.mayaqq.estrogen.client.registry.entityRenderers.mothElytra.MothElytraModel;
import dev.mayaqq.estrogen.client.registry.particles.DashParticle;
import dev.mayaqq.estrogen.client.registry.particles.MothFuzzParticle;
import dev.mayaqq.estrogen.registry.EstrogenParticles;
import dev.mayaqq.estrogen.utils.EstrogenParticleRegistrator;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class EstrogenClientEvents {
    public static void onDisconnect() {
        UwUfy.disconnect();
        DynamicDreamTexture.resetActive();
    }

    public static void onRegisterParticles(BiConsumer<ParticleType<SimpleParticleType>, EstrogenParticleRegistrator<SimpleParticleType>> consumer) {
        consumer.accept(EstrogenParticles.DASH.get(), DashParticle.Provider::new);
        consumer.accept(EstrogenParticles.MOTH_FUZZ.get(), (SpriteSet spriteSet) -> (simpleParticleType, clientLevel, d, e, f, g, h, i) -> new MothFuzzParticle(clientLevel, d, e, f, spriteSet));
    }

    public static void registerModelLayer(LayerDefinitionRegistry consumer) {
        consumer.register(MothModel.LAYER_LOCATION, MothModel::createBodyLayer);
        consumer.register(MothElytraModel.LAYER_LOCATION, MothElytraModel::createBodyLayer);
    }

    public static void onReloadRenderer(ClientLevel world) {
        if(!Backend.canUseInstancing(world)) DynamicDreamTexture.resetActive();
    }

    @FunctionalInterface
    public interface LayerDefinitionRegistry {

        void register(ModelLayerLocation location, Supplier<LayerDefinition> definition);
    }
}
