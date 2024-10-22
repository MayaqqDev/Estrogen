package dev.mayaqq.estrogen.client.cosmetics.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.mayaqq.estrogen.client.cosmetics.Cosmetic;
import dev.mayaqq.estrogen.client.cosmetics.EstrogenCosmetics;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import org.joml.Vector2d;

public class CosmeticRenderLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public CosmeticRenderLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        Cosmetic cosmetic = EstrogenCosmetics.getCosmetic(player.getUUID());
        if(cosmetic == null) return;

        stack.pushPose();
        stack.mulPose(Axis.XP.rotationDegrees(180));
        stack.scale(0.75f, 0.75f, 0.75f);

        var hDiff = Vector2d.distance(player.xOld, player.zOld, player.getX(), player.getZ());
        var yDiff = player.yOld - player.position().y;

        var y = Math.max(Mth.lerp(0.3, yDiff, 0), 0) * 1.5f;
        var z = -Mth.lerp(0.3, hDiff, 0) * 1.25f;

        stack.translate(0, 0, z - 1);

        boolean bl = cosmetic.useDefaultAnimation();

        stack.translate(0f, bl ? (Mth.sin(ageInTicks / 10) / 4) - yDiff + y : .125 - yDiff + y, 0f);

        if(bl) {
            stack.translate(0.5f, 0.5f, 0.5f);
            stack.mulPose(Axis.YP.rotationDegrees((ageInTicks * 1) % 360f));
            stack.translate(-0.5f, -0.5f, -0.5f);
        }

        cosmetic.render(RenderType::entityCutout, buffer, stack, packedLight, OverlayTexture.NO_OVERLAY);

        stack.popPose();
    }
}
