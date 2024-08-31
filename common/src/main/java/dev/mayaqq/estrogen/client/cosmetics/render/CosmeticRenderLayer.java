package dev.mayaqq.estrogen.client.cosmetics.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.mayaqq.estrogen.client.cosmetics.Cosmetic;
import dev.mayaqq.estrogen.client.cosmetics.Cosmetics;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class CosmeticRenderLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    public CosmeticRenderLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        // TODO: Maya you wanted to do this right? the actual rendering
        // I copied and adapted how resourceful bees does it for testing, but u should probs redo this to how u like

        Cosmetic cosmetic = Cosmetics.getCosmetic(livingEntity.getUUID());
        if(cosmetic == null) return;

        stack.pushPose();
        stack.mulPose(Axis.XP.rotationDegrees(180));
        stack.scale(0.25f, 0.25f, 0.25f);
        stack.mulPose(Axis.YP.rotationDegrees((ageInTicks * 0.01F / 2f) * 360f));
        stack.translate(0f, (1.5 * Mth.sin(ageInTicks / 10 - 30f)), 3f);
        stack.mulPose(Axis.YP.rotationDegrees(-90));

        cosmetic.render(RenderType::entityTranslucentCull, buffer, stack, packedLight);

        stack.popPose();
    }
}
