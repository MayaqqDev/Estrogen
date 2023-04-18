package dev.mayaqq.estrogen.entityRenderers;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.entities.DashParticleEntity;
import dev.mayaqq.estrogen.models.DashParticleEntityModel;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class DashParticleRenderer extends EntityRenderer<DashParticleEntity> {

    public DashParticleRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(DashParticleEntity entity) {
        return Estrogen.id("textures/entity/dash_particle.png");
    }
}
