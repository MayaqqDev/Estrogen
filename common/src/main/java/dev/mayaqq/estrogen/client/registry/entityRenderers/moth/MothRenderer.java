package dev.mayaqq.estrogen.client.registry.entityRenderers.moth;

import dev.mayaqq.estrogen.registry.entities.MothEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MothRenderer extends MobRenderer<MothEntity, MothModel> {

    private static final ResourceLocation FLUFFY_TEXTURE = new ResourceLocation("estrogen", "textures/entity/moth/fluffy.png");
    private static final ResourceLocation NORMAL_TEXTURE = new ResourceLocation("estrogen", "textures/entity/moth/normal.png");

    public MothRenderer(EntityRendererProvider.Context context) {
        super(context, new MothModel(context.bakeLayer(MothModel.LAYER_LOCATION)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(MothEntity entity) {
        if (entity.isFluffy()) {
            return FLUFFY_TEXTURE;
        } else {
            return NORMAL_TEXTURE;
        }
    }
}
