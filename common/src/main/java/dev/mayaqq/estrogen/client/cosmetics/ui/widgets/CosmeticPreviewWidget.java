package dev.mayaqq.estrogen.client.cosmetics.ui.widgets;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.gui.widget.AbstractSimiWidget;
import com.simibubi.create.foundation.gui.widget.BoxWidget;
import dev.mayaqq.estrogen.client.cosmetics.Cosmetic;
import net.minecraft.Optionull;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class CosmeticPreviewWidget extends AbstractSimiWidget {

    private final Cosmetic cosmetic;
    private final boolean animate;

    private float xRot;
    private float yRot;
    private float zRot;
    private float offsetX;
    private float offsetY;
    private float offsetZ;

    public CosmeticPreviewWidget(Cosmetic cosmetic, int x, int y, boolean animate, @Nullable PartPose initialPose) {
        super(x, y);
        this.cosmetic = cosmetic;
        this.animate = animate;
        if(initialPose != null) {
            this.xRot = initialPose.xRot;
            this.yRot = initialPose.yRot;
            this.zRot = initialPose.zRot;
            this.offsetX = initialPose.x;
            this.offsetY = initialPose.y;
            this.offsetZ = initialPose.z;
        }
    }

    @Override
    protected void doRender(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        PoseStack matrices = graphics.pose();
        matrices.translate(this.getX() + 8, this.getY() + 8, z);


        if(offsetX != 0 || offsetY != 0 || offsetZ != 0) {
            matrices.translate(offsetX, offsetY, offsetZ);
        }
        if(xRot != 0 || yRot != 0 || zRot != 0) {
            matrices.mulPose(new Quaternionf().rotationXYZ(xRot * Mth.DEG_TO_RAD, yRot * Mth.DEG_TO_RAD, zRot * Mth.DEG_TO_RAD));
        }

        matrices.scale(16.0F, -16.0F, 16.0F);
        cosmetic.render(RenderType::entityTranslucent, graphics.bufferSource(), matrices, LightTexture.FULL_BRIGHT);
        graphics.flush();
    }

    @Override
    public void tick() {
        if(animate) {
            this.yRot += 5;
            if(yRot >= 360f) xRot = 0;
        }
    }
}
