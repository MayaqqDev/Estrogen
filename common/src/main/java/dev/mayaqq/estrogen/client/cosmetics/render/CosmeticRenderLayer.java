package dev.mayaqq.estrogen.client.cosmetics.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.mayaqq.estrogen.client.cosmetics.Cosmetic;
import dev.mayaqq.estrogen.client.cosmetics.Cosmetics;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;

public class CosmeticRenderLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    public CosmeticRenderLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        Cosmetic cosmetic = Cosmetics.getCosmetic(player.getUUID());
        if(cosmetic == null) return;

        stack.pushPose();
        stack.mulPose(Axis.XP.rotationDegrees(180));
        //stack.mulPose(Axis.YP.rotationDegrees((ageInTicks * 0.01F / 2f) * 360f));
        //TODO fix it and make it spin slowly :(

        stack.translate(0f, (Mth.sin(ageInTicks / 10) / 4), 0f);
        stack.mulPose(Axis.YP.rotationDegrees(90));

        var x = Mth.lerp(0.3, player.xo - player.position().x, 0);
        var y = Mth.lerp(0.3, player.yo - player.position().y, 0);
        var z = Mth.lerp(0.3, player.zo - player.position().z, 0);

        stack.translate(x, y, z);

        cosmetic.render(RenderType::entityTranslucentCull, buffer, stack, packedLight, OverlayTexture.NO_OVERLAY);

        stack.popPose();
    }
}
