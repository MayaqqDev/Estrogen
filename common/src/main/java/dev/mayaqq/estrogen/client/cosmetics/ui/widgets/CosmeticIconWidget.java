package dev.mayaqq.estrogen.client.cosmetics.ui.widgets;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.gui.widget.AbstractSimiWidget;
import dev.mayaqq.estrogen.client.cosmetics.Cosmetic;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

public class CosmeticIconWidget extends AbstractSimiWidget {

    public static final PartPose DEFAULT_POSE = PartPose.rotation(30.0f, 225.0f, 0);

    private final Cosmetic cosmetic;
    private final PartPose pose;

    private float scale = 1.0f;
    private float rotationSpeed;
    private float animRot;

    public CosmeticIconWidget(Cosmetic cosmetic, int x, int y, @Nullable PartPose referencePose) {
        super(x, y);
        this.cosmetic = cosmetic;
        this.pose = (referencePose != null) ? referencePose : DEFAULT_POSE;
        this.z = 150;
    }

    @Override
    protected void doRender(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        PoseStack matrices = graphics.pose();
        matrices.translate(this.getX() - 8f, this.getY() - 8f, z);

        if(pose.x != 0 || pose.y != 0 || pose.z != 0) {
            matrices.translate(pose.x, pose.y, pose.z);
        }
        matrices.scale(16.0F, -16.0F, 16.0F);

        if(this.hasTransforms()) {
            matrices.translate(0.5f, 0.5f, 0.5f);
            matrices.scale(scale, scale, scale);
            matrices.mulPose(rotateXYZ(pose.xRot, pose.yRot, pose.zRot));
            if(rotationSpeed != 0) {
                matrices.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, animRot, animRot + rotationSpeed)));
            }
            matrices.translate(-0.5f, -0.5f, -0.5f);
        }

        Lighting.setupForEntityInInventory();
        cosmetic.render(RenderType::entityTranslucent, graphics.bufferSource(), matrices, LightTexture.FULL_BRIGHT);
        graphics.flush();

    }

    // TODO: Actually properly animate this
    @Override
    public void tick() {
        if(rotationSpeed > 0) {
            this.animRot += rotationSpeed;
            if(animRot >= 360f) animRot = 0;
        }
    }

    private Quaternionf rotateXYZ(float x, float y, float z) {
        return new Quaternionf().rotateXYZ(x * Mth.DEG_TO_RAD, y * Mth.DEG_TO_RAD, z * Mth.DEG_TO_RAD);
    }

    public CosmeticIconWidget setRotationSpeed(float speed) {
        this.rotationSpeed = speed;
        return this;
    }

    public CosmeticIconWidget setScale(float scale) {
        this.scale = scale;
        return this;
    }

    public boolean hasTransforms() {
        return pose.xRot != 0 || pose.yRot != 0 || pose.zRot != 0 || scale != 1.0f || rotationSpeed != 0 || animRot != 0;
    }
}
