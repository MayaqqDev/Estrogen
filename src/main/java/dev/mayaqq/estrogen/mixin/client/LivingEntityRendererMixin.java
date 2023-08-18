package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.utils.extensions.LivingEntityRendererExtension;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> implements LivingEntityRendererExtension {

    @Final
    @Shadow
    protected List<FeatureRenderer<T, M>> features;


    @Override
    public void estrogen$addFeature(FeatureRenderer context) {
        features.add(context);
    }
}
